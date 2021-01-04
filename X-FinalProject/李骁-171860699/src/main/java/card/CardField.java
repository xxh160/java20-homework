package card;

import creature.Creature;
import creature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

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
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Button;


public class CardField implements Runnable {
    private List<Card> cards;

    private final int cardFieldSize = 5;

    private int money = 30; // 拥有的金钱

    private Text moneyText; // 金钱的文字显示

    private Button controller; //按Q退出的控制器

    private final int freshCost = 2; // D牌花费

    private final int posX = 150;

    private final int posY = 400;

    private Pane root; // 所在pane

    public CardField(Pane root) {
        //cards = new ArrayList<Card>();
        cards = new LinkedList<Card>();
        this.root = root;
        moneyText = new Text("金钱数：" + money);
        moneyText.setLayoutX(posX - 120);
        moneyText.setLayoutY(posY + 20);
        moneyText.setFont(new Font("Bold", 20));
        //moneytext设置D牌
        //不是control类型无法设置事件

        //root.getChildren().add(moneyText);
        MainCanvas.addToPane(moneyText);

        MainCanvas.exec.submit(this); // 线程开始


        //初始化控制器按钮 TODO 换个控制器
        controller = new Button("刷新手牌(" + freshCost + "块)\n快捷键(D)");
        controller.setFont(new Font("Bold", 15));
        controller.setLayoutX(posX - 130);
        controller.setLayoutY(posY + 50);
        controller.setPrefWidth(120);
        controller.setPrefHeight(50);
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
                    MainCanvas.runwayField.getRunways().get(1).addToEnemyCreatures(new Dawa());
                }
            }
        });
        //点击D牌按钮
        controller.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                freshAllCards();
            }
        });
        //root.getChildren().add(controller);
        MainCanvas.addToPane(controller);
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
        //root.getChildren().remove(card.getImageView()); // 清理掉
        MainCanvas.removeFromPane(card.getImageView());
        synchronized (cards) {
            cards.remove(card); //O(n)
        }
    }

    public void removeCard(int index) {
        if (index < cards.size()) {
            Card card = cards.get(index); //O(n)
            //root.getChildren().remove(card.getImageView());
            MainCanvas.removeFromPane(card.getImageView());
            synchronized (cards) {
                cards.remove(index); //O(n)
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
        // 补充消耗的卡，TODO 随机、根据阵营
        for (int i = 0; i < empty; i++) {
            Card card = Card.createRandomCard();
            //Card card = new PropCardClearRunway();
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
        //TODO 暂时不加了
        //money = money + 1;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getCardFieldSize() {
        return cardFieldSize;
    }

    public void setCards(List<Card> cards) {
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