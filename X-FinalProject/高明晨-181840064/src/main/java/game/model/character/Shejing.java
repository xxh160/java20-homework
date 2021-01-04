package game.model.character;

import game.BattleGround;
import game.model.Bullet;
import game.model.Character;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;
import tool.point.Pixel;

public class Shejing extends Character {
    public Shejing() {
        super(new Grid(4, 11), 100, 200, 20, 10, Color.PINK, Camp.Yaojing);
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        Pixel p;
        for (int i = 0; i < 8; i++)
            for (int j = 1; j < 7; j++) {
                p = Screen.gridToLeftBulletPixel(new Grid(i, j));
                BattleGround.bulletThreadPool.execute(new Bullet(this, 10, bulletSpeed, p, bulletRadius, bulletColor));
            }

    }
}