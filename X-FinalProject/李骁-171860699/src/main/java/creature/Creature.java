package creature;

import java.net.URL;
import java.util.ArrayList;

import javafx.scene.image.*;
import view.MainCanvas;
import javafx.scene.canvas.GraphicsContext;
import java.lang.Math;
import runway.Runway;

public class Creature implements Runnable {

    private int power;

    private int posX;

    private int posY;

    private int moveSpeed;

    private int defaultMoveSpeed = 1;

    private int figureSize;

    private boolean belongToMe;

    private String name;

    private Image image;

    private Runway runway;

    private int price;

    public Creature() {
        URL url = getClass().getClassLoader().getResource("huluwa.png");
        System.out.println(url);
        String imagePath = new String(url.toString());
        image = new Image(url.toString());
    }

    public Creature(int power, int posX, int posY, int moveSpeed, boolean isGoodMan, String name, Runway runway) {
        this.power = power;
        this.posX = posX;
        this.posY = posY;
        this.moveSpeed = moveSpeed;
        this.figureSize = 50;
        this.belongToMe = isGoodMan;
        this.name = name;
        this.runway = runway;
        URL url = getClass().getClassLoader().getResource(name + ".png");
        System.out.println(url);
        String imagePath = new String(url.toString());
        image = new Image(url.toString());

        
    }

    public void move() {
        if (belongToMe) {
            posX = posX + moveSpeed;
        }
        else {
            posX = posX - moveSpeed;
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
        //while (true) {
            //获取敌方生物
            ArrayList<Creature> enemyCreatures = (belongToMe == true) ?
             this.runway.getYourCreatures() : this.runway.getMyCreatures();
            //检测碰撞，撞到就停
            if (enemyCreatures.size() > 0 && isCollide(enemyCreatures.get(0))) {
                this.moveSpeed = 0;
            }
            else {
                this.moveSpeed = defaultMoveSpeed;
                this.move();
            }

            try {
                Thread.sleep(10);
            }
            catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /*public void drawCreature(GraphicsContext gc) {
        double w = image.getWidth();
        double h = image.getHeight();
        gc.drawImage(image, 0, 0, w, h, posX, posY, w, h);
    }*/

    public void drawCreature(ImageView imageView) {
        imageView.setImage(image);
        imageView.setX(posX);
        imageView.setY(posY);
    }

    public boolean isCollide(Creature other) {
        return this.posY == other.posY
            && Math.abs(this.posX - other.posX) <= this.figureSize + other.figureSize;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Image getImage() {
        return this.image;
    }

    public void setRunway(Runway runway) {
        this.runway = runway;
    }
}