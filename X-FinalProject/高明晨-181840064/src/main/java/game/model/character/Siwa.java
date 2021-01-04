package game.model.character;

import java.util.Random;

import game.BattleGround;
import game.model.Bullet;
import game.model.Character;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;
import tool.point.Pixel;

public class Siwa extends Character {
    public Siwa() {
        super(new Grid(4, 0), 100, 100, 20, 10, Color.GREENYELLOW, Camp.Hulu);
    }

    @Override
    public void commonAttack() {
        if (isDead()) {
            return;
        }
        Pixel p;
        p = Screen.gridToRightBulletPixel(grid);
        int damage = strength;
        Random rand = new Random(3);
        if (rand.nextInt(4) == 0) {
            damage = damage * 2;
        }
        BattleGround.bulletThreadPool.execute(new Bullet(this, damage, bulletSpeed, p, bulletRadius, bulletColor));
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        int damage;
        Random rand = new Random(3);

        for (int i = 0; i < 8; i++) {
            if (rand.nextInt(4) == 0) {
                damage = 10;
            } else {
                damage = 20;
            }
            BattleGround.bulletThreadPool.execute(new Bullet(this, damage, bulletSpeed,
                    Screen.gridToRightBulletPixel(new Grid(i, 5)), bulletRadius, bulletColor));
        }

    }
}