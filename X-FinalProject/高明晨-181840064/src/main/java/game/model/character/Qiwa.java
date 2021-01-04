package game.model.character;

import game.BattleGround;
import game.model.Bullet;
import game.model.Character;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;

public class Qiwa extends Character {
    public Qiwa() {
        super(new Grid(5, 11), 80, 120, 10, 20, Color.PURPLE, Camp.Yaojing);
        // System.out.println(bulletSpeed);
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        BattleGround.bulletThreadPool
                .execute(new Bullet(this, 100, -30, Screen.gridToLeftBulletPixel(grid), bulletRadius * 2, bulletColor));
    }
}