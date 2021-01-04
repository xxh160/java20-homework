package game.model.character;

import game.BattleGround;
import game.model.Bullet;
import game.model.Character;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;
import tool.point.Pixel;

public class Erwa extends Character {
    public Erwa() {
        super(new Grid(2, 0), 80, 100, 10, 20, Color.ORANGE, Camp.Hulu);
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        Pixel p = Screen.gridToRightBulletPixel(grid);
        for (int i = 0; i < 5; i++) {
            BattleGround.bulletThreadPool.execute(new Bullet(this, 10, bulletSpeed, p, bulletRadius, bulletColor));
            p.setX(p.getX() + 50);
        }

    }
}