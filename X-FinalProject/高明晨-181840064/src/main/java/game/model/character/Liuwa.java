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

public class Liuwa extends Character {
    public Liuwa() {
        super(new Grid(6, 0), 100, 100, 20, 10, Color.VIOLET, Camp.Hulu);
    }

    @Override
    public void hurt(Character src, int damage) {
        Random rand = new Random(5);
        if (rand.nextInt(5) == 0) {
            return;
        }
        int nextHP = getHP() - damage;
        if (nextHP <= 0) {
            setAliveDead();
            gridLock.lock();
            assert BattleGround.getCharacter(grid) != null;
            BattleGround.setDead(grid);
            gridLock.unlock();
        } else {
            setMP(Math.min(getMP() + damage / 4, maxMP));
            setHP(nextHP);
        }
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        Random rand = new Random(5);
        int x, y;
        Pixel pixel;
        for (int i = 0; i < 5; i++) {
            x = rand.nextInt(7);
            y = rand.nextInt(6) + 5;
            pixel = Screen.gridToRightBulletPixel(new Grid(x, y));
            BattleGround.bulletThreadPool
                    .execute(new Bullet(this, 100, bulletSpeed, pixel, bulletRadius * 2, bulletColor));
        }

    }
}