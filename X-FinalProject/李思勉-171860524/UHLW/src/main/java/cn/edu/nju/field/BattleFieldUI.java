package cn.edu.nju.field;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BattleFieldUI extends Canvas{
    private static Image terrainImg = null;
    private static int terrainImgCols = 5;
    private static final int tileWidth = 32;
    private static final int tileHeight = 32;
    private boolean isRunning = true;
    private GraphicsContext gContext;
    public void init(){
        terrainImg = new Image(getClass().getResourceAsStream("/img/floor.png"));
        terrainImgCols = (int) terrainImg.getWidth()/tileWidth;
    }
    public void draw(BattleField battleField) {
        GraphicsContext gc = getGraphicsContext2D();
        int mapWidth = battleField.getRowNum();
        int mapHeight = battleField.getColNum();
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int px = battleField.getTerrain(y,x).getTypeInt() % terrainImgCols;
                int py = battleField.getTerrain(y,x).getTypeInt() / terrainImgCols;
                gc.drawImage(terrainImg, px * tileWidth, py * tileHeight, tileWidth, tileHeight, x * tileWidth, y
                        * tileHeight, tileWidth, tileHeight);
            }
        }
    }


}
