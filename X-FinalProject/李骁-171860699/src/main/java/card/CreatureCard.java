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
        runway.addToMyCreatures(creature);
    }

    @Override
    protected boolean canReleaseOnRunway() {
        //判断我方队尾和对方队头是否在我方末端
        //仅考虑我方即可，对方的不用考虑
        
        return runway.canReleaseMyCreature();
    }
}