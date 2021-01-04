package gui;
import CreatrueClass.*;
import Network.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import BattleField.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;


public class Controller
{
    @FXML
    private GridPane battlegridpane;

    @FXML
    private Button StartButton;

    @FXML
    private Button LoadButton;

    @FXML
    private Pane StartPane;

    @FXML
    private Pane StatePane;

    @FXML
    private ImageView AskillImg;

    @FXML
    private ImageView SkillImg;

    @FXML
    private ImageView load;

    @FXML
    private Rectangle CurrentBlood;

    @FXML Circle profileImg;

    @FXML Text AskillName;

    @FXML Text SkillName;

    @FXML Text Askilldes;

    @FXML Text Skilldes;

    @FXML Label askillcd;

    @FXML Label skillcd;

    Battlefield battlefield;

    ImageView groundview[][];

    String buffset[];

    Filerecord filerecord[];
    int filelength;
    int fileindex;
    int filetime;

    Stage stage;
    String state;
    ImageView selectview = standard(new ImageView(), new Image("/Select.png"));
    Timer refreshtimer;
    Timer msgtimer;
    Timer filetimer;

    client mc;
    public void initController(Stage stage) throws IOException
    {
        this.stage=stage;
        filerecord=new Filerecord[1000];
        mc=new client("GC");
        Thread th=new Thread(mc);
        th.start();
        battlegridpane.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                if (state!="Gaming") return;
                select(event.getY(),event.getX());
            }
        });

        battlegridpane.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event)
            {
                if (state!="Gaming") return;
                if (battlefield.getselectX()==-1) return;
                String tmpmsg="";
               // System.out.println(event.getCode().getName());
                switch (event.getCode())
                {
                    case UP:
                        tmpmsg=battlefield.getselectX()+" "+battlefield.getselectY()+" "+"UP";
                        mc.sendMsg(tmpmsg);
                        battlefield.solvemsg(Message.UP,battlefield.getselectX(),battlefield.getselectY(),true);
                        break;
                    case DOWN:
                        tmpmsg=battlefield.getselectX()+" "+battlefield.getselectY()+" "+"DOWN";
                        mc.sendMsg(tmpmsg);
                        battlefield.solvemsg(Message.DOWN,battlefield.getselectX(),battlefield.getselectY(),true);
                        break;
                    case LEFT:
                        tmpmsg=battlefield.getselectX()+" "+battlefield.getselectY()+" "+"LEFT";
                        mc.sendMsg(tmpmsg);
                        battlefield.solvemsg(Message.LEFT,battlefield.getselectX(),battlefield.getselectY(),true);
                        break;
                    case RIGHT:
                        tmpmsg=battlefield.getselectX()+" "+battlefield.getselectY()+" "+"RIGHT";
                        mc.sendMsg(tmpmsg);
                        battlefield.solvemsg(Message.RIGHT,battlefield.getselectX(),battlefield.getselectY(),true);
                        break;
                    case A:
                        tmpmsg=battlefield.getselectX()+" "+battlefield.getselectY()+" "+"A";
                        mc.sendMsg(tmpmsg);
                        battlefield.solvemsg(Message.ATTACK,battlefield.getselectX(),battlefield.getselectY(),true);
                        break;
                    case Q:
                        tmpmsg=battlefield.getselectX()+" "+battlefield.getselectY()+" "+"Q";
                        mc.sendMsg(tmpmsg);
                        battlefield.solvemsg(Message.SKILL,battlefield.getselectX(),battlefield.getselectY(),true);
                        break;
                    default:break;
                }
                if (battlefield.getState()=="Evil" || battlefield.getState()=="Justice")
                {
                    if (battlefield.getState()==mc.getSide()) gamewin();
                    else gamelose();
                }
            }
        });

        buffset=new String[]{"attack_increase","attack_decrease","defence_increase","defence_decrease","stun","bound","mess"};

        //System.out.println("!!!!");

        groundview=new ImageView[17][14];

        for (int i=0;i<17;i++)
            for (int j=0;j<14;j++)
            {
                 groundview[i][j]= standard(new ImageView(),new Image("/Ground.png"));
            }

        AskillImg.setFitWidth(62.5);
        AskillImg.setFitHeight(54);

        SkillImg.setFitWidth(62.5);
        SkillImg.setFitHeight(54);
    }

    public void startgame()
    {
        state="Gaming";
        battlefield=new Battlefield(17,14);
        battlefield.putin(3,1,new HuLuWa(100,100,100,"Red"));
        battlefield.putin(3,3,new HuLuWa(100,100,100,"Orange"));
        battlefield.putin(3,5,new HuLuWa(100,100,100,"Yellow"));
        battlefield.putin(3,7,new HuLuWa(100,100,100,"Green"));
        battlefield.putin(3,9,new HuLuWa(100,100,100,"Blue"));
        battlefield.putin(3,11,new HuLuWa(100,100,100,"Cyan"));
        battlefield.putin(3,13,new HuLuWa(100,100,100,"Purple"));

        battlefield.putin(1,2,new Grandpa(100,10,1));
        battlefield.putin(1,12,new Pangolin(100,100,10));

        battlefield.putin(14,2,new Frog(100,20,60));
        battlefield.putin(14,4,new Crocodile(120,80,100));
        battlefield.putin(14,6,new Scorpion(200,100,120));
        battlefield.putin(14,8,new Snake(200,100,120));
        battlefield.putin(14,10,new Chilopod(120,80,100));
        battlefield.putin(14,12,new Mouse(100,20,60));
        refresh();


        battlefield.startgame();
        battlegridpane.setFocusTraversable(true);
        battlegridpane.setVisible(true);
        StartButton.setVisible(false);
        LoadButton.setVisible(false);
        load.setVisible(false);
        selectview.setVisible(true);

        if (!mc.isConnect()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("离线模式");
            alert.setHeaderText("宁怎么在一个人玩游戏，是没有朋友吗？");
            alert.setContentText("离线模式下无法分配阵营，你可以支配所有单位");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("分配阵营");
            if (mc.getSide()=="Evil")
                alert.setHeaderText("分配的阵营为:妖精");
            else
                alert.setHeaderText("分配的阵营为:葫芦娃");
            alert.setContentText("出发！");

            alert.showAndWait();
        }
        msgtimer=new Timer();
        msgtimer.schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        if (mc.ifNewMsg())
                        {
                            //System.out.println("newmsg:" + mc.getMsg());
                            Platform.runLater(
                                    () -> {SolveReceivedMsg(mc.getMsg());}
                            );
                            if (battlefield.getState()=="Evil" || battlefield.getState()=="Justice")
                            {
                                if (battlefield.getState()==mc.getSide()) gamewin();
                                else gamelose();
                            }
                        }
                    }
                },0,100
        );

        refreshtimer=new Timer();
        refreshtimer.schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        //System.out.println("REFRESH");
                        Platform.runLater(
                                () -> {refresh();}
                        );
                        //this.cancel();
                    }
                },0,100
        );
    }

    public void loadgame() {
        /*Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Warning Dialog");
        alert.setContentText("Careful with the next step!");

        alert.showAndWait();*/

        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择一个对战文件");
        chooser.setInitialDirectory(new File("C:\\"));
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("所有文件", "*.*"),
                new FileChooser.ExtensionFilter("所有文本文件", "*.txt", "*.doc", "*.docx"));

        File file = chooser.showOpenDialog(stage); // 显示文件打开对话框
        System.out.println("准备打开的文件路径是："+file.getAbsolutePath());

        BufferedReader reader = null;
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            filelength=0;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                System.out.println("line " + line + ": " + tempString);
                filerecord[filelength]=new Filerecord(tempString);
                filelength++;
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally
        {
            if (reader != null) {
                try
                {
                    reader.close();
                }
                catch (IOException e1) { }
            }
        }

        state="Loading";
        battlefield=new Battlefield(17,14);
        battlefield.startload();
        battlefield.putin(3,1,new HuLuWa(100,100,100,"Red"));
        battlefield.putin(3,3,new HuLuWa(100,100,100,"Orange"));
        battlefield.putin(3,5,new HuLuWa(100,100,100,"Yellow"));
        battlefield.putin(3,7,new HuLuWa(100,100,100,"Green"));
        battlefield.putin(3,9,new HuLuWa(100,100,100,"Blue"));
        battlefield.putin(3,11,new HuLuWa(100,100,100,"Cyan"));
        battlefield.putin(3,13,new HuLuWa(100,100,100,"Purple"));

        battlefield.putin(1,2,new Grandpa(100,10,1));
        battlefield.putin(1,12,new Pangolin(100,100,10));

        battlefield.putin(14,2,new Frog(100,20,60));
        battlefield.putin(14,4,new Crocodile(120,80,100));
        battlefield.putin(14,6,new Scorpion(200,100,120));
        battlefield.putin(14,8,new Snake(200,100,120));
        battlefield.putin(14,10,new Chilopod(120,80,100));
        battlefield.putin(14,12,new Mouse(100,20,60));
        refresh();

        battlefield.startgame();
        battlegridpane.setFocusTraversable(true);
        battlegridpane.setVisible(true);
        StartButton.setVisible(false);
        LoadButton.setVisible(false);
        load.setVisible(false);
        selectview.setVisible(false);

        refreshtimer=new Timer();
        refreshtimer.schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        //System.out.println("REFRESH");
                        Platform.runLater(
                                () -> {refresh();}
                        );
                        //this.cancel();
                    }
                },0,100
        );

        fileindex=0;
        filetime=filerecord[0].getTime();
        filetimer=new Timer();
        filetimer.schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        //System.out.println("REFRESH");
                        Platform.runLater(
                                () ->
                                {
                                       while (fileindex<filelength && filetime>=filerecord[fileindex].getTime())
                                       {
                                           System.out.println(filetime+" "+filerecord[fileindex].getMsg()+" "+
                                                   filerecord[fileindex].getX()+" "+filerecord[fileindex].getY());
                                           battlefield.solvemsg(filerecord[fileindex].getMsg(),
                                                    filerecord[fileindex].getX(),filerecord[fileindex].getY(),false);
                                           fileindex++;
                                       }
                                       filetime+=1;
                                       if (fileindex>=filelength)
                                       {
                                           recordend();
                                           this.cancel();
                                       }
                                    /*battlefield.solvemsg(filerecord[fileindex].getMsg(),
                                            filerecord[fileindex].getX(),filerecord[fileindex].getY());
                                    fileindex++;*/
                                }
                        );
                        //this.cancel();
                    }
                },0,100
        );

    }
    private void recordend()
    {
        refreshtimer.cancel();
        battlegridpane.setFocusTraversable(false);
        StatePane.setVisible(false);
        battlegridpane.setVisible(false);
        StartButton.setVisible(true);
        LoadButton.setVisible(true);
        load.setVisible(true);
        /*Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Record End!");
        alert.showAndWait();*/
        filetimer.cancel();
    }

    private void gamewin()
    {
        msgtimer.cancel();
        refreshtimer.cancel();
        battlegridpane.setFocusTraversable(false);
        StatePane.setVisible(false);
        battlegridpane.setVisible(false);
        StartButton.setVisible(true);
        LoadButton.setVisible(true);
        load.setVisible(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //mc.sendMsg("11");
        alert.setTitle("Game End!");
        alert.setHeaderText("GAME END");
        if (mc.isConnect())
        {
            alert.setHeaderText("YOU WIN");
            alert.setContentText("液液");
        }
        alert.showAndWait();
    }

    private void gamelose()
    {
        msgtimer.cancel();
        refreshtimer.cancel();
        mc.sendMsg("endgame");
        battlegridpane.setFocusTraversable(false);
        StatePane.setVisible(false);
        battlegridpane.setVisible(false);
        StartButton.setVisible(true);
        LoadButton.setVisible(true);
        load.setVisible(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game End!");
        alert.setHeaderText("GAME END");
        if (mc.isConnect())
        {
            alert.setHeaderText("YOU LOSE");
            alert.setContentText("乌乌");
        }
        alert.showAndWait();
    }

    private void SolveReceivedMsg(String str)
    {
        String[] strbuf=str.split(" ");
        int x=Integer.parseInt(strbuf[0]);
        int y=Integer.parseInt(strbuf[1]);
        Message msg = null;
        switch (strbuf[2]) {
            case "UP":
                msg=Message.UP;
                break;
            case "DOWN":
                msg=Message.DOWN;
                break;
            case "LEFT":
                msg=Message.LEFT;
                break;
            case "RIGHT":
                msg=Message.RIGHT;
                break;
            case "A":
                msg=Message.ATTACK;
                break;
            case "Q":
                msg=Message.SKILL;
                break;
            default:break;
        }
        battlefield.solvemsg(msg,x,y,false);
    }

    private void select(double x, double y)
    {
        int panex = (int) Math.floor(y/62.5)+1;
        int paney = (int) Math.floor(x/54)+1;
        //System.out.println(panex);
        //System.out.println(paney);

        Creatrue tmpc=battlefield.getcontent(panex,paney);
        boolean pd=false;
        if (!mc.isConnect()) pd=true;
                else
                    if(tmpc!=null && tmpc.getSide()==mc.getSide()) pd=true;
        if (tmpc!=null && pd)
        {
            battlefield.select(panex,paney);

            SkillImg.setImage(tmpc.getSkill().getImg());
            SkillName.setText(tmpc.getSkill().getName());
            Skilldes.setText(tmpc.getSkill().getDescription());

            AskillImg.setImage(tmpc.getAskill().getImg());
            AskillName.setText(tmpc.getAskill().getName());
            Askilldes.setText(tmpc.getAskill().getDescription());
            profileImg.setFill(new ImagePattern(tmpc.getProfilephoto()));
            CurrentBlood.setWidth(748*((tmpc.getTmpblood()) *1.0)/ (tmpc.getBlood()*1.0));
            StatePane.setVisible(true);
        }
        else
        {
            battlefield.unselect();
            StatePane.setVisible(false);
        }
    }


    private void refresh()
    {
        if (battlefield.getState()=="Evil" || battlefield.getState()=="Justice")
        {
            if (battlefield.getState()==mc.getSide()) gamewin();
            else gamelose();
        }

        battlegridpane.getChildren().clear();
        Creatrue tmpc;
        ImageView tmpeffect;
        for (int i=1;i<17;i++)
            for (int j=1;j<14;j++)
            {
                battlegridpane.add(groundview[i][j],i,j);

                tmpc=battlefield.getcontent(i,j);

                if (tmpc!=null)
                {
                    battlegridpane.add(tmpc.getimgview(),i,j);
                    battlegridpane.add(tmpc.getBloodview(),i,j);
                    battlegridpane.add(tmpc.getTmpbloodview(),i,j);
                    for (int k=0;k<7;k++)
                        if (tmpc.hasbuff(buffset[k]))
                            battlegridpane.add(tmpc.getbuffview(k),i,j);
                }

                if (battlefield.haseffect(i,j))
                {
                    battlegridpane.add(battlefield.geteffect(i,j),i,j);
                }
            }

        if (battlefield.getselectX()!=-1 && battlefield.getcontent(battlefield.getselectX(),battlefield.getselectY())!=null)
        {
            battlegridpane.add(selectview, battlefield.getselectX(), battlefield.getselectY());
            tmpc=battlefield.getcontent(battlefield.getselectX(),battlefield.getselectY());
            if (tmpc.getSkill().incd())
                skillcd.setText("冷却中");
            else
                skillcd.setText("");
            CurrentBlood.setWidth(748*((tmpc.getTmpblood()) *1.0)/ (tmpc.getBlood()*1.0));
        }
    }

    private ImageView standard(ImageView imgv, Image img)
    {
        imgv.setImage(img);
        imgv.setFitHeight(54);
        imgv.setFitWidth(62.5);
        imgv.setPreserveRatio(true);
        return imgv;
    }
}
