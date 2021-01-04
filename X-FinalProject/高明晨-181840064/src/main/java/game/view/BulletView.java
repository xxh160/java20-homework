package game.view;

import game.model.Bullet;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import tool.point.Pixel;

public class BulletView {
    public Bullet bullet;
    public Circle view;
    private transient boolean alive;

    public BulletView(Bullet bullet) {
        this.bullet = bullet;
        view = new Circle(0, 0, bullet.getRadius());
        view.setFill(bullet.getColor());
        alive = true;
        update();
    }

    public boolean isDead() {
        return !alive;
    }

    public void setDead() {
        alive = false;
    }

    public void addScene(Pane root) {
        Platform.runLater(() -> {
            root.getChildren().add(view);
        });
    }

    public void removeScene(Pane root) {
        Platform.runLater(() -> {
            root.getChildren().remove(view);
        });
    }

    public void update() {
        Pixel p = bullet.getPixel();
        view.setLayoutX(p.getX());
        view.setLayoutY(p.getY());
    }

    public Circle getView() {
        return view;
    }
}