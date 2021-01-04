package game.model;

import game.BattleGround;
import game.view.BulletView;
import javafx.scene.paint.Color;
import tool.Screen;
import tool.point.Grid;
import tool.point.Pixel;

public class Bullet implements Runnable {
    private int damage;
    private Character src;
    private Color color;
    private int radius;
    private Pixel pixel;
    private int speed;
    private BulletView bulletView;

    public Bullet(Character src, int damage, int speed, Pixel pixel, int radius, Color color) {
        this.src = src;
        this.damage = damage;
        this.pixel = new Pixel(pixel);
        this.speed = speed;
        this.color = color;
        this.radius = radius;
        bulletView = new BulletView(this);
        bulletView.addScene(BattleGround.root);
        BattleGround.bulletViewSetLock.lock();
        BattleGround.bulletViewSet.add(bulletView);
        BattleGround.bulletViewSetLock.unlock();
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }

    public void move() {
        pixel.setX(pixel.getX() + speed);
    }

    public Pixel getPixel() {
        return pixel;
    }

    public void hit(Character dest) {
        src.hit(dest, damage);
        dest.hurt(src, damage);
    }

    public void end() {
        /*
         * Platform.runLater(()->{ bulletView.remove(BattleGround.root); });
         */
        bulletView.setDead();

    }

    @Override
    public void run() {
        Grid grid;
        Character dest;
        while (pixel.getX() >= 93 + radius * 2 && pixel.getX() <= 1307 - radius * 2) {
            grid = Screen.pixelToGrid(pixel);
            dest = BattleGround.getCharacter(grid);
            if (dest != null && dest.camp != src.camp) {
                hit(dest);
                break;
            }
            move();
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        end();
    }

    public String getThreadID() {
        return Thread.currentThread().getName();
    }
}