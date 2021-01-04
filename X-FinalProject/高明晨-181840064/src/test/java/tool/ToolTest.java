package tool;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import tool.point.Grid;
import tool.point.Pixel;

public class ToolTest {
    @Test
    public void testPoint() {
        Random rand = new Random();
        int x = rand.nextInt(100);
        int y = rand.nextInt(100);
        Point p = new Point(x, y);
        assertEquals(x, p.getX());
        assertEquals(y, p.getY());
        Point q = new Point(p);
        assertEquals(x, q.getX());
        assertEquals(y, q.getY());
    }

    @Test
    public void ScreenCharacterPointTest() {
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 11; y++) {
                Grid g = new Grid(x, y);
                Pixel p = Screen.gridToCharacterPixel(g);
                Grid g2 = Screen.pixelToGrid(p);
                assertEquals(x, g2.getX());
                assertEquals(y, g2.getY());
            }
    }

    @Test
    public void ScreenBulletPointTest() {
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 6; y++) {
                Grid g = new Grid(x, y);
                Pixel p = Screen.gridToCharacterPixel(g);
                Grid g2 = Screen.pixelToGrid(p);
                p = Screen.gridToRightBulletPixel(g);
                g2 = Screen.pixelToGrid(p);
                assertEquals(x, g2.getX());
                assertEquals(y + 1, g2.getY());
            }

        for (int x = 0; x < 8; x++)
            for (int y = 6; y < 11; y++) {
                Grid g = new Grid(x, y);
                Pixel p = Screen.gridToCharacterPixel(g);
                Grid g2 = Screen.pixelToGrid(p);
                p = Screen.gridToLeftBulletPixel(g);
                g2 = Screen.pixelToGrid(p);
                assertEquals(x, g2.getX());
                assertEquals(y - 1, g2.getY());
            }

    }
}