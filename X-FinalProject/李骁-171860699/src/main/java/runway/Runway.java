package runway;

import java.util.ArrayList;

import creature.Creature;

public class Runway {

    private ArrayList<Creature> myCreatures; //跑道上的我方生物

    private ArrayList<Creature> yourCreatures; //跑道上的对方生物

    public Runway() {
        myCreatures = new ArrayList<Creature>();
        yourCreatures = new ArrayList<Creature>();
    }

    public void addMyCreature(Creature creature) {
        myCreatures.add(creature);
        creature.setRunway(this);
    }

    public void addYourCreature(Creature creature) {
        yourCreatures.add(creature);
        creature.setRunway(this);
    }

    public ArrayList<Creature> getYourCreatures() {
        return yourCreatures;
    }

    public ArrayList<Creature> getMyCreatures() {
        return myCreatures;
    }
}