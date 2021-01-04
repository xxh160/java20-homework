package cn.edu.nju.main;

import cn.edu.nju.field.BattleField;
import cn.edu.nju.field.Point;
import cn.edu.nju.field.Terrain;

import cn.edu.nju.record.HLWRecord;
import cn.edu.nju.role.*;
import cn.edu.nju.web.Connection;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.security.PublicKey;
import java.util.*;

// 解决思路： task不要停
public class Controller {
    @FXML
    private Canvas battleFieldUI;
    @FXML
    private ImageView roleImg;
    @FXML
    private Button attackButton;
    @FXML
    private Button defendButton;
    @FXML
    private Button skillButton;
    @FXML
    private Button uniqueButton;
    @FXML
    private ListView<String>roleLeftList;
    private static ObservableList<String>dataLeftList = FXCollections.observableArrayList();
    private static Image chooseEffect = new Image("/img/choose/2.png");
    @FXML
    private  ImageView head1;
    @FXML
    private ProgressBar HPProgress;
    @FXML
    private ProgressBar MPProgress;
    @FXML
    private ProgressBar hpProgress1;
    @FXML
    private ProgressBar mpProgress1;
    @FXML
    private Label hpText;
    @FXML
    private Label mpText;
    @FXML
    private TextArea battleText;
    @FXML
    private Button endButton;
    private MainPart mainPart;

