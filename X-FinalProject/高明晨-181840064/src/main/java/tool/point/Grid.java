package tool.point;

import tool.Point;

public class Grid extends Point {
    public Grid(int x, int y) {
        super(x, y);
    }

    public Grid(Grid p) {
        super(p);
    }
}