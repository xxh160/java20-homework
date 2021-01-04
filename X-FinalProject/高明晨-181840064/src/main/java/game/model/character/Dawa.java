package game.model.character;

import game.BattleGround;
import game.model.Bullet;
import game.model.Character;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;

public class Dawa extends Character {
    public Dawa() {
        super(new Grid(1, 0), 100, 100, 30, 10, Color.RED, Camp.Hulu);
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        BattleGround.bulletThreadPool.execute(
                new Bullet(this, 100, bulletSpeed, Screen.gridToRightBulletPixel(grid), bulletRadius * 2, bulletColor));
    }
}