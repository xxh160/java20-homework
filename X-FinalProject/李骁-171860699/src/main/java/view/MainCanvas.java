package view;

import java.util.ArrayList;

import creature.Creature;
import runway.Runway;
import card.CardField;
import card.Card;
import card.CreatureCard;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainCanvas extends Canvas {

    public static AnchorPane root; //父面板

    private Creature huluwa1, huluwa2; //测试的生物

    public static ArrayList<Runway> runways; //跑道区

    public static CardField cardField; //卡牌区

    private GraphicsContext graphicsContext; //移动后无法消除原图

    //private ImageView imageView, imageView2; //移动后可以消除原图

    private boolean isRunning = true; //游戏正在运行

    private Button controller; //按Q退出的控制器

    public static ExecutorService exec; //线程池

    private Thread thread = new Thread(new Runnable(){ //画图线程
    
        @Override
        public void run() {
            while (!Thread.interrupted() && isRunning) {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        draw();
                        update();
                    }
                });
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    //e.printStackTrace();
                }
            }
        }
    });

    public MainCanvas(AnchorPane root, double width, double height) {
        super(width, height);
        this.root = root; //父面板
        graphicsContext = getGraphicsContext2D();

        //初始化控制器按钮
        controller = new Button();
        controller.setLayoutX(300);
        controller.setLayoutY(400);
        controller.setPrefWidth(1);
        controller.setPrefHeight(1);
        //controller.setVisible(false);
        controller.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("按下了：" + event.getCode().name());
                if (event.getCode() == KeyCode.D) {
                    System.out.println("换牌");
                    cardField.freshAllCards();
                }
                else if (event.getCode() == KeyCode.G) {
                    System.out.println("给敌方加人");
                    runways.get(1).addYourCreature(new Creature());
                }
            }
        });
        root.getChildren().add(controller);



        //初始化跑道
        runways = new ArrayList<Runway>();
        for (int i = 0; i < 3; i++) {
            runways.add(new Runway(100, 50 + 100*i, 100, 600));
        }
        //生成葫芦娃
        //huluwa1 = new Creature(1, 0, 0, 1, true, "huluwa", runways.get(0));
        //huluwa2 = new Creature(1, 600, 0, 1, false, "huluwa", runways.get(0));
        //葫芦娃放置到跑道上
        //runways.get(0).addMyCreature(huluwa1);
        //runways.get(0).addYourCreature(huluwa2);

        
        //线程池
        exec = Executors.newCachedThreadPool();
        //启动葫芦娃线程
        //exec.execute(huluwa1);
        //exec.execute(huluwa2);
        //exec.execute(thread);

        //初始化卡牌区
        cardField = new CardField(root);
        cardField.removeAllCards();
        cardField.fillCards();

        //添加葫芦娃到pane
        //huluwa1.addToPane(root);
        //huluwa2.addToPane(root);
        /*
        //用于显示葫芦娃的imageview，加入面板
        imageView = new ImageView();
        root.getChildren().add(imageView); 
        imageView2 = new ImageView();
        root.getChildren().add(imageView2);*/

        /*
        //用于显示卡牌区的imageview，加入面板
        cardViews = new ArrayList<ImageView>();
        for (int i = 0; i < cardField.getCardFieldSize(); i++) {
            ImageView cardView = new ImageView();
            cardViews.add(cardView);
            root.getChildren().add(cardView);
        }*/
        /*
        //遍历卡牌区，画卡牌区
        ArrayList<Card> cards = cardField.getCards();
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).drawCard();
        }*/
    }

    public void draw() {
        
        //huluwa1.drawCreature(imageView);
        //huluwa2.drawCreature(imageView2);
        /*
        //遍历卡牌区，画卡牌区
        ArrayList<Card> cards = cardField.getCards();
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).drawCard();
        }*/
    }

    public void update() {

    }

}