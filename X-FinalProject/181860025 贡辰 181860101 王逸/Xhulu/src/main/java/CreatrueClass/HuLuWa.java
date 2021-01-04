package CreatrueClass;

import Skill.Bufftype;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Skill.*;
import java.util.Timer;
import java.util.TimerTask;

public class HuLuWa extends  Creatrue
{
    String name;

    public HuLuWa()
    {
        initbuffview();
        side="Justice";
        type="huluwa";
        status="right";
        blood=0;
        tmpblood=0;
        defence=0;
        attack=0;
        tmpblood=blood;
        this.creatrueimage=null;
        this.profilephoto=null;
    }

    public HuLuWa(int blood,int defence,int attack,String name)
    {
        initbuffview();
        side="Justice";
        type="huluwa";
        status="right";
        this.blood=blood;
        this.tmpblood=blood;
        this.defence=defence;
        this.attack=attack;
        this.name=name;
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+".png"));
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

        this.profilephoto=new Image("/"+name+"/profile.png");
        this.skill.setImg(new Image("/"+name+"/"+name+"_skill.png"));

        this.askill.setImg(new Image("/"+name+"/"+name+"_askill.png"));
        this.askill.setDescription("葫芦娃向前挥出一拳,"+"\n"+"造成等同攻击力的伤害");
        this.askill.setName("葫芦神拳");
        int[] tmprangex={1};
        int[] tmprangey={0};
        askill.setRange(tmprangex,tmprangey);
        askill.setAttack(attack);

        setskill();
    }

    @Override
    public void turnleft()
    {
        if (status=="right")
            super.turnleft();
        else
            status="skill_left";
        changedirection();
    }

    @Override
    public void turnright()
    {
        if (status=="left")
            super.turnright();
        else
            status="skill_right";
        changedirection();
    }

    @Override
    public void attack()
    {
        System.out.println("?");
        Image tmpimg=this.creatrueimage;
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+"_attack.png"));
        creatrueview.setImage(creatrueimage);
        Timer timer=new Timer();
        timer.schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        System.out.println("ATTACK");
                        creatrueimage=tmpimg;
                        creatrueview.setImage(creatrueimage);
                        this.cancel();
                    }
                },100
        );
    }

    @Override
    public void useskill()
    {
        System.out.println("SKILL"+name);
        switch (name)
        {
            case "Red":
                String tmpstatus=this.status;
                this.status="skill_"+this.status;
                creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+".png"));
                creatrueview.setImage(this.creatrueimage);
                blood=blood+100;
                attack=attack+100;
                askill.setAttack(attack);
                tmpblood=blood;
                Timer timer=new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                status=tmpstatus;
                                creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+".png"));
                                creatrueview.setImage(creatrueimage);
                                blood=blood-100;
                                if (tmpblood>blood) {tmpblood=blood;}
                                attack=attack-100;
                                askill.setAttack(attack);
                                this.cancel();
                            }
                        },5000
                );
                break;
            case "Orange":
                Image tmpimg=this.creatrueimage;
                this.creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/eye.png"));
                creatrueview.setImage(creatrueimage);
                timer=new Timer();
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
                        },300
                );
                break;
            case "Yellow":
                tmpstatus=this.status;
                this.status="skill_"+this.status;
                creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+".png"));
                creatrueview.setImage(this.creatrueimage);
                defence=defence+100;
                timer=new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                status=tmpstatus;
                                creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+".png"));
                                creatrueview.setImage(creatrueimage);
                                defence=defence-100;
                                this.cancel();
                            }
                        },5000
                );
                break;
            case "Green":
                tmpimg=this.creatrueimage;
                this.creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+"_attack.png"));
                creatrueview.setImage(creatrueimage);
                timer=new Timer();
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
                break;
            case "Blue":
                tmpblood=tmpblood+60;
                if (tmpblood>blood) tmpblood=blood;
                tmpimg=this.creatrueimage;
                this.creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+"_attack.png"));
                creatrueview.setImage(creatrueimage);
                timer=new Timer();
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
                break;
            case "Cyan":
                tmpstatus=this.status;
                this.status="skill_"+this.status;
                creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+".png"));
                creatrueview.setImage(this.creatrueimage);
                timer=new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                status=tmpstatus;
                                creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+".png"));
                                creatrueview.setImage(creatrueimage);
                                this.cancel();
                            }
                        },1000
                );
                break;
            case "Purple":
                tmpimg=this.creatrueimage;
                this.creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/hulu.png"));
                creatrueview.setImage(creatrueimage);
                timer=new Timer();
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
                        },300
                );
                break;

        }
    }

    private void changedirection()
    {
        this.creatrueimage=new Image(this.getClass().getResourceAsStream("/"+name+"/"+name+"_"+status+".png"));
        creatrueview.setImage(this.creatrueimage);
    }

    private void setskill()
    {
        switch (name)
        {
            case "Red":
                this.skill.setName("力大无穷");
                this.skill.setDescription("大娃身体变得巨大"+"\n"+"攻击力提升，血量上限提升并回复所有血量,持续5s CD:15s");
                this.skill.setAttack(0);
                this.skill.setCd(15);
                break;
            case "Orange":
                this.skill.setName("千里顺风");
                this.skill.setDescription("二娃运用千里眼顺风耳"+"\n"+"使周围友方的攻击力上升50,持续15s CD:30s");
                this.skill.setBuff(new Buff(Bufftype.ATTACK,50));
                this.skill.setEffectimg(new Image("/"+name+"/effect.png"));
                this.skill.setAttack(0);
                this.skill.setCd(20);
                this.skill.setRange(new int[]{0,0,0,1,-1},new int[]{0,1,-1,0,0});
                break;
            case "Yellow":
                this.skill.setName("铜墙铁壁");
                this.skill.setDescription("三娃硬了"+"\n"+"防御力上升！,持续5s CD:10s");
                this.skill.setAttack(0);
                this.skill.setCd(10);
                break;
            case "Green":
                this.skill.setName("刚阳之火");
                this.skill.setDescription("四娃吐火"+"\n"+"造成大量伤害 CD:20s");
                this.skill.setEffectimg(new Image("/"+name+"/effect.png"));
                this.skill.setAttack(attack*2);
                this.skill.setCd(20);
                this.skill.setRange(new int[]{1,2,3},new int[]{0,0,0});
                break;
            case "Blue":
                this.skill.setName("惊涛骇浪");
                this.skill.setDescription("五娃吐水"+"\n"+"造成伤害的同时治疗自身 CD:20s");
                this.skill.setEffectimg(new Image("/"+name+"/effect.png"));
                this.skill.setAttack(attack+60);
                this.skill.setCd(20);
                this.skill.setRange(new int[]{1,2,3},new int[]{0,0,0});
                break;
            case "Cyan":
                this.skill.setName("来去无踪 ");
                this.skill.setDescription("六娃进入隐身状态"+"\n"+"不会受到攻击,持续1s CD:5s");
                this.skill.setAttack(0);
                this.skill.setCd(5);
                break;
            case "Purple":
                this.skill.setName("紫金神葫");
                this.skill.setDescription("七娃使用神葫芦"+"\n"+"对周围敌方单位造成伤害并束缚,持续5s CD:30s");
                this.skill.setAttack(100);
                this.skill.setEffectimg(new Image("/"+name+"/effect.png"));
                this.skill.setCd(30);
                this.skill.setBuff(new Buff(Bufftype.BOUND,0));
                this.skill.setRange(new int[]{0,0,1,-1,-1,-1,1,1},new int[]{1,-1,0,0,-1,1,-1,1});
                break;
        }
    }

    @Override
    public void attacked(int damage)
    {
        if (name=="Cyan" && status.contains("skill")) return;
        int realdamage=damage-defence;
        if (realdamage<=0) realdamage=10;
        tmpblood=tmpblood-realdamage;
        if (tmpblood<=0)
        {
            tmpblood=0;
            status="dead";
        }
    }
}

