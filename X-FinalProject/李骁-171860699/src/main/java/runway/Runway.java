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
        myCreatures.add(creature);
        creature.setRunway(this);
        creature.addToPane(MainCanvas.root);
    }

    public void addYourCreature(Creature creature) {
        yourCreatures.add(creature);
        creature.setRunway(this);
        creature.addToPane(MainCanvas.root);
    }

    public ArrayList<Creature> getYourCreatures() {
        return yourCreatures;
    }

    public ArrayList<Creature> getMyCreatures() {
        return myCreatures;
    }
}