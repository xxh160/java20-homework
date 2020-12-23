package card;

import java.util.ArrayList;
import creature.Creature;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;

public class CardField {
    private ArrayList<Card> cards;

    private final int cardFieldSize = 5;

    private int money; //拥有的金钱

    private final int posX = 300;

    private final int posY = 500;

    private Pane root; //所在pane

    public CardField(Pane root) {
        cards = new ArrayList<Card>();
        this.root = root;
    }

    public void removeCard(Card card) {
        root.getChildren().remove(card.getImageView()); //清理掉
        cards.remove(card);
    }

    public void removeCard(int index) {
        if (index < cards.size()) {
            Card card = cards.get(index);
            root.getChildren().remove(card.getImageView());
            cards.remove(index);
        }
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
            
            card.drawCard();
            card.addToPane(root); //添加到pane里
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getCardFieldSize() {
        return cardFieldSize;
    }
}