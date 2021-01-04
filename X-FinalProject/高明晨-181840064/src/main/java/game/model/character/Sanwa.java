package game.model.character;

import java.util.Random;

import game.BattleGround;
import game.model.Bullet;
import game.model.Character;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;

public class Sanwa extends Character {
    public Sanwa() {
        super(new Grid(3, 0), 100, 100, 20, 10, Color.YELLOW, Camp.Hulu);
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        BattleGround.bulletThreadPool.execute(
                new Bullet(this, 60, bulletSpeed, Screen.gridToRightBulletPixel(grid), bulletRadius * 2, bulletColor));
    }

    @Override
    public void hurt(Character src, int damage) {
        super.hurt(src, damage);
        if (src != this) {
            Random rand = new Random(2);
            if (rand.nextInt(4) == 0) {
                src.hurt(this, damage * 2);
            }
        }

    }
}