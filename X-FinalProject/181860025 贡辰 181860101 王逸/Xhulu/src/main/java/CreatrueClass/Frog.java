package CreatrueClass;

import Skill.Buff;
import Skill.Bufftype;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class  Frog extends Creatrue
{
    public Frog()
    {
        initbuffview();
        side="Evil";
        type="frog";
        blood=0;
        defence=0;
        attack=0;
        tmpblood=blood;
        status="left";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Frog/Frog_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);
    }

    public Frog(int blood,int defence,int attack)
    {
        initbuffview();
        side="Evil";
        type="frog";
        this.blood=blood;
        this.defence=defence;
        this.attack=attack;
        tmpblood=blood;
        status="left";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Frog/Frog_"+status+".png"));
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

        this.profilephoto=new Image("/Frog/profile.png");
        this.skill.setImg(new Image("/Frog/Frog_skill.png"));
        this.skill.setName("蛤蟆重击");
        this.skill.setDescription("蛤蟆精用舌头重击敌人\n眩晕敌人1s同时造成伤害 Cd:10s");
        this.skill.setEffectimg(new Image("/Frog/effect.png"));
        this.skill.setRange(new int[]{1},new int[]{0});
        this.skill.setAttack(attack);
        this.skill.setCd(10);
        this.skill.setBuff(new Buff(Bufftype.STUN,50));

        this.askill.setImg(new Image("/Frog/Frog_askill.png"));
        this.askill.setName("蛤蟆冲击");
        this.askill.setDescription("蛤蟆精用舌头惩罚敌人");
        int[] tmprangex={1};
        int[] tmprangey={0};
        askill.setRange(tmprangex,tmprangey);
        askill.setAttack(attack);
    }

    @Override
    public void useskill()
    {
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Frog/Frog_"+status+"_attack.png"));
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
    public void attack() {
        System.out.println("?");
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Frog/Frog_"+status+"_attack.png"));
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
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Frog/Frog_"+status+".png"));
        creatrueview.setImage(this.creatrueimage);
    }
}
