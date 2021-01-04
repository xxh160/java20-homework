package CreatrueClass;

import Skill.Buff;
import Skill.Bufftype;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Crocodile extends Creatrue
{
    public Crocodile()
    {
        initbuffview();
        side="Evil";
        type="crocodile";
        blood=0;
        defence=0;
        attack=0;
        tmpblood=blood;
        status="left";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Crocodile/Crocodile_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);
    }

    public Crocodile(int blood,int defence,int attack)
    {
        initbuffview();
        side="Evil";
        type="crocodile";
        this.blood=blood;
        this.defence=defence;
        this.attack=attack;
        status="left";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Crocodile/Crocodile_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);
        this.profilephoto=new Image("/Crocodile/profile.png");
        tmpblood=blood;

        this.bloodview=new ImageView();
        bloodview.setImage(new Image("/Blood.png"));
        bloodview.setFitHeight(54);
        bloodview.setFitWidth(62.5);
        bloodview.setPreserveRatio(true);
        this.tmpbloodview=new ImageView();
        tmpbloodview.setImage(new Image("/Tmpblood.png"));
        tmpbloodview.setFitHeight(54);
        tmpbloodview.setFitWidth(62.5);

        int[] tmprangex={1};
        int[] tmprangey={0};
        this.askill.setRange(tmprangex,tmprangey);
        this.askill.setAttack(attack);
        this.askill.setImg(new Image("/Crocodile/Crocodile_askill.png"));
        this.askill.setName("鳄鱼抽击");
        this.askill.setDescription("鳄鱼精用尾巴抽敌人");

        this.skill.setImg(new Image("/Crocodile/Crocodile_skill.png"));
        this.skill.setBuff(new Buff(Bufftype.DEFENCE,-50));
        this.skill.setName("血鳄咆哮");
        this.skill.setDescription("鳄鱼精发出咆哮,\n周围的敌人防御力大幅下降 持续5s CD:10s");
        this.skill.setEffectimg(new Image("Crocodile/effect.png"));
        this.skill.setCd(10);
        this.skill.setAttack(0);
        this.skill.setRange(new int[]{0,0,1,-1,-1,-1,1,1},new int[]{1,-1,0,0,-1,1,-1,1});
    }

    @Override
    public void attack() {
        System.out.println("?");
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Crocodile/Crocodile_"+status+"_attack.png"));
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
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Crocodile/Crocodile_"+status+"_attack.png"));
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
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Crocodile/Crocodile_"+status+".png"));
        creatrueview.setImage(this.creatrueimage);
    }
}