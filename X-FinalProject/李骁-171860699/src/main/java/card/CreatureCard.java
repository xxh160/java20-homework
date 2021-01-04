package card;

import creature.Creature;
import view.MainCanvas;

public class CreatureCard extends DraggableCard {

    Creature creature;

    public CreatureCard(Creature creature) {
        this.creature = creature;
        this.price = creature.getPrice();
        loadImage(creature.getCardImage());
    }

    @Override
    protected void cardAction() {
        //添加我方生物
        runway.addToMyCreatures(creature);
        //发包给敌方
        MainCanvas.sendMessage("add" + creature.getName() + "," + runway.getId());
        //添加记录
        MainCanvas.recorder.recordOperation("i", "add"+creature.getName(), runway.getId());
    }

    @Override
    protected boolean canReleaseOnRunway() {
        //判断我方队尾和对方队头是否在我方末端
        //仅考虑我方即可，对方的不用考虑
        
        return runway.canReleaseMyCreature();
    }
}