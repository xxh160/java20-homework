package Skill;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Skill
{
    int attack;
    Image img;
    String description;
    String name;
    Image effectimg;
    int[] rangex,rangey;
    int cd;
    boolean flag;
    Buff buff;

    public Skill()
    {
        attack=0;
        cd=0;
        flag=false;
        img=null;
        buff=null;
        description="";
        name="";
        effectimg=null;
        rangex=new int[1];
        rangey=new int[1];
    }

    public Buff getBuff() { return buff; }

    public void setBuff(Buff buff){this.buff=buff;}

    public void setAttack(int atk)
    {
        attack=atk;
    }

    public int getAttack() {return attack;}

    public void setCd(int cd) {this.cd=cd; }

    public void setImg(Image img)
    {
        this.img=img;
    }

    public void setDescription(String str)
    {
        description=str;
    }

    public void setRange(int[] rangex,int[] rangey) {this.rangex=rangex;this.rangey=rangey;}

    public void setName(String name) {this.name=name;}

    public int[] getRangex() {return rangex;}

    public int[] getRangey() {return rangey;}

    public String getDescription() {return description;}

    public String getName() {return name;}

    public Image getImg()
    {
        return this.img;
    }

    public void setEffectimg(Image img) { effectimg=img; }

    public Image getEffectimg() { return effectimg; }

    public Boolean incd()
    {
        return flag;
    }

    public boolean iscd()
    {
        if (flag) return true;
        flag=true;
        Timer timer= new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run()
                    {
                        flag=false;
                    }
                }, cd*1000
        );
        return false;
    }
}
