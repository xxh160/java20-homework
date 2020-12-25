package card;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import creature.Creature;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import runway.Runway;
import java.util.ArrayList;
import view.*;

public abstract class Card {

    protected int price; //卡牌价格

    protected int position; // 位于卡牌区第几号位置

    protected Image image; //卡牌图片

    protected ImageView imageView; // 画卡牌区里的卡

    protected double lastEventX, lastEventY, lastX, lastY; // 控制卡片拖动的变量

    public Card() {
        
        imageView = new ImageView(); //要放在load之前，不然iv还没加载
        
        price = 2;
    }

    protected void loadImage(String imageName) {
        URL url = getClass().getClassLoader().getResource(imageName);
        System.out.println(url);
        image = new Image(url.toString());
        imageView.setImage(image); //TODO 这里会不会导致脱节
    }

    public static Card createRandomCard() {
        Random rand = new Random();
        Card[] cardPool = { 
            new CreatureCard(new Creature()), // TODO 改实际生物
            new PropCardCostAddN(1),
            new PropCardCostMinusN(1),
            new PropCardClearRunway(),
            new PropCardKillEnemyHead(),
        };
        return cardPool[rand.nextInt(cardPool.length)];
    }

    public int getPrice() {
        return price;
    }

    public Image getImage() {
        return image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void drawCard() {
        imageView.setImage(image);
        imageView.setX(MainCanvas.cardField.getPosX() + (image.getWidth() + 10) * position);
        imageView.setY(MainCanvas.cardField.getPosY());
    }

    public void addToPane(Pane pane) {
        pane.getChildren().add(imageView);
    }

    public int getPosition() {
        return position;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}