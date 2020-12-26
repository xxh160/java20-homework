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
        runway.addMyCreature(creature);
    }
}