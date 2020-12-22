package card;

import java.util.ArrayList;

import creature.Creature;

public class CardField {
    private ArrayList<Card> cards;

    private final int cardFieldSize = 5;

    private int money; //拥有的金钱

    private final int posX = 300;

    private final int posY = 500;

    public CardField() {
        cards = new ArrayList<Card>();
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void removeCard(int index) {
        cards.remove(index);
    }

    public void removeAllCards() {
        cards.clear();
    }

    public void fillCards() {
        int empty = cardFieldSize - cards.size();
        System.out.println("卡牌需要填充" + Integer.toString(empty) + "张");
        if (empty == 0) return;
        //补充消耗的卡，TODO 定时，随机
        for (int i = 0; i < empty; i++) {
            Card card = new CreatureCard(new Creature());
            cards.add(card);
            card.setPosition(cards.indexOf(card));
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getCardFieldSize() {
        return cardFieldSize;
    }
}