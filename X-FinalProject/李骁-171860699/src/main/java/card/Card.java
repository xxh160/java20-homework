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

    protected int price; // 卡牌价格

    protected int position; // 位于卡牌区第几号位置

    protected Image image; // 卡牌图片

    protected ImageView imageView; // 画卡牌区里的卡

    public Card() {

        imageView = new ImageView(); // 要放在load之前，不然iv还没加载

        price = 2; //TODO 由具体子类自己设置
    }

    protected void loadImage(String imageName) {
        URL url = getClass().getClassLoader().getResource(imageName);
        System.out.println("loadImage: " + url);
        image = new Image(url.toString());
        imageView.setImage(image); // TODO 这里会不会导致脱节
    }

    protected void loadImage(Image image) {
        this.image = image;
        imageView.setImage(image);
    }

    public static Card createRandomCard() {
        Random rand = new Random();
        Card card;
        switch (rand.nextInt(6)) {
            case 0:
                card = new CreatureCard(new Creature());
                break; // TODO 改实际生物
            case 1:
                card = new PropCardCostAddN(1);
                break;
            case 2:
                card = new PropCardCostMinusN(1);
                break;
            case 3:
                card = new PropCardClearRunway();
                break;
            case 4:
                card = new PropCardKillEnemyHead();
                break;
            case 5:
                card = new PropCardFreeze();
                break;
            default:
                card = new PropCardClearRunway();
                break;
        }
        return card;
    }

    protected void cardAction() {

    }

    public void drawCard() {
        imageView.setImage(image);
        imageView.setX(MainCanvas.cardField.getPosX() + (image.getWidth() + 10) * position);
        imageView.setY(MainCanvas.cardField.getPosY());
    }

    public void addToPane(Pane pane) {
        pane.getChildren().add(imageView);
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

    public int getPosition() {
        return position;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}