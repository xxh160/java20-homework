package CreatrueClass;

import Skill.Buff;
import Skill.Bufftype;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Chilopod extends Creatrue
{
    public Chilopod()
    {
        initbuffview();
        side="Evil";
        type="chilopod";
        blood=0;
        defence=0;
        attack=0;
        tmpblood=blood;
        status="left";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Chilopod/Chilopod_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);
    }

    public Chilopod(int blood,int defence,int attack)
    {
        initbuffview();
        side="Evil";
        type="chilopod";
        status="left";
        this.blood=blood;
        this.defence=defence;
        this.attack=attack;
        tmpblood=blood;
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Chilopod/Chilopod_"+status+".png"));
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

        this.profilephoto=new Image("/Chilopod/profile.png");
        this.skill.setImg(new Image("/Chilopod/Chilopod_skill.png"));
        this.skill.setName("毒刺牢笼");
        this.skill.setDescription("蜈蚣精向周围刺出毒刺\n造成伤害 并使敌方眩晕2s CD:20s");
        this.skill.setRange(new int[]{0,0,1,-1},new int[]{1,-1,0,0});
        this.skill.setEffectimg(new Image("/Chilopod/effect.png"));
        this.skill.setBuff(new Buff(Bufftype.STUN,50));
        this.skill.setCd(20);
        this.skill.setAttack(attack);

        this.askill.setImg(new Image("/Chilopod/Chilopod_askill.png"));
        this.askill.setName("毒性光线");
        this.askill.setDescription("蜈蚣精用眼喷射出光线");
        int[] tmprangex={1};
        int[] tmprangey={0};
        askill.setRange(tmprangex,tmprangey);
        askill.setAttack(attack);
    }

    @Override
    public void attack() {
        System.out.println("?");
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Chilopod/Chilopod_"+status+"_attack.png"));
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
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Chilopod/Chilopod_"+status+".png"));
        creatrueview.setImage(this.creatrueimage);
    }
}


