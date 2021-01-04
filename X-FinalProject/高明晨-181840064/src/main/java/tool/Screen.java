package tool;

import tool.point.Grid;
import tool.point.Pixel;

public class Screen {
    public static Grid pixelToGrid(Pixel p) {
        return new Grid((p.getY() - 90) / 103, (p.getX() - 93) / 103);
    }

    public static Pixel gridToCharacterPixel(Grid g) {
        return new Pixel(110 + 102 * g.getY(), 113 + 102 * g.getX());
    }

    public static Pixel gridToMPPixel(Grid g) {
        return new Pixel(115 + 102 * g.getY(), 105 + 102 * g.getX());
    }

    public static Pixel gridToHPixel(Grid g) {
        return new Pixel(115 + 102 * g.getY(), 97 + 102 * g.getX());
    }

    public static Pixel gridToFlagPixel(Grid g) {
        return new Pixel(140 + 102 * g.getY(), 94 + 102 * g.getX());
    }

    public static Pixel gridToLeftBulletPixel(Grid g) {
        return new Pixel(92 + 103 * g.getY(), 140 + 103 * g.getX());
    }

    public static Pixel gridToRightBulletPixel(Grid g) {
        return new Pixel(196 + 103 * g.getY(), 140 + 103 * g.getX());
    }
}