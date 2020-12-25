package card;

import java.util.ArrayList;
import creature.Creature;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import view.MainCanvas;
import javafx.scene.text.Font;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.Button;


public class CardField implements Runnable {
    private ArrayList<Card> cards;

    private final int cardFieldSize = 5;

    private int money = 10; // 拥有的金钱

    private Text moneyText; // 金钱的文字显示

    private Button controller; //按Q退出的控制器

    private final int freshCost = 2; // D牌花费

    private final int posX = 150;

    private final int posY = 400;

    private Pane root; // 所在pane

    public CardField(Pane root) {
        cards = new ArrayList<Card>();
        this.root = root;
        moneyText = new Text("金钱数：" + money);
        moneyText.setLayoutX(posX);
        moneyText.setLayoutY(posY - 20);
        moneyText.setFont(new Font("Bold", 20));
        //moneytext设置D牌
        //不是control类型无法设置事件

        root.getChildren().add(moneyText);

        MainCanvas.exec.submit(this); // 线程开始


        //初始化控制器按钮 TODO 换个控制器
        //TODO 还是没解决D牌时卡一下的问题
        controller = new Button();
        controller.setLayoutX(50);
        controller.setLayoutY(400);
        controller.setPrefWidth(1);
        controller.setPrefHeight(1);
        //controller.setVisible(false);
        controller.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("按下了：" + event.getCode().name());
                if (event.getCode() == KeyCode.D) {
                    System.out.println("换牌");
                    freshAllCards();
                }
                else if (event.getCode() == KeyCode.G) {
                    System.out.println("给敌方加人");
                    MainCanvas.runways.get(1).addYourCreature(new Creature());
                }
            }
        });
        root.getChildren().add(controller);
        
    }

    public void freshAllCards() {
        // D牌，有钱才能D
        if (money >= freshCost) {
            money = money - freshCost;
            removeAllCards();
            fillCards();
        }
        else {
            //TODO 提示没钱
            System.out.println("没钱D牌");
        }
    }

    public void removeCard(Card card) {
        root.getChildren().remove(card.getImageView()); // 清理掉
        synchronized (cards) {
            cards.remove(card);
        }
    }

    public void removeCard(int index) {
        if (index < cards.size()) {
            Card card = cards.get(index);
            root.getChildren().remove(card.getImageView());
            synchronized (cards) {
                cards.remove(index);
            }
        }
    }

    public void removeAllCards() {
        synchronized (cards) {
            for (int i = cards.size() - 1; i >= 0; i--) {
                removeCard(i);
            }
        }
        // cards.clear();
    }

    public void fillCards() {
        int empty = cardFieldSize - cards.size();
        System.out.println("卡牌需要填充" + Integer.toString(empty) + "张");
        if (empty == 0)
            return;
        // 补充消耗的卡，TODO 随机
        for (int i = 0; i < empty; i++) {
            Card card = Card.createRandomCard();
            cards.add(card);
            card.setPosition(cards.indexOf(card));

            card.drawCard();
            card.addToPane(root); // 添加到pane里
        }
    }

    public void cardsCostPlusN(int n) {
        synchronized(cards) {
            /*for (int i = 0; i < cards.size(); i++) {
                cards.get(i).setPrice(cards.get(i).getPrice() + n);
            }*/
            cards.forEach(c->c.setPrice(c.getPrice() + n));
        }
    }

    public void cardsCostMinusN(int n) {
        synchronized(cards) {
            //最小减到0
            cards.forEach(c->c.setPrice(Math.max(0, c.getPrice() - n)));
        }
    }

    public void run() {
        while (!Thread.interrupted()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    draw();
                    update();
                }
            });
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("卡牌区线程结束");
    }

    public void draw() {
        moneyText.setText("金钱数：" + money);
    }

    public void update() {
        money = money + 1;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getCardFieldSize() {
        return cardFieldSize;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }
}