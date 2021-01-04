package game.model.character;

import java.util.Random;

import game.BattleGround;
import game.model.Bullet;
import game.model.Character;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;

public class Wugong extends Character {
    public Wugong() {
        super(new Grid(2, 11), 100, 100, 20, 10, Color.DARKRED, Camp.Yaojing);
    }

    @Override
    public void commonAttack() {
        if (isDead()) {
            return;
        }
        int damage = (int) ((2 - ((double) HP) / 100.0) * strength);
        // System.out.println(damage);
        BattleGround.bulletThreadPool.execute(
                new Bullet(this, damage, bulletSpeed, Screen.gridToLeftBulletPixel(grid), bulletRadius, bulletColor));
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        Random rand = new Random(7);
        int damage = rand.nextInt(101);
        BattleGround.bulletThreadPool.execute(new Bullet(this, damage, bulletSpeed, Screen.gridToLeftBulletPixel(grid),
                bulletRadius * 2, bulletColor));
    }
}