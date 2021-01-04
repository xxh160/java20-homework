package CreatrueClass;

import Skill.*;
import javafx.scene.image.Image;
import BattleField.*;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Creatrue
{
    int blood;
    int defence;
    int attack;
    int tmpblood;
    String side;
    String status;
    String type;
    Image creatrueimage;
    Image profilephoto;
    ImageView creatrueview;
    ImageView bloodview;
    ImageView tmpbloodview;
    Skill skill;
    Skill askill;
    Vector<String> statusbar=new Vector<String>();
    ImageView buffview[];

    public Creatrue()
    {
        side="";
        status="";
        type="";
        blood=0;
        defence=0;
        attack=0;
        tmpblood=blood;
        skill=new Skill();
        askill=new Skill();
    }

    public Creatrue(int b, int d, int a)
    {
        side="";
        status="";
        type="";
        blood=b;
        defence=d;
        attack=a;
        tmpblood=blood;
        skill=null;
        askill=null;
        skill=new Skill();
        askill=new Skill();
        int[] tmprangex={1};
        int[] tmprangey={0};
        askill.setRange(tmprangex,tmprangey);
        askill.setAttack(attack);
    }

    public ImageView getimgview()
    {
        return creatrueview;
    }

    public ImageView getBloodview() {return bloodview;}

    public ImageView getTmpbloodview()
    {
        tmpbloodview.setFitWidth(62.5 * (tmpblood) *1.0/ (blood*1.0));
        return tmpbloodview;
    }

    public boolean hasbuff(String askbuff) { return (statusbar.indexOf(askbuff)!=-1); }

    public Image getProfilephoto() {return profilephoto;}

    public Skill getSkill() { return skill;}

    public Skill getAskill()
    {
        return askill;
    }

    public int getBlood() {return blood;}

    public int getTmpblood() {return tmpblood;}

    public int getAttack() {return attack;}

    public String getStatus() {return status;}

    public String getSide() {return side;}

    public void turnleft() {status="left";}

    public void turnright() {status="right";}

    public void setTmpblood(int tmpblood)
    {
        this.tmpblood=tmpblood;
        if (tmpblood>blood) this.tmpblood=blood;
    }

    public void attack() {}

    public void useskill() {}

    public void attacked(int damage)
    {
        int realdamage=damage-defence;
        if (realdamage<=0) realdamage=10;
        tmpblood=tmpblood-realdamage;
        if (tmpblood<=0)
        {
            tmpblood=0;
            status="dead";
        }
    }

    public void influenced(Buff buff,int lastime)
    {

        switch (buff.getBufftype())
        {
            case ATTACK:
                int v=buff.getValue();
                Timer timer;
                if (v>0)
                    statusbar.add("attack_increase");
                else
                    statusbar.add("attack_decrease");
                    attack=attack+v;
                    timer=new Timer();
                    timer.schedule(
                            new TimerTask()
                            {
                                @Override
                                public void run()
                                {
                                    attack=attack-v;
                                    if (v>0)
                                        statusbar.remove("attack_increase");
                                    else
                                        statusbar.remove("attack_decrease");
                                    this.cancel();
                                }
                            },lastime*1000
                    );

                break;
            case DEFENCE:
                v=buff.getValue();
                if (v>0)
                    statusbar.add("defence_increase");
                else
                    statusbar.add("defence_decrease");
                defence=defence+v;
                timer=new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                defence=defence-v;
                                if (v>0)
                                    statusbar.remove("defence_increase");
                                else
                                    statusbar.remove("defence_decrease");
                                this.cancel();
                            }
                        },lastime*1000
                );
                break;
            case STUN:
                statusbar.add("stun");
                timer=new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                statusbar.remove("stun");
                                this.cancel();
                            }
                        },lastime*1000
                );
                break;
            case BOUND:
                statusbar.add("bound");
                timer=new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                statusbar.remove("bound");
                                this.cancel();
                            }
                        },lastime*1000
                );
                break;
            case MESS:
                statusbar.add("mess");
                timer=new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                statusbar.remove("mess");
                                this.cancel();
                            }
                        },lastime*1000
                );
        }
    }
    public boolean isdead()
    {
        return tmpblood<=0;
    }

    public void initbuffview()
    {
        String buffset[]=new String[]{"attack_increase","attack_decrease","defence_increase","defence_decrease","stun","bound","mess"};
        buffview=new ImageView[7];
        for (int i=0;i<7;i++)
        {
            buffview[i]=standard(new ImageView(),new Image("/Buff/"+buffset[i]+".png"));
        }
    }

    public ImageView getbuffview(int index)
    {
        return buffview[index];
    }
    protected ImageView standard(ImageView imgv, Image img)
    {
        imgv.setImage(img);
        imgv.setFitHeight(54);
        imgv.setFitWidth(62.5);
        imgv.setPreserveRatio(true);
        return imgv;
    }
}
