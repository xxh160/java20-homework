package card;

import java.net.URL;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Card {

    protected int price;

    protected Image image;

    protected int position; //位于卡牌区第几号位置

    protected ImageView imageView; //专门画卡片
    protected double lastEventX, lastEventY, lastX, lastY; //控制卡片拖动的变量

    public Card() {
        URL url = getClass().getClassLoader().getResource("huluwa_card.png");
        System.out.println(url);
        image = new Image(url.toString());
        imageView = new ImageView();
        imageView.setImage(image);

        System.out.println("card设置鼠标事件");
        imageView.setOnMousePressed(e->{
            lastX = imageView.getTranslateX();
            lastY = imageView.getTranslateY();
            lastEventX = e.getSceneX();
            lastEventY = e.getSceneY();
            System.out.println("mouse pressed, v.x: " + lastX + ", v.y: "
            + lastY + ", e.x: " + lastEventX + ", e.y: " + lastEventY);
        });

        imageView.setOnMouseDragged(e->{
            //System.out.println("mouse dragged");
            double dx = lastEventX - e.getSceneX();
            double dy = lastEventY - e.getSceneY();
            double nx = lastX - dx;
            double ny = lastY - dy;
            System.out.println("nx: " + nx + ", ny: " + ny);
            imageView.setTranslateX(nx);
            imageView.setTranslateY(ny);
        });

        imageView.setOnMouseReleased(e->{
            System.out.println("mouse released, sceney: " + e.getSceneY());

            if (e.getSceneY() < 150 && e.getSceneY() > 100) {
                //do nothing
            }
            else {
                imageView.setTranslateX(0);
                imageView.setTranslateY(0);
                //imageView.setX(initX);
                //imageView.setY(initY);
            }
        });
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
        imageView.setX(150+(image.getWidth()+10)*position);
        imageView.setY(400);
    }

    public void addToPane(Pane pane) {
        pane.getChildren().add(imageView);
    }
}