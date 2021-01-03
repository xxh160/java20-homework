package runway;

import java.util.ArrayList;

import creature.Creature;
import view.MainCanvas;

public class Runway {

    private ArrayList<Creature> myCreatures; // 跑道上的我方生物

    private ArrayList<Creature> enemyCreatures; // 跑道上的对方生物

    private int posX, posY;

    private int width, length;

    private int id; //跑道编号

    // private ImageView imageView; //跑道图片

    public Runway(int posX, int posY, int width, int length, int id) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.length = length;
        this.id = id;
        myCreatures = new ArrayList<Creature>();
        enemyCreatures = new ArrayList<Creature>();
    }

    public void addToMyCreatures(Creature creature) {
        // 将一个生物插入到我方阵营，并设置位置，方向，加入pane，启动线程
        creature.setPosX(posX - 2*creature.getFigureSize());
        creature.setPosY(posY);
        creature.setBelongToMe(true);
        creature.setAlive(true);
        myCreatures.add(creature);
        creature.setRunway(this);
        creature.addToPane(MainCanvas.root);
        MainCanvas.exec.submit(creature);
    }

    public void addToEnemyCreatures(Creature creature) {
        // 将一个生物插入到敌方阵营，并设置位置，方向，加入pane，启动线程
        //System.out.println("addToEnemyCreatures begin");
        creature.flipImage(); //敌人的话要翻转图片
        //System.out.println("addToEnemyCreatures 1");
        creature.setPosX(posX + length);
        //System.out.println("addToEnemyCreatures 2");
        creature.setPosY(posY);
        //System.out.println("addToEnemyCreatures 3");
        creature.setBelongToMe(false);
        //System.out.println("addToEnemyCreatures 4");
        creature.setAlive(true);
        //System.out.println("addToEnemyCreatures 5");
        enemyCreatures.add(creature);
        //System.out.println("addToEnemyCreatures 6");
        creature.setRunway(this);
        //System.out.println("addToEnemyCreatures 7");
        creature.addToPane(MainCanvas.root);
        //System.out.println("addToEnemyCreatures 8");
        MainCanvas.exec.submit(creature);
        //System.out.println("addToEnemyCreatures end");
    }

    public void removeFromMyCreatures(Creature creature) {
        // 将一个生物从我方队伍移除，让生物死亡，关闭线程和定时器，并从pane中移除
        myCreatures.remove(creature);
        creature.Die();
        creature.removeFromPane(MainCanvas.root);
    }

    public void removeFromEnemyCreatures(Creature creature) {
        // 将一个生物从敌方队伍移除，让生物死亡，关闭线程和定时器，并从pane中移除
        enemyCreatures.remove(creature);
        creature.Die();
        creature.removeFromPane(MainCanvas.root);
    }

    public void removeAllCreatures() {
        // 移除所有creature，并停止线程，TODO 线程安全
        synchronized (myCreatures) {
            for (int i = myCreatures.size() - 1; i >= 0; i--) {
                removeFromMyCreatures(myCreatures.get(i));
            }
        }
        synchronized (enemyCreatures) {
            for (int i = enemyCreatures.size() - 1; i >= 0; i--) {
                removeFromEnemyCreatures(enemyCreatures.get(i));
            }
        }
    }

    public void killMyHead() {
        synchronized (myCreatures) {
            if (myCreatures.size() > 0) {
                System.out.println("击杀我方队头");
                removeFromMyCreatures(myCreatures.get(0));
            } else {
                System.out.println("我方无队头");
            }
        }
    }

    public void killEnemyHead() {
        synchronized (enemyCreatures) {
            if (enemyCreatures.size() > 0) {
                System.out.println("击杀敌方队头");
                removeFromEnemyCreatures(enemyCreatures.get(0));
            } else {
                System.out.println("敌方无队头");
            }
        }
    }

    public void freezeEnemyCreatures() {
        // 冰冻敌方
        System.out.println("freezeEnemyCreatures");
        for (Creature c : enemyCreatures) {
            c.freeze(3000);
        }
    }

    public void freezeMyCreatures() {
        // 冰冻我方生物3000ms
        System.out.println("freezeMyCreatures");
        for (Creature c : myCreatures) {
            c.freeze(3000);
        }
    }

    public boolean canReleaseMyCreature() {
        //判断我方队尾和敌方队头即可
        //(mylen == 0 || tail.posx > posx) && (enemylen == 0 || enemy.head.posx > posx)
        int myLen = myCreatures.size();
        int enemyLen = enemyCreatures.size();
        return (myLen == 0 || myCreatures.get(myLen - 1).getPosX() > this.posX)
        && (enemyLen == 0 || enemyCreatures.get(0).getPosX() > this.posX);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Creature> getEnemyCreatures() {
        return enemyCreatures;
    }

    public ArrayList<Creature> getMyCreatures() {
        return myCreatures;
    }
}