    @FXML
    private ListView<String>roleRightList;
    private final static int innerHeight = 16;
    private final static int innerWidth = 25;
    // width 800-20 height 518-13
    private static ObservableList<String>dataRightList = FXCollections.observableArrayList();
    private static HuLuWa[] huluwaList = new HuLuWa[6];
    private static Snake snake = new Snake();
    private static Scorp scorp = new Scorp();
    private static BattleField<Creature> battleField;
    private double canvasWidth = 0;
    private double canvasHeight = 0;
    private double canvasCellWidth = 32;
    private double canvasCellHeight = 32;
    private WritableImage whiteCover;
    private Creature choosedCreature;
    private Creature choosedEnemy;
    private BooleanProperty moveDrawable = new SimpleBooleanProperty();
    private GraphicsContext gc;
    private int moveSpeed = 50;
    private BooleanProperty myTurn =  new SimpleBooleanProperty();
    private Connection connection;
    private int playerID = 0;
    private Task enemyTurn;
    private int roundNum = 0;
    private static Boolean useSkillMode;
    private static Boolean replayMode;
    private int choosedSkill;
    private int skillSpeed = 100;
    private Thread connectThread;
    @FXML
    private void initialize() {
        // x y is reverse
        replayMode = false;
        useSkillMode = false;
        attackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onUseSkill1(event);
            }
        });
        defendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onUseSkill2(event);
            }
        });
        skillButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onUseSkill3(event);
            }
        });
        uniqueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onUseSkill4(event);
            }
        });
        endButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enemyRoundStart();
            }
        });
        gc = battleFieldUI.getGraphicsContext2D();
        battleField = new BattleField<Creature>(Creature.class,innerHeight,innerWidth);
        canvasWidth = battleFieldUI.getWidth();//800 - 25
        canvasHeight = battleFieldUI.getHeight();//518 - 16
        canvasCellWidth = canvasWidth/innerWidth;
        canvasCellHeight = canvasHeight/innerHeight;
        moveDrawable.setValue(true);
        for(int i=0;i<6;i++){
            huluwaList[i] = new HuLuWa();
            battleField.set((i+1)*2,1,huluwaList[i]);
        }
        battleField.set(innerHeight-2,innerWidth-2,snake);
        battleField.set(2,innerWidth-2,scorp);
        initStone();
        battleText.appendText("大娃：谁要挑战我，我将终结他的生命！\n");

        battleText.setDisable(true);
        battleText.setFocusTraversable(false);
        endButton.setFocusTraversable(false);
        skillButton.setFocusTraversable(false);
        uniqueButton.setFocusTraversable(false);
        attackButton.setFocusTraversable(false);
        defendButton.setFocusTraversable(false);
    }
    public void init(){
        battleText.clear();
        for(int i=0;i<6;i++){
            HuLuWa huLuWa = huluwaList[i];
            Creature c = battleField.getCreature((i+1)*2,1);
            battleField.set(huLuWa.getPosy(),huLuWa.getPosx(),c);
            battleField.set((i+1)*2,1,huluwaList[i]);
            huLuWa.init();
        }
        Creature c = battleField.getCreature(innerHeight-2,innerWidth-2);
        battleField.set(scorp.getPosy(),scorp.getPosx(),c);
        battleField.set(innerHeight-2,innerWidth-2,scorp);
        scorp.init();

        c = battleField.getCreature(2,innerWidth-2);
        battleField.set(snake.getPosy(),snake.getPosx(),c);
        battleField.set(2,innerWidth-2,snake);
        snake.init();

        enemyTurn = new Task() {
            @Override
            protected Object call() throws Exception {
                String  command;
                while(true){
                    //System.out.println(connection.isConnect());
                    command = connection.getMessage();
                    if(command==null){
                        System.out.println("链接断开？");
                    }
                    if(command.equals("game over")){
                        return null;
                    }
                    updateMessage(command);
                }
            }
        };
        enemyTurn.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("parse"+newValue);
                parseCommand(newValue);
            }
        });

        connectThread = new Thread(enemyTurn);
        connectThread.start();
    }
    public void initStone(){
        for(int i=0;i<innerWidth;i++){
            battleField.set(0,i,new Obstacle());
            battleField.set(innerHeight-1,i,new Obstacle());
        }
        for(int y=1;y<innerHeight-1;y++){
            battleField.set(y,0,new Obstacle());
            battleField.set(y,innerWidth-1,new Obstacle());
        }
        for(int x=2;x<innerWidth-2;x+=2){
            for(int y=2;y<innerHeight-2;y+=2){
                battleField.set(y,x,new Obstacle());
            }
        }

    }
    public void initSkill(){
        Button[] skillList = new Button[]{attackButton,defendButton,skillButton,uniqueButton};
        for(int i=0;i<4;i++){
            Skill skill = choosedCreature.getSkill(i);
            Image img = skill.getImg();
            String text = skill.getText();
            Button button = skillList[i];
            button.setGraphic(new ImageView(img));
            Tooltip tooltip = new Tooltip(text);
            tooltip.setShowDelay(Duration.ZERO);
            button.setTooltip(tooltip);
        }
    }
    public void initProper(){
        dataRightList.clear();
        dataLeftList.clear();
        Creature c = choosedCreature;
        dataLeftList.add("武力 : "+c.getATK());
        dataLeftList.add("防御 : "+c.getAD());
        dataLeftList.add("速度 : "+c.getSP());
        dataRightList.add("智力 : "+c.getAP());
        dataRightList.add("魔抗 : "+c.getMD());
        dataRightList.add("范围 : "+c.getRG());
        roleLeftList.setItems(dataLeftList);
        roleRightList.setItems(dataRightList);

        int hp = c.getHP();
        int mp = c.getMP();
        int maxHP = c.getMaxHP();
        int maxMP = c.getMaxMP();
        HPProgress.setProgress((double)hp/maxHP);
        MPProgress.setProgress((double)mp/maxMP);


        hpText.setText(hp+"/"+maxHP);
        mpText.setText(mp+"/"+maxMP);

    }
    public void initHead(){ roleImg.setImage(choosedCreature.getHead());
    }
    public void initEnemyProper(){
        Creature c = choosedEnemy;
        int hp = c.getHP();
        int mp = c.getMP();
        int maxHP = c.getMaxHP();
        int maxMP = c.getMaxMP();
        hpProgress1.setProgress((double)hp/maxHP);
        mpProgress1.setProgress((double)mp/maxMP);
    }
    public void initEnemyHead(){
        head1.setImage(choosedEnemy.getHead());
    }
    public void initColor(){
        whiteCover = new WritableImage(32,32);
        PixelWriter writer = whiteCover.getPixelWriter();
        for(int x=0;x<32;x++){
            for(int y=0;y<32;y++){
                writer.setColor(x,y,new Color(1,1,1,0.3));
            }
        }
    }
    public void startReplay(){
        battleText.clear();
        myTurn.setValue(false);
        replayMode = true;
        skillButton.setDisable(true);
        uniqueButton.setDisable(true);
        attackButton.setDisable(true);
        defendButton.setDisable(true);
        endButton.setDisable(true);
        useSkillMode = false;
        initProper();
        initEnemyProper();
    }
    public static Map<Point,Point> getReadablePoints(int sx, int sy, int range, boolean ignore){
        Point p = new Point(sx,sy,0,null);
        Queue<Point> queue = new LinkedList<>();
        queue.offer(p);
        String []direct = new String[]{"r","l","d","u"};
        Map<Point,Point> res = new HashMap<>();
        res.put(p,null);
        while(!queue.isEmpty()){
             Point root = queue.poll();
             int x = root.x;
             int y = root.y;
             int d = root.depth;
             if(d<range) {
                 int []tx = new int[]{x+1,x-1,x,x};
                 int []ty = new int[]{y,y,y+1,y-1};

                 for(int i=0;i<4;i++){
                     if(battleField.isTargetWalkable(tx[i],ty[i])||useSkillMode||ignore){
                         Point newP = new Point(tx[i],ty[i],d+1,direct[i]);
                         if(!res.containsKey(newP)){
                             res.put(newP,root);
                             queue.add(newP);
                         }
                     }
                 }
             }
        }
        return res;
    }
    public void roundStart(){
        System.out.println("round "+roundNum++);
        Creature.recovery();
        if(playerID==0){
            battleText.appendText("葫芦娃回合！\n");
            for(int i=0;i<6;i++){
                huluwaList[i].removeBuff();
            }
        }
        else{
            battleText.appendText("妖精回合！\n");
            snake.removeBuff();
            scorp.removeBuff();
        }
        initProper();
    }
    public void enemyRoundStart(){
        if(myTurn.get()){
            myTurn.setValue(false);
            try {
                connection.sendMessage("end "+roundNum);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(playerID==0){
                snake.removeBuff();
                scorp.removeBuff();
            }
            else{
                for(int i=0;i<6;i++){
                    huluwaList[i].removeBuff();
                }
            }
            initProper();
            draw();
            battleText.appendText("请等待敌方移动...\n");
        }
    }
    public void draw(Image image,double x,double y){
        gc.drawImage(image,x*canvasCellWidth,y*canvasCellHeight,canvasCellWidth,canvasCellHeight);
    }
    public void draw(){
        for (int y = 0; y < innerHeight; y++) {
            for (int x = 0; x < innerWidth; x++) {
                int type = battleField.getTerrain(y,x).getTypeInt();
                Creature c = battleField.getCreature(y,x);
                Image img = Terrain.imgList[type];
                draw(img,x,y);
                if(c.isDrawable()){
                    Image cimg = c.getSWalk();
                    draw(cimg,x,y);
                }
            }
        }
        if(useSkillMode){
            drawCover(choosedCreature.getSkill(choosedSkill).getRange());
        }
        else{
            drawCover();
        }

    }
    public void addFrame(ObservableList<KeyFrame> keyFrames, int sx, int sy, DoubleProperty tx,DoubleProperty ty, StringProperty mapKey,int st, String direct){
        switch (direct){
            case "u":{
                for(int i=3;i>=0;i--){
                    KeyFrame keyFrame = new KeyFrame(
                            Duration.millis(st+moveSpeed*i),
                            new KeyValue(tx,sx),
                            new KeyValue(ty,(double)sy-i/3),
                            new KeyValue(moveDrawable,false),
                            new KeyValue(mapKey,direct+(i%3+1))
                    );
                    keyFrames.add(0,keyFrame);
                }
                break;
            }
            case "d":{
                for(int i=3;i>=0;i--){
                    KeyFrame keyFrame = new KeyFrame(
                            Duration.millis(st+moveSpeed*i),
                            new KeyValue(tx,sx),
                            new KeyValue(ty,sy+(double)i/3),
                            new KeyValue(moveDrawable,false),
                            new KeyValue(mapKey,direct+(i%3+1))
                    );
                    keyFrames.add(0,keyFrame);
                }
                break;
            }
            case "l":{
                for(int i=3;i>=0;i--){
                    KeyFrame keyFrame = new KeyFrame(
                            Duration.millis(st+moveSpeed*i),
                            new KeyValue(tx,sx-(double)i/3),
                            new KeyValue(ty,sy),
                            new KeyValue(moveDrawable,false),
                            new KeyValue(mapKey,direct+(i%3+1))
                    );
                    keyFrames.add(0,keyFrame);
                }
                break;
            }
            case "r":{
                for(int i=3;i>=0;i--){
                    KeyFrame keyFrame = new KeyFrame(
                            Duration.millis(st+moveSpeed*i),
                            new KeyValue(tx,sx+(double)i/3),
                            new KeyValue(ty,sy),
                            new KeyValue(moveDrawable,false),
                            new KeyValue(mapKey,"r"+(i%3+1))
                    );
                    keyFrames.add(0,keyFrame);
                }
                break;
            }
        }
    }
    public void drawWalk(Creature c,int targetX,int targetY){
        Map<String,Image> walkMap = c.getWalkMap();
        DoubleProperty ty  = new SimpleDoubleProperty();
        DoubleProperty tx = new SimpleDoubleProperty();
        StringProperty mapKey = new SimpleStringProperty();
        Timeline timeLine = new Timeline();
        ObservableList<KeyFrame> keyFrames = timeLine.getKeyFrames();
        Map<Point,Point> map = getReadablePoints(c.getPosx(),c.getPosy(),c.getSP(),false);
        Point targetPoint = new Point(targetX,targetY,0,null);
        Point pre = map.get(targetPoint);
        String dir = null;
        if(pre==null||targetPoint==null){
            return;
        }
        if(targetPoint.y>pre.y){
            dir = "d";
        }
        else if(targetPoint.y<pre.y){
            dir = "u";
        }
        if(targetPoint.x<pre.x){
            dir = "l";
        }
        else if(targetPoint.x>pre.x){
            dir = "r";
        }
        addFrame(keyFrames,pre.x,pre.y,tx,ty,mapKey,pre.depth*moveSpeed*4,dir);
        while(map.get(pre)!=null){
            Point root = map.get(pre);
            addFrame(keyFrames,root.x,root.y,tx,ty,mapKey,root.depth*moveSpeed*4,pre.direct);
            pre = root;
        }
        moveDrawable.setValue(false);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                c.setDrawable(moveDrawable.getValue());
                draw();
                draw(walkMap.get(mapKey.get()),tx.doubleValue(),ty.doubleValue());

            }
        };
        timeLine.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                moveDrawable.setValue(true);
                c.setDrawable(true);
                draw();
                timer.stop();
                if(replayMode){
                    HLWRecord.playNextCommand();
                }
            }
        });
        timer.start();
        timeLine.play();
    }
    public void drawSkill(int x,int y,Skill skill){
        Timeline skillLine = new Timeline();
        ObservableList<KeyFrame> keyFrames = skillLine.getKeyFrames();
        IntegerProperty index = new SimpleIntegerProperty(0);
        int frameNum = skill.getFrameNum();
        for(int i=0;i<frameNum;i++){
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(skillSpeed*i),
                    new KeyValue(index,i)
            );
            keyFrames.add(keyFrame);
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
                if(skill.getLength()==0){
                    if(skill.getFocus()){
                        gc.drawImage(skill.getFrame(index.get()),(x-1)*canvasCellWidth,(y-1)*canvasCellHeight,canvasCellWidth*3,canvasCellHeight*3);
                    }
                    else{
                        gc.drawImage(skill.getFrame(index.get()),(x-1)*canvasCellWidth,(y-2)*canvasCellHeight,canvasCellWidth*3,canvasCellHeight*3);
                    }
                    //draw(skill.getFrame(index.get()),x,y);
                }
                else{
                    gc.drawImage(skill.getFrame(index.get()),(x-1)*canvasCellWidth,y*canvasCellHeight,canvasCellWidth*skill.getLength(),canvasCellHeight);
                }
            }
        };
        skillLine.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                moveDrawable.setValue(true);
                draw();
                timer.stop();
                if(replayMode){
                    HLWRecord.playNextCommand();
                }
            }
        });
        moveDrawable.setValue(false);
        timer.start();
        skillLine.play();
    }
    public void drawCover(){
        if(choosedCreature!=null){
            drawCover(choosedCreature.getSP());
        }
    }
    public void drawCover(int range){
        Creature c = choosedCreature;
        if(!c.isEnemy(playerID)&&c.isDrawable()){
            drawCover(choosedCreature.getPosx(),choosedCreature.getPosy(),range);
        }
    }
    public void drawCover(int sx,int sy,int range){
        if(myTurn.get()) {
            Map<Point, Point> map = getReadablePoints(sx,sy,range,false);
            for (Point p : map.keySet()) {
                draw(whiteCover, p.x, p.y);
            }
        }
    }
    public int[] convertXY(double x,double y){
        int []xy = new int[2];
        xy[0] = (int)Math.floor(x/canvasCellWidth);
        xy[1] = (int)Math.floor(y/canvasCellHeight);
        return xy;
    }
    public void onClickCanvas(MouseEvent event){
        double eventX = event.getX();
        double eventY = event.getY();
        int[] xy = convertXY(eventX,eventY);
        MouseButton mouseButton= event.getButton();
        if(mouseButton==MouseButton.SECONDARY){
            System.out.println("left click");
            battleText.appendText("您取消了释放技能\n");
            useSkillMode = false;
            drawCover();
            return;
        }
        if(useSkillMode){
            handleSkillClick(xy[0],xy[1]);
            return;
        }
        Creature c = battleField.getCreature(xy[1],xy[0]);
        // click another creature
        if(c.isAnimal()){
            if(replayMode){
                choosedCreature = c;
                choosedEnemy = c;
                initHead();
                initProper();
                initSkill();
                initEnemyHead();
                initEnemyProper();
            }
            else if(c.isEnemy(playerID)){
                choosedEnemy = c;
                initEnemyHead();
                initEnemyProper();
            }
            else{
                choosedCreature = c;
                initHead();
                initProper();
                initSkill();
            }
        }
        // click not creature
        else if(myTurn.get()&&!choosedCreature.isEnemy(playerID)&&battleField.isTargetWalkable(xy[0],xy[1])&&moveDrawable.get()){
            Point p = new Point(xy[0],xy[1],0,null);
            int range = choosedCreature.getSP();
            Map<Point,Point> map = getReadablePoints(choosedCreature.getPosx(),choosedCreature.getPosy(),range,false);
            Point pre = map.get(p);
            if(pre == null)
                return;
            int consume =pre.depth+1;

            if(range >= consume){

                try {
                    connection.sendMoveMessage(choosedCreature.getID(),xy[0],xy[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                drawWalk(choosedCreature,xy[0],xy[1]);
                choosedCreature.walkTo(battleField,xy[1],xy[0]);
                initProper();
            }
        }
    }
    public void onKeyPressed(KeyEvent event){
        KeyCode keyCode = event.getCode();
        System.out.println(keyCode);
        if(keyCode == KeyCode.SPACE){
            if(moveDrawable.get()){
                if(connection.getRecordMode()){
                    HLWRecord.endRecord();
                    battleText.appendText("录像文件已保存！\n");
                }
                else{
                    HLWRecord.startRecord();
                    battleText.appendText("开始录像！\n");
                }
            }
            else{
                battleText.appendText("请在动画演出完成后再录像！");
            }
            return;
        }

        if(!choosedCreature.isEnemy(playerID)&&moveDrawable.get()&&choosedCreature.getSP()!=0){
            int posx = choosedCreature.getPosx();
            int posy = choosedCreature.getPosy();
            int targetX = posx;
            int targetY = posy;
            if(keyCode == KeyCode.LEFT||keyCode == KeyCode.A){
                targetX = posx - 1;
            }
            else if(keyCode == KeyCode.RIGHT||keyCode == KeyCode.D){
                targetX = posx + 1;
            }
            else if(keyCode == KeyCode.UP||keyCode == KeyCode.W){
                targetY = posy - 1;
            }
            else if(keyCode == KeyCode.DOWN||keyCode == KeyCode.S){
                targetY = posy + 1;
            }
            if(posx!=targetX||posy!=targetY){
                if(battleField.isTargetWalkable(targetX,targetY)){
                    if(myTurn.get()) {
                        try {
                            connection.sendMoveMessage(choosedCreature.getID(),targetX,targetY);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        drawWalk(choosedCreature, targetX, targetY);
                        choosedCreature.walkTo(battleField, targetY, targetX);
                        initProper();
                    }
                }
            }
        }
    }
    public void onMoveCanvas(MouseEvent event){
        double eventX = event.getX();
        double eventY = event.getY();
        int[] xy = convertXY(eventX,eventY);
        draw();
        int scope = choosedCreature.getSkill(choosedSkill).getScope();
        if(useSkillMode&&scope!=0){
            drawCover(xy[0],xy[1],scope);
        }
        draw(chooseEffect,xy[0],xy[1]);

    }
    public void onUseSkill1(ActionEvent event){
        if(myTurn.get()&&!choosedCreature.isEnemy(playerID)&&moveDrawable.get()){
            if(choosedCreature.isAction()) {
                battleText.appendText("使用1号技能\n");
                useSkillMode = true;
                choosedSkill = 0;
                drawCover(choosedCreature.getSkill(2).getRange());
            }
            else{
                battleText.appendText("您本回合无法继续释放技能，可能是被敌方沉默了\n");
            }
        }


    }
    public void onUseSkill2(ActionEvent event){
        System.out.println("use skill 2\n");
        if(myTurn.get()&&!choosedCreature.isEnemy(playerID)&&moveDrawable.get()){
            if(choosedCreature.isAction()) {
                battleText.appendText("使用2号技能\n");
                useSkillMode = true;
                choosedSkill = 1;
            } else{
                battleText.appendText("您本回合无法继续释放技能，可能是被敌方沉默了\n");
            }
        }


    }
    public void onUseSkill3(ActionEvent event){
        System.out.println("use skill 3\n");
        if(myTurn.get()&&!choosedCreature.isEnemy(playerID)&&moveDrawable.get()){
            if(choosedCreature.isAction()){
                battleText.appendText("使用3号技能\n");
                useSkillMode = true;
                choosedSkill = 2;
                drawCover(choosedCreature.getSkill(2).getRange());
            }
            else{
                battleText.appendText("您本回合无法继续释放技能，可能是被敌方沉默了\n");
            }
        }
    }
    public void onUseSkill4(ActionEvent event){
        System.out.println("use skill 4\n");
        if(myTurn.get()&&!choosedCreature.isEnemy(playerID)&&moveDrawable.get()){
            if(choosedCreature.isAction()){
                battleText.appendText("使用4号技能\n");
                useSkillMode = true;
                choosedSkill = 3;
                drawCover(choosedCreature.getSkill(3).getRange());
            }
            else{
                battleText.appendText("您本回合无法继续释放技能，可能是被敌方沉默了\n");
            }

        }

    }
    public void handleSkillClick(int x,int y){
        Creature c = battleField.getCreature(y,x);
        Skill skill = choosedCreature.getSkill(choosedSkill);
        // enough mp & hp
        if(choosedCreature.getHP() < skill.getHpConsume()||choosedCreature.getMP() <= skill.getMpConsume()){
            battleText.appendText("HP或MP不足以支持你使用该技能\n");
            return;
        }


        // enemy only
        if(skill.getSkillType()!=Skill.RECOVERY&&skill.getSkillType()!=Skill.BUFF){
            if(c.isAnimal()&&!c.isEnemy(playerID)){
                battleText.appendText("请选择合法对象！\n");
                return;
            }
        }
        // check air
        if(skill.getSkillType()==Skill.MOVE){
            if(c.isAnimal()||c.isStone()){
                battleText.appendText("请选择合法对象！\n");
                return;
            }
        }
        else{
            if(!c.isAnimal()){
                battleText.appendText("请选择合法对象！\n");
                return;
            }
        }
        // range check
        Map<Point,Point> map = getReadablePoints(choosedEnemy.getPosx(),choosedCreature.getPosy(),skill.getRange(),false);
        Point p = map.get(new Point(c.getPosx(),c.getPosy(),0,null));
        if(p!=null&&p.depth+1>skill.getRange()){
            battleText.appendText("请选择合法对象\n");
            return;
        }
        // use skill
        try {
            connection.sendSkillMessage(choosedCreature.getID(),choosedSkill,x,y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(skill.getSkillType()==Skill.MOVE){
            choosedCreature.useSkill(skill,battleField,x,y);
        }
        else{
            choosedCreature.useSkill(skill,c);
        }
        drawSkill(x,y,skill);
        if(c.isAir()){
            if(playerID==0){
                if(snake.isAir()&&scorp.isAir()){
                    try {
                        connection.sendOverMessage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    gameOver("屌");
                    return;
                }
            }
            else {
                boolean live = false;
                for(HuLuWa huLuWa:huluwaList){
                    if(huLuWa.isAnimal()){
                        live = true;
                    }
                }
                if(!live){
                    try {
                        connection.sendOverMessage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    gameOver("屌");
                    return;
                }
            }
        }


        if(c.isEnemy(playerID)&&c.isAnimal()) {
            choosedEnemy = c;
            initEnemyProper();
            initEnemyHead();
        }
        useSkillMode = false;
        initProper();
        initEnemyProper();
        initEnemyHead();
        draw();
    }

    public void parseCommand(String command){
        String []para = command.split(" ");
        System.out.println("parse "+command);
        switch (para[0]){
            case "move":{
                Creature c = Creature.getCreatureById(Integer.parseInt(para[1]));
                int targetX = Integer.parseInt(para[2]);
                int targetY = Integer.parseInt(para[3]);
                drawWalk(c,targetX,targetY);
                c.walkTo(battleField,targetY,targetX);
                initProper();
                if(replayMode){
                    int id = Integer.parseInt(para[1]);
                    if(id<2){
                        playerID = 0;
                    }
                    else{
                        playerID = 1;
                    }
                }
                break;
            }
            case "skill":{
                parseSkill(para);
                if(replayMode){
                    int id = Integer.parseInt(para[1]);
                    if(id<2){
                        playerID = 0;
                    }
                    else{
                        playerID = 1;
                    }
                }
                break;
            }
            case "end":{
                roundStart();
                if(!replayMode){
                    myTurn.setValue(true);
                }
                else{
                    HLWRecord.playNextCommand();
                }
                break;
            }
            case "null":{
                break;
            }
            case "game":{
                if(replayMode){
                    gameOver("完");
                }
                else if(myTurn.get()){
                    gameOver("屌");
                }
                else{
                    gameOver("菜");
                }
            }
        }
    }
    public void parseSkill(String[]para){
        Creature c = Creature.getCreatureById(Integer.parseInt(para[1]));
        int skillIndex = Integer.parseInt(para[2]);
        Skill skill = c.getSkill(skillIndex);
        int x = Integer.parseInt(para[3]);
        int y = Integer.parseInt(para[4]);
        Creature targetC = battleField.getCreature(y,x);

        if(skill.getSkillType()==Skill.MOVE){
            c.useSkill(skill,battleField,x,y);
            battleText.appendText(c.getName()+"使用了位移技能\n");
        }
        else{
            c.useSkill(skill,targetC);
            battleText.appendText(c.getName()+"对"+targetC.getName()+"使用了"+skillIndex+"号技能\n");
        }
        draw();
        drawSkill(x,y,skill);
        if(checkDead(targetC)){
            return;
        };
        initProper();
        initEnemyProper();
        initEnemyHead();
    }
    public void setPlayerID(int id){
        this.playerID = id;
        myTurn.set(id == 0);
        if(myTurn.get()){
            System.out.println("round "+roundNum++);;
            choosedCreature = huluwaList[0];
            choosedEnemy = snake;
        }
        else{
            choosedCreature = snake;
            choosedEnemy = huluwaList[0];
        }
        init();
        initSkill();
        initProper();
        initHead();
        initColor();
        initEnemyHead();
        initEnemyProper();
        replayMode = false;
        useSkillMode = false;
        skillButton.setDisable(false);
        uniqueButton.setDisable(false);
        attackButton.setDisable(false);
        defendButton.setDisable(false);
        endButton.setDisable(false);
        draw();
    }
    public void setConnection(Connection connect){
        connection = connect;
    }
    public boolean checkDead(Creature targetC){
        if(targetC.isAir()&&choosedCreature.getID() == targetC.getID()){
            choosedCreature = null;
            if(replayMode){
                for(HuLuWa huLuWa:huluwaList){
                    if(!huLuWa.isAir()){
                        choosedCreature = huLuWa;
                        choosedEnemy = huLuWa;
                        break;
                    }
                }
                if(!snake.isAir()){
                    choosedCreature = snake;
                    choosedEnemy = snake;
                }
                if(!scorp.isAir()){
                    choosedCreature = scorp;
                    choosedEnemy = scorp;
                }
            }
            else if(playerID == 0){
                for(HuLuWa huLuWa:huluwaList){
                    if(!huLuWa.isAir()){
                        choosedCreature = huLuWa;
                        break;
                    }
                }
            }
            else{
                if(!snake.isAir()){
                    choosedCreature = snake;
                }
                if(!scorp.isAir()){
                    choosedCreature = scorp;
                }
            }
            if(choosedCreature == null){
                try {
                    connection.sendOverMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(replayMode){
                    gameOver("完");
                }
                else{
                    gameOver("菜");
                }

                return true;
            }
            initProper();
            initHead();
            initSkill();
        }
        return false;
    }
    public void gameOver(String state){
        try {
            if(connection!=null){
                connection.close();
                if(connection.getRecordMode()){
                    HLWRecord.endRecord();
                }
            }
            mainPart.showOverUI(mainPart.getPrimaryStage(),state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setMainPart(MainPart mainPart){
        this.mainPart = mainPart;
    }
    public void setRecordMode(boolean record){
        connection.setRecordMode(record);
    }
    public HuLuWa[] getHuluwaList(){
        return huluwaList;
    }
    public Snake getSnake(){
        return snake;
    }
    public Scorp getScorp(){
        return scorp;
    }
    public BattleField<Creature> getBattleField(){
        return battleField;
    }

    public void setChoosedCreature(Creature choosedCreature) {
        this.choosedCreature = choosedCreature;
    }
    public void setChoosedEnemy(Creature choosedEnemy){
        this.choosedEnemy = choosedEnemy;
    }
}
