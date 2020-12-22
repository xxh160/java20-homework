package card;

import creature.Creature;

public class CreatureCard extends Card {

    Creature creature;

    public CreatureCard(Creature creature) {
        this.creature = creature;
    }
}