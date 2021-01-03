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

public class Huangchong extends Character {
    public Huangchong() {
        super(new Grid(1, 11), 100, 50, 10, 20, Color.GOLD, Camp.Yaojing);
    }

    @Override
    public void commonAttack() {
        if (isDead()) {
            return;
        }
        Pixel p;
        p = Screen.gridToLeftBulletPixel(grid);
        int damage = strength;
        Random rand = new Random(6);
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
        int damage = strength;
        Random rand = new Random(6);
        if (rand.nextInt(5) == 0) {
            damage = damage * 2;
        }
        BattleGround.bulletThreadPool.execute(
                new Bullet(this, 50, bulletSpeed, Screen.gridToLeftBulletPixel(grid), bulletRadius * 2, bulletColor));
    }
}