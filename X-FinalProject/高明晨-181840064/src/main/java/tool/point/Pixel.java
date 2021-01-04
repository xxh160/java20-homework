package tool.point;

import tool.Point;

public class Pixel extends Point {
    public Pixel(int x, int y) {
        super(x, y);
    }

    public Pixel(Pixel p) {
        super(p);
    }
}