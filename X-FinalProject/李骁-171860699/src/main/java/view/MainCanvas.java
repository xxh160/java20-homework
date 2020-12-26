package view;

import java.util.ArrayList;

import creature.Creature;
import runway.Runway;
import runway.RunwayField;
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

    //public static ArrayList<Runway> runways; //跑道区

    public static RunwayField runwayField; //跑道区

    public static CardField cardField; //卡牌区

    private boolean isRunning = true; //游戏正在运行

    //private Button controller; //按Q退出的控制器

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
        
        //初始化跑道
        /*runways = new ArrayList<Runway>();
        for (int i = 0; i < 3; i++) {
            runways.add(new Runway(100, 50 + 100*i, 100, 600));
        }*/
        runwayField = new RunwayField();

        //线程池
        exec = Executors.newCachedThreadPool();
        //启动葫芦娃线程
        //exec.execute(thread);

        //初始化卡牌区
        cardField = new CardField(root);
        cardField.removeAllCards();
        cardField.fillCards();

    }

    public void draw() {

    }

    public void update() {

    }

}