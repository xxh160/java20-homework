package CreatrueClass;

import Skill.Buff;
import Skill.Bufftype;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Snake extends Creatrue
{
    public Snake()
    {
        initbuffview();
        side="Evil";
        type="snake";
        status="left";
        blood=0;
        defence=0;
        attack=0;
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Snake/Snake_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);
    }

    public Snake(int blood,int defence,int attack)
    {
        initbuffview();
        side="Evil";
        type="snake";
        status="left";
        this.blood=blood;
        tmpblood=blood;
        this.defence=defence;
        this.attack=attack;
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Snake/Snake_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);

        this.bloodview=new ImageView();
        bloodview.setImage(new Image("/Blood.png"));
        bloodview.setFitHeight(54);
        bloodview.setFitWidth(62.5);
        bloodview.setPreserveRatio(true);
        this.tmpbloodview=new ImageView();
        tmpbloodview.setImage(new Image("/Tmpblood.png"));
        tmpbloodview.setFitHeight(54);
        tmpbloodview.setFitWidth(62.5);

        this.profilephoto=new Image("/Snake/profile.png");
        this.skill.setImg(new Image("/Snake/Snake_skill.png"));
        this.skill.setName("混乱撕咬");
        this.skill.setDescription("蛇精使用毒液撕咬前方单位\n使敌方进入混乱状态10s，并造成伤害 CD:15s");
        this.skill.setEffectimg(new Image("/Snake/effect.png"));
        this.skill.setBuff(new Buff(Bufftype.MESS,0));
        this.skill.setCd(15);
        this.skill.setAttack(attack);
        this.skill.setRange(new int[]{1},new int[]{0});

        this.askill.setImg(new Image("/Snake/Snake_askill.png"));
        this.askill.setName("蛇之突击");
        this.askill.setDescription("蛇精进行冲撞突击");
        int[] tmprangex={1};
        int[] tmprangey={0};
        askill.setRange(tmprangex,tmprangey);
        askill.setAttack(attack);

    }

    @Override
    public void attack() {
        System.out.println("?");
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Snake/Snake_"+status+"_attack.png"));
        creatrueview.setImage(creatrueimage);
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("ATTACK");
                        creatrueimage = tmpimg;
                        creatrueview.setImage(creatrueimage);
                        this.cancel();
                    }
                }, 100
        );
    }

    @Override
    public void useskill() {
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Snake/Snake_"+status+"_attack.png"));
        creatrueview.setImage(creatrueimage);
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        creatrueimage = tmpimg;
                        creatrueview.setImage(creatrueimage);
                        this.cancel();
                    }
                }, 100
        );
    }
    @Override
    public void turnleft()
    {
        super.turnleft();
        changedirection();
    }


    @Override
    public void turnright()
    {
        super.turnright();
        changedirection();
    }

    private void changedirection()
    {
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Snake/Snake_"+status+".png"));
        creatrueview.setImage(this.creatrueimage);
    }
}
