package CreatrueClass;

import Skill.Buff;
import Skill.Bufftype;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Mouse extends Creatrue
{
    public Mouse()
    {
        side="Evil";
        type="mouse";
        blood=0;
        defence=0;
        attack=0;
        tmpblood=blood;
        status="left";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Mouse/Mouse_"+status+".png"));
        this.creatrueview=new ImageView();
        creatrueview.setImage(creatrueimage);
        creatrueview.setFitHeight(54);
        creatrueview.setFitWidth(62.5);
        creatrueview.setPreserveRatio(true);
    }

    public Mouse(int blood,int defence,int attack)
    {
        initbuffview();
        side="Evil";
        type="mouse";
        this.blood=blood;
        tmpblood=blood;
        this.defence=defence;
        this.attack=attack;
        status="left";
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Mouse/Mouse_"+status+".png"));
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

        this.profilephoto=new Image("/Mouse/profile.png");

        this.skill.setImg(new Image("/Mouse/Mouse_skill.png"));
        this.skill.setName("虚弱瘟疫");
        this.skill.setDescription("老鼠精释放瘟疫，\n使周围敌方单位虚弱，攻击力下降，持续6s，CD:10s");
        this.skill.setEffectimg(new Image("/Mouse/effect.png"));
        this.skill.setBuff(new Buff(Bufftype.ATTACK,-50));
        this.skill.setRange(new int[]{0,0,1,-1},new int[]{1,-1,0,0});
        this.skill.setAttack(0);
        this.skill.setCd(10);

        this.askill.setImg(new Image("/Mouse/Mouse_askill.png"));
        this.askill.setName("老鼠爪击");
        this.askill.setDescription("老鼠精用爪子抓人");
        int[] tmprangex={1};
        int[] tmprangey={0};
        askill.setRange(tmprangex,tmprangey);
        askill.setAttack(attack);
    }

    @Override
    public void attack() {
        System.out.println("?");
        final Image tmpimg = this.creatrueimage;
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Mouse/Mouse_"+status+"_attack.png"));
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
        this.creatrueimage = new Image(this.getClass().getResourceAsStream("/Mouse/Mouse_"+status+"_attack.png"));
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
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/Mouse/Mouse_"+status+".png"));
        creatrueview.setImage(this.creatrueimage);
    }
}
