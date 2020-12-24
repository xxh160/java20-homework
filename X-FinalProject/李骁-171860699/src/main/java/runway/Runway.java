package runway;

import java.util.ArrayList;

import creature.Creature;
import view.MainCanvas;

public class Runway {

    private ArrayList<Creature> myCreatures; //跑道上的我方生物

    private ArrayList<Creature> yourCreatures; //跑道上的对方生物

    private int posX, posY;

    private int width, length;

    //private ImageView imageView; //跑道图片

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

    public Runway(int posX, int posY, int width, int length) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.length = length;
        myCreatures = new ArrayList<Creature>();
        yourCreatures = new ArrayList<Creature>();
    }

    public void addMyCreature(Creature creature) {
        //将一个生物插入到我方阵营，并设置位置，方向，加入pane，启动线程
        creature.setPosX(posX);
        creature.setPosY(posY);
        creature.setBelongToMe(true);
        creature.setRunning(true);
        myCreatures.add(creature);
        creature.setRunway(this);
        creature.addToPane(MainCanvas.root);
        MainCanvas.exec.submit(creature);
    }

    public void addYourCreature(Creature creature) {
        //将一个生物插入到敌方阵营，并设置位置，方向，加入pane，启动线程
        creature.setPosX(posX + length);
        creature.setPosY(posY);
        creature.setBelongToMe(false);
        creature.setRunning(true);
        yourCreatures.add(creature);
        creature.setRunway(this);
        creature.addToPane(MainCanvas.root);
        MainCanvas.exec.submit(creature);
    }

    public void removeFromMyCreature(Creature creature) {
        //将一个生物从我方队伍移除，并从pane中移除，线程已run完
        myCreatures.remove(creature);
        MainCanvas.root.getChildren().remove(creature.getImageView());
    }

    public void removeFromYourCreature(Creature creature) {
        //将一个生物从敌方队伍移除，并从pane中移除，线程已run完
        yourCreatures.remove(creature);
        MainCanvas.root.getChildren().remove(creature.getImageView());
    }

    public ArrayList<Creature> getYourCreatures() {
        return yourCreatures;
    }

    public ArrayList<Creature> getMyCreatures() {
        return myCreatures;
    }
}