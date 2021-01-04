package CreatrueClass;

import Skill.Buff;
import Skill.Bufftype;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Scorpion extends Creatrue
{
    public Scorpion()
    {
        initbuffview();
        side="Evil";
        type="scorpion";
        blood=0;
        defence=0;
        attack=0;
        tmpblood=blood;
        status="left";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Scorpion/Scorpion_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);
    }

    public Scorpion(int blood,int defence,int attack)
    {
        initbuffview();
        side="Evil";
        type="scorpion";
        this.blood=blood;
        tmpblood=blood;
        this.defence=defence;
        this.attack=attack;
        status="left";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Scorpion/Scorpion_"+status+".png"));
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

        this.profilephoto=new Image("/Scorpion/profile.png");
        this.skill.setImg(new Image("/Scorpion/Scorpion_skill.png"));
        this.skill.setName("幽冥禁锢");
        this.skill.setDescription("禁锢周围敌人，\n造成大量伤害，并回复一定血量 CD:30s");
        this.skill.setCd(30);
        this.skill.setBuff(new Buff(Bufftype.BOUND,0));
        this.skill.setEffectimg(new Image("/Scorpion/effect.png"));
        this.skill.setAttack(attack*2);
        this.skill.setRange(new int[]{0,0,0,0,1,2,-1,-2,1,-1,1,-1},new int[]{1,2,-1,-2,0,0,0,0,1,1,-1,-1});

        this.askill.setImg(new Image("/Scorpion/Scorpion_askill.png"));
        this.askill.setName("蝎子毒刺");
        this.askill.setDescription("蝎子精用尾巴刺人");
        int[] tmprangex={1};
        int[] tmprangey={0};
        askill.setRange(tmprangex,tmprangey);
        askill.setAttack(attack);
    }

    @Override
    public void attack() {
        System.out.println("?");
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Scorpion/Scorpion_"+status+"_attack.png"));
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
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Scorpion/Scorpion_"+status+"_attack.png"));
        creatrueview.setImage(creatrueimage);
        setTmpblood(tmpblood+60);
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
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Scorpion/Scorpion_"+status+".png"));
        creatrueview.setImage(this.creatrueimage);
    }
}
