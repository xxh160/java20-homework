package CreatrueClass;

import Skill.Buff;
import Skill.Bufftype;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Pangolin extends Creatrue
{
    public Pangolin()
    {
        initbuffview();
        side="Justice";
        status="right";
        type="pangolin";
        blood=0;
        defence=0;
        attack=0;
        tmpblood=blood;
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Pangolin/Pangolin_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);
    }

    public Pangolin(int blood,int defence,int attack)
    {
        initbuffview();
        side="Justice";
        status="right";
        type="pangolin";
        this.blood=blood;
        tmpblood=blood;
        this.defence=defence;
        this.attack=attack;
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Pangolin/Pangolin_"+status+".png"));
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

        this.profilephoto=new Image("/Pangolin/profile.png");

        this.skill.setImg(new Image("/Pangolin/Pangolin_skill.png"));
        this.skill.setAttack(attack);
        this.skill.setName("撼山动地");
        this.skill.setDescription("穿山甲摇动地面"+"\n"+"对周围的敌人造成伤害,并造成3s的眩晕 CD:15s");
        this.skill.setBuff(new Buff(Bufftype.STUN,50));
        this.skill.setCd(15);
        this.skill.setEffectimg(new Image("/Pangolin/effect.png"));
        this.skill.setRange(new int[]{0,0,1,-1,-1,-1,1,1},new int[]{1,-1,0,0,-1,1,-1,1});

        this.askill.setImg(new Image("/Pangolin/Pangolin_askill.png"));
        this.askill.setName("穿山抽击");
        this.askill.setDescription("用尾巴抽敌人!");
        int[] tmprangex={1};
        int[] tmprangey={0};
        askill.setRange(tmprangex,tmprangey);
        askill.setAttack(attack);
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

    @Override
    public void attack() {
        System.out.println("?");
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Pangolin/Pangolin_"+status+"_attack.png"));
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
    public void useskill()
    {
        Image tmpimg;
        tmpimg=this.creatrueimage;
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Pangolin/Pangolin_"+status+"_attack.png"));
        creatrueview.setImage(creatrueimage);
        Timer timer=new Timer();
        timer.schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        creatrueimage=tmpimg;
                        creatrueview.setImage(creatrueimage);
                        this.cancel();
                    }
                },100
        );
    }

    private void changedirection()
    {
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Pangolin/Pangolin_"+status+".png"));
        creatrueview.setImage(this.creatrueimage);
    }
}
