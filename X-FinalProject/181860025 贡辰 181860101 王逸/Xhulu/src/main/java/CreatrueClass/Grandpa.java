package CreatrueClass;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Grandpa extends Creatrue
{
    public Grandpa()
    {
        side="Justice";
        type="grandpa";
        blood=0;
        defence=0;
        attack=0;
        tmpblood=blood;
        status="right";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Grandpa/Grandpa_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);
    }

    public Grandpa(int blood,int defence,int attack)
    {
        side="Justice";
        System.out.println("!");
        type="grandpa";
        this.blood=blood;
        tmpblood=blood;
        this.defence=defence;
        this.attack=attack;
        status="right";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Grandpa/Grandpa_"+status+".png"));
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

        this.profilephoto=new Image("/Grandpa/profile.png");

        this.skill.setImg(new Image("/Grandpa/Grandpa_skill.png"));
        this.skill.setName("爷♥爷♥的♥爱");
        this.skill.setDescription("使所有友方单位回复所有血量 CD:60s");
        this.skill.setCd(60);
        this.skill.setEffectimg(new Image("/Grandpa/effect.png"));

        this.askill.setImg(new Image("/Grandpa/Grandpa_askill.png"));
        int[] tmprangex={1};
        int[] tmprangey={0};
        askill.setRange(tmprangex,tmprangey);
        askill.setAttack(attack);
        this.askill.setName("爷爷的铁拳!");
        this.askill.setDescription("爷爷的老拳"+"\n"+"给你一妈头");
    }

    @Override
    public void attack() {
        System.out.println("?");
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Grandpa/Grandpa_"+status+"_attack.png"));
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
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Grandpa/Grandpa_"+status+"_attack.png"));
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
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Grandpa/Grandpa_"+status+".png"));
        creatrueview.setImage(this.creatrueimage);
    }
}
