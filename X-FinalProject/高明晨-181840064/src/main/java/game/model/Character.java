package game.model;

import java.util.concurrent.locks.ReentrantLock;

import game.BattleGround;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Direction;
import tool.Screen;
import tool.point.Grid;
import tool.point.Pixel;

abstract public class Character {
    protected volatile Grid grid;
    protected Grid initGrid;
    protected int HP;
    protected int MP;
    protected int maxHP;
    protected int maxMP;
    protected boolean alive;
    protected int strength;
    protected int bulletSpeed;
    protected int bulletRadius;
    protected Camp camp;
    protected Color bulletColor;
    protected ReentrantLock HPLock;
    protected ReentrantLock MPLock;
    protected ReentrantLock aliveLock;
    protected ReentrantLock gridLock;

    public Character(Grid initGrid, int maxHP, int maxMP, int strength, int bulletSpeed, Color bulletColor, Camp camp) {
        this.initGrid = new Grid(initGrid);
        this.maxHP = maxHP;
        this.maxMP = maxMP;
        this.strength = strength;
        this.camp = camp;
        this.bulletSpeed = bulletSpeed;
        this.bulletColor = bulletColor;
        this.bulletRadius = 5;
        if (camp == Camp.Yaojing) {
            this.bulletSpeed = -bulletSpeed;
        }
        HPLock = new ReentrantLock();
        MPLock = new ReentrantLock();
        aliveLock = new ReentrantLock();
        gridLock = new ReentrantLock();
        init();

    }

    public void init() {
        grid = new Grid(initGrid);
        HP = maxHP;
        MP = maxMP;
        alive = true;
    }

    public synchronized boolean move(Direction d) {
        if (isDead())
            return false;
        int dx = 0;
        int dy = 0;
        switch (d) {
            case Down:
                dx = +1;
                break;
            case Up:
                dx = -1;
                break;
            case Left:
                dy = -1;
                break;
            case Right:
                dy = 1;
                break;
            default:
                break;
        }
        boolean ret;
        // System.out.println("prepare move "+d);
        gridLock.lock();
        // System.out.println("Dead: "+isDead());
        Grid dest = new Grid(grid.getX() + dx, grid.getY() + dy);
        if (BattleGround.move(grid, dest)) {
            ret = true;
            grid = dest;
            // System.out.println("move Success grid"+grid.getX()+" "+grid.getY());
        } else {
            ret = false;
            // System.out.println("move fail");
        }
        gridLock.unlock();
        return ret;
    }

    abstract public void specialAttack();

    public void commonAttack() {
        if (isDead()) {
            return;
        }
        Pixel p;
        if (camp == Camp.Hulu) {
            p = Screen.gridToRightBulletPixel(grid);
        } else {
            p = Screen.gridToLeftBulletPixel(grid);
        }
        BattleGround.bulletThreadPool.execute(new Bullet(this, strength, bulletSpeed, p, bulletRadius, bulletColor));
    }

    public void hit(Character dest, int damage) {
        if (dest.camp == camp) {
            return;
        }
        setMP(Math.min(getMP() + damage / 2, maxMP));
    }

    public void hurt(Character src, int damage) {
        if (src.camp == camp) {
            return;
        }
        int nextHP = getHP() - damage;
        if (nextHP <= 0) {
            setAliveDead();
            gridLock.lock();
            BattleGround.setDead(grid);
            gridLock.unlock();
        } else {
            setMP(Math.min(getMP() + damage / 4, maxMP));
            setHP(nextHP);
        }
    }

    public int getHP() {
        HPLock.lock();
        int ret = HP;
        HPLock.unlock();
        return ret;
    }

    public void setHP(int hP) {
        HPLock.lock();
        HP = hP;
        HPLock.unlock();
    }

    public int getMP() {
        MPLock.lock();
        int ret = MP;
        MPLock.unlock();
        return ret;
    }

    public void setMP(int mP) {
        MPLock.lock();
        MP = mP;
        MPLock.unlock();
    }

    public boolean isDead() {
        aliveLock.lock();
        boolean ret = alive;
        aliveLock.unlock();
        return !ret;
    }

    public void setAliveDead() {
        aliveLock.lock();
        alive = false;
        aliveLock.unlock();
    }

    public void setGrid(Grid g) {
        gridLock.lock();
        grid.setX(g.getX());
        grid.setY(g.getY());
        gridLock.unlock();
    }

    public Grid getGrid() {
        gridLock.lock();
        Grid ret = new Grid(grid);
        gridLock.unlock();
        return ret;
    }

    public int getMaxHP() {
        return maxHP;
    }
}