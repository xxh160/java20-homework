package game.model.character;

import game.BattleGround;
import game.model.Bullet;
import game.model.Character;
import javafx.scene.paint.Color;
import tool.Camp;
import tool.Screen;
import tool.point.Grid;
import tool.point.Pixel;

public class Xiezi extends Character {
    public Xiezi() {
        super(new Grid(3, 11), 200, 100, 20, 10, Color.BLACK, Camp.Yaojing);
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        Pixel p = Screen.gridToLeftBulletPixel(grid);
        BattleGround.bulletThreadPool.execute(new Bullet(this, 50, bulletSpeed, p, bulletRadius * 2, bulletColor));
        p.setX(p.getX() - 50);
        BattleGround.bulletThreadPool.execute(new Bullet(this, 50, bulletSpeed, p, bulletRadius * 2, bulletColor));
    }
}