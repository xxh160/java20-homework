package game.view;

import game.model.Character;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tool.Screen;
import tool.point.Grid;
import tool.point.Pixel;

public class CharacterView {
    private Character character;
    private ImageView view;
    private Rectangle red;
    private Rectangle blue;
    private boolean isRemove;

    public CharacterView(Character character, String url) {
        this.character = character;
        Image img = new Image(getClass().getClassLoader().getResource(url).toString());
        view = new ImageView(img);
        red = new Rectangle(0, 0, (character.getHP() + 1) / 2, 5);
        blue = new Rectangle(0, 0, (character.getMP() + 1) / 2, 5);
        red.setFill(Color.RED);
        blue.setFill(Color.BLUE);
        isRemove = false;
        update();
    }

    public void addScene(Pane root) {
        root.getChildren().add(view);
        root.getChildren().add(blue);
        root.getChildren().add(red);
    }

    public void removeScene(Pane root) {
        if (isRemove == false) {
            red.setWidth(0);
            root.getChildren().remove(view);
            root.getChildren().remove(blue);
            root.getChildren().remove(red);
            // System.out.println("in remove scence view: "+view);
            isRemove = true;
        }

    }

    public void show(Grid g, double RedWidth, double BlueWidth) {
        character.setGrid(g);
        Pixel characterP = Screen.gridToCharacterPixel(g);
        Pixel blueP = Screen.gridToMPPixel(g);
        Pixel redP = Screen.gridToHPixel(g);
        blue.setWidth(BlueWidth);
        red.setWidth(RedWidth);
        view.setLayoutX(characterP.getX());
        view.setLayoutY(characterP.getY());
        blue.setLayoutX(blueP.getX());
        blue.setLayoutY(blueP.getY());
        red.setLayoutX(redP.getX());
        red.setLayoutY(redP.getY());
    }

    public void update() {
        Grid grid = character.getGrid();
        show(grid, (double) character.getHP() / 2, (double) character.getMP() / 2);
    }

    public Rectangle getRed() {
        return red;
    }

    public Rectangle getBlue() {
        return blue;
    }

    public Grid getGrid() {
        return character.getGrid();
    }
}