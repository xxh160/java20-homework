package game.model.character;

import java.util.Random;

import game.BattleGround;
import game.model.Bullet;
import game.model.Character;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;

public class Hama extends Character {
    public Hama() {
        super(new Grid(6, 11), 100, 100, 0, 0, Color.WHITE, Camp.Yaojing);
    }

    @Override
    public void commonAttack() {
        if (isDead()) {
            return;
        }

        Random rand = new Random(11);
        int damage = rand.nextInt(31) + 5;
        int speed = rand.nextInt(11) + 5;
        BattleGround.bulletThreadPool.execute(
                new Bullet(this, damage, -speed, Screen.gridToLeftBulletPixel(grid), bulletRadius, bulletColor));
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        Random rand = new Random(11);
        int x = rand.nextInt(4);
        int damage = rand.nextInt(51) + 25;
        int speed = rand.nextInt(11) + 5;
        switch (x) {
            case 0:
                BattleGround.bulletThreadPool.execute(new Bullet(this, damage, -speed,
                        Screen.gridToLeftBulletPixel(grid), bulletRadius * 2, bulletColor));
                break;
            case 1:
                setHP(Math.min(getHP() + 50, maxHP));
                break;

            case 2:
                setHP(Math.max(getHP() - 50, 1));
            default:
                break;
        }

    }
}