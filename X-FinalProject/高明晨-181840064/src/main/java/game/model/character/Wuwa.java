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

public class Wuwa extends Character {
    public Wuwa() {
        super(new Grid(5, 0), 100, 100, 20, 10, Color.BLUE, Camp.Hulu);
    }

    @Override
    public void hit(Character dest, int damage) {
        super.hit(dest, damage);
        Random rand = new Random(4);
        int x = rand.nextInt(100);
        if (x == 99) {
            dest.setHP(damage + dest.getMaxHP());
        } else if (x == 98 || x == 97) {
            this.setHP(maxHP);
        } else if (x < 10) {
            this.setHP(Math.min(this.maxHP, this.getHP() + damage));
        }
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        Pixel p = Screen.gridToRightBulletPixel(grid);
        BattleGround.bulletThreadPool.execute(new Bullet(this, 30, bulletSpeed, p, bulletRadius * 2, bulletColor));
        p.setX(p.getX() + 50);
        BattleGround.bulletThreadPool.execute(new Bullet(this, 30, bulletSpeed, p, bulletRadius * 2, bulletColor));
    }
}