package card;

import creature.Creature;
import view.MainCanvas;

public class CreatureCard extends DraggableCard {

    Creature creature;

    public CreatureCard(Creature creature) {
        this.creature = creature;
        loadImage(creature.getCardImage());
    }

    @Override
    protected void cardAction() {
        //添加我方生物，告诉敌方
        runway.addToMyCreatures(creature);
        //TODO 改统一的发包
        if (MainCanvas.server != null) {
        MainCanvas.server.sendMessage("add0");
        }
        else if (MainCanvas.client != null) {
            MainCanvas.client.sendMessage("add1");
        }
    }

    @Override
    protected boolean canReleaseOnRunway() {
        //判断我方队尾和对方队头是否在我方末端
        //仅考虑我方即可，对方的不用考虑
        
        return runway.canReleaseMyCreature();
    }
}