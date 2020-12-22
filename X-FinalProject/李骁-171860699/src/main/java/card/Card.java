package card;

import java.net.URL;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Card {

    private int price;

    private Image image;

    private int position; //位于卡牌区第几号位置

    public Card() {
        URL url = getClass().getClassLoader().getResource("huluwa_card.png");
        System.out.println(url);
        image = new Image(url.toString());
    }

    public int getPrice() {
        return price;
    }

    public Image getImage() {
        return image;
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

    public void drawCard(ImageView imageView) {
        imageView.setImage(image);
        imageView.setX(150+(image.getWidth()+10)*position);
        imageView.setY(400);
    }
}