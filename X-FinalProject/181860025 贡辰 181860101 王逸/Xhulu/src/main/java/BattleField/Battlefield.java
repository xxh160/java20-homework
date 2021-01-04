package BattleField;

import CreatrueClass.*;
import Skill.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class Battlefield
{
    int width;
    int height;
    private Position[][] positions;
    private int[][] conditions;
    private int tmpx,tmpy;
    int selectx;
    int selecty;
    String state;
    boolean ifload=false;

    public Battlefield()
    {
        this.state="initial";
        this.selectx=-1;
        this.selecty=-1;
        this.width=20;
        this.height=10;
        this.positions=new Position[width][height];
        this.conditions=new int[width][height];
        for (int i=0;i<this.width;i++)
        {
            for (int j=0;j<this.height;j++)
            {
                conditions[i][j]=0;
                positions[i][j]=new Position();
            }
        }

    }

    public Battlefield(int w,int h)
    {
        width=w;
        height=h;
        this.positions=new Position[width][height];
        this.conditions=new int[width][height];
        for (int i=0;i<this.width;i++)
        {
            for (int j=0;j<this.height;j++)
            {
                conditions[i][j]=0;
                positions[i][j]=new Position();
            }
        }
    }
    public void startload()
    {
        ifload=true;
    }
    public void startgame()
    {
        state="gaming";
    }
    public void putin(int i, int j,Creatrue c)
    {
        positions[i][j].putin(c);
    }

    public void putin(int i, int j, Image img)
    {
        positions[i][j].putin(img);
    }

    public String getState() { return state; }

    public Creatrue getcontent(int i, int j)
    {
        return positions[i][j].getcontent();
    }

    public ImageView geteffect(int i, int j){return positions[i][j].getSpeffect();}
    public void select(int x,int y)
    {
        selectx=x;
        selecty=y;
    }
    public void unselect()
    {
        selectx=-1;
        selecty=-1;
    }

    public int getselectX()
    {
        return selectx;
    }

    public int getselectY()
    {
        return selecty;
    }


    public void solvemsg(Message msg,int x,int y,boolean ifhost)
    {
        if (state!="gaming") return;
        if (x==-1 || y==-1) return;
        //System.out.println("solve"+msg);
        Creatrue tmpc=getcontent(x,y);
        if (tmpc==null) return;
        tmpx=0;
        tmpy=0;
        switch (msg)
        {
            case UP:
                if (tmpc.hasbuff("stun") || tmpc.hasbuff("bound")) break;
                if (tmpc.hasbuff("mess"))
                    tmpy=y+1;
                else
                    tmpy=y-1;
                if (tmpy>0 && tmpy<=13 && getcontent(x,tmpy)==null)
                {
                    if (ifhost) selecty=tmpy;
                    positions[x][y].clear();
                    positions[x][tmpy].putin(tmpc);
                }
                break;
            case DOWN:
                if (tmpc.hasbuff("stun") || tmpc.hasbuff("bound")) break;
                if (tmpc.hasbuff("mess"))
                    tmpy=y-1;
                else
                    tmpy=y+1;
                if (tmpy<=13 && tmpy>0 && getcontent(x,tmpy)==null)
                {
                    if (ifhost) selecty=tmpy;
                    positions[x][y].clear();
                    positions[x][tmpy].putin(tmpc);
                }
                break;
            case LEFT:
                if (tmpc.hasbuff("stun") || tmpc.hasbuff("bound")) break;
                if (tmpc.hasbuff("mess"))
                {
                    tmpx = x + 1;
                    if (tmpc.getStatus().contains("left"))
                        tmpc.turnright();
                }
                else
                {
                    tmpx = x - 1;
                    if (tmpc.getStatus().contains("right"))
                        tmpc.turnleft();
                }
                if (tmpx>0 && tmpx<=16 && getcontent(tmpx,y)==null)
                {
                    if (ifhost) selectx=tmpx;
                    positions[x][y].clear();
                    positions[tmpx][y].putin(tmpc);
                }
                break;
            case RIGHT:
                if (tmpc.hasbuff("stun") || tmpc.hasbuff("bound")) break;
                if (tmpc.hasbuff("mess"))
                {
                    tmpx = x - 1;
                    if (tmpc.getStatus().contains("right"))
                        tmpc.turnleft();
                }
                else
                {
                    tmpx = x + 1;
                    if (tmpc.getStatus().contains("left"))
                        tmpc.turnright();
                }
                if (tmpx<=16 && tmpx>0 && getcontent(tmpx,y)==null)
                {
                    if (ifhost) selectx=tmpx;
                    positions[x][y].clear();
                    positions[tmpx][y].putin(tmpc);
                }
                break;
            case ATTACK:
                if (tmpc.hasbuff("stun")) break;
                tmpc.attack();
                Skill askill=tmpc.getAskill();
                int[] tmprangex=askill.getRangex();
                int[] tmprangey=askill.getRangey();

                int d=0;
                if (tmpc.getStatus().contains("left"))
                    d=-1;
                else d=1;
                for (int i=0;i<tmprangex.length;i++)
                {
                    tmpx=tmprangex[i]*d+x;
                    tmpy=tmprangey[i]*d+y;
                    if (isok(tmpx,tmpy))
                    {
                        Creatrue cattacked=getcontent(tmpx,tmpy);
                        if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                        {
                            cattacked.attacked(tmpc.getAttack());
                            if (cattacked.isdead())
                                positions[tmpx][tmpy].clear();
                        }
                    }
                }
                break;

            case SKILL:
                if (tmpc.hasbuff("stun")) break;
                if (!tmpc.getSkill().iscd() || ifload)
                {
                    System.out.println("skill");
                    tmpc.useskill();
                    Skill skill = tmpc.getSkill();
                    tmprangex=skill.getRangex();
                    tmprangey=skill.getRangey();
                    switch (skill.getName())
                    {
                        case "虚弱瘟疫":
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]+x;
                                tmpy=tmprangey[i]+y;

                                System.out.print(tmpx);
                                System.out.print(" ");
                                System.out.println(tmpy);
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                        cattacked.influenced(skill.getBuff(),6);

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=6;
                                }
                            }
                            setcleareffect(6,300);
                            break;
                        case "血鳄咆哮":
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]+x;
                                tmpy=tmprangey[i]+y;

                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                        cattacked.influenced(skill.getBuff(),5);

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=7;
                                }

                            }
                            setcleareffect(7,300);
                            break;
                        case "爷♥爷♥的♥爱":
                            for (int i=0;i<width;i++)
                                for (int j=0;j<height;j++)
                                {
                                    Creatrue c=positions[i][j].getcontent();
                                    if (c!=null && c.getSide()==tmpc.getSide())
                                    {
                                        putin(i,j,skill.getEffectimg());
                                        conditions[i][j]=8;
                                        c.setTmpblood(c.getBlood());
                                    }
                                }
                            setcleareffect(8,300);
                            break;
                        case "千里顺风":
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]+x;
                                tmpy=tmprangey[i]+y;
                                /*System.out.print(tmpx);
                                System.out.print(" ");
                                System.out.println(tmpy);*/
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()==tmpc.getSide())
                                        cattacked.influenced(skill.getBuff(),15);

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=9;
                                }
                            }
                            setcleareffect(9,300);
                            break;
                        case "蛤蟆重击":
                            d=0;
                            if (tmpc.getStatus().contains("left"))
                                d=-1;
                            else d=1;
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]*d+x;
                                tmpy=tmprangey[i]*d+y;
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                    {
                                        cattacked.influenced(skill.getBuff(),2);
                                        cattacked.attacked(tmpc.getAttack());
                                        if (cattacked.isdead())
                                            positions[tmpx][tmpy].clear();
                                    }

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=10;
                                }

                            }
                            setcleareffect(10,300);
                            break;
                        case "撼山动地":
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]+x;
                                tmpy=tmprangey[i]+y;
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                    {
                                        cattacked.influenced(skill.getBuff(),3);
                                        cattacked.attacked(tmpc.getAttack());
                                        if (cattacked.isdead())
                                            positions[tmpx][tmpy].clear();
                                    }

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=11;

                                }

                            }
                            setcleareffect(11,300);
                            break;
                        case "紫金神葫":
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]+x;
                                tmpy=tmprangey[i]+y;
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                    {
                                        cattacked.influenced(skill.getBuff(),5);
                                        cattacked.attacked(tmpc.getAttack());
                                        if (cattacked.isdead())
                                            positions[tmpx][tmpy].clear();
                                    }

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=12;
                                }

                            }
                            setcleareffect(12,300);
                            break;
                        case "刚阳之火":
                            d=0;
                            if (tmpc.getStatus().contains("left"))
                                d=-1;
                            else d=1;
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]*d+x;
                                tmpy=tmprangey[i]*d+y;
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                    {
                                        cattacked.attacked(tmpc.getAttack()*2);
                                        if (cattacked.isdead())
                                            positions[tmpx][tmpy].clear();
                                    }

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=1;
                                }
                            }
                            setcleareffect(1,300);
                            break;
                        case "惊涛骇浪":
                            d=0;
                            if (tmpc.getStatus().contains("left"))
                                d=-1;
                            else d=1;
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]*d+x;
                                tmpy=tmprangey[i]*d+y;
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                    {
                                        cattacked.attacked(tmpc.getAttack()+60);
                                        if (cattacked.isdead())
                                            positions[tmpx][tmpy].clear();
                                    }

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=2;
                                }
                            }
                            setcleareffect(2,300);
                            break;
                        case "幽冥禁锢":
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]+x;
                                tmpy=tmprangey[i]+y;
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                    {
                                        cattacked.influenced(skill.getBuff(),3);
                                        cattacked.attacked(tmpc.getAttack()*2);
                                        if (cattacked.isdead())
                                            positions[tmpx][tmpy].clear();
                                    }

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=4;
                                }
                            }
                            setcleareffect(4,400);
                            break;

                        case "毒刺牢笼":
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]+x;
                                tmpy=tmprangey[i]+y;
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                    {
                                        cattacked.influenced(skill.getBuff(),2);
                                        cattacked.attacked(tmpc.getAttack());
                                        if (cattacked.isdead())
                                            positions[tmpx][tmpy].clear();
                                    }

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=3;
                                }
                            }
                            setcleareffect(3,300);
                            break;

                        case "混乱撕咬":
                            d=0;
                            if (tmpc.getStatus().contains("left"))
                                d=-1;
                            else d=1;
                            for (int i=0;i<tmprangex.length;i++)
                            {
                                tmpx=tmprangex[i]*d+x;
                                tmpy=tmprangey[i]*d+y;
                                if (isok(tmpx,tmpy))
                                {
                                    Creatrue cattacked=getcontent(tmpx,tmpy);
                                    if (cattacked!=null && cattacked.getSide()!=tmpc.getSide())
                                    {
                                        cattacked.influenced(skill.getBuff(),10);
                                        cattacked.attacked(tmpc.getAttack());
                                        if (cattacked.isdead())
                                            positions[tmpx][tmpy].clear();
                                    }

                                    putin(tmpx,tmpy,skill.getEffectimg());
                                    conditions[tmpx][tmpy]=5;
                                }
                            }
                            setcleareffect(5,300);
                            break;
                    }
                }
                break;
        }
        int pd=isend();
        if (pd!=0)
        {
            if (pd==-1) state="Evil";//evil wins
            if (pd==1) state="Justice";//just wins
        }

    }
    public boolean haseffect(int x,int y)
    {
        return positions[x][y].haseffect();
    }

    private int isend()
    {
        int evilnum=0;
        int justnum=0;
        for (int i=0;i<this.width;i++)
        {
            for (int j=0;j<this.height;j++)
            {
                if (positions[i][j].getcontent()!=null)
                {
                    Creatrue tmpc=positions[i][j].getcontent();
                    if (tmpc.getSide()=="Evil") evilnum=evilnum+1;
                    if (tmpc.getSide()=="Justice") justnum=justnum+1;
                    if (evilnum!=0 && justnum!=0) return 0;
                }
            }
        }
        if (evilnum==0) return 1;
        return -1;
    }

    private void setcleareffect(int type, int delay)
    {
        Timer timer=new Timer();
        timer.schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        for (int i=0;i<width;i++)
                            for (int j=0;j<height;j++)
                                if (conditions[i][j]==type)
                                {
                                    conditions[i][j]=0;
                                    positions[i][j].cleareffect();
                                }
                        this.cancel();
                    }
                },delay
        );
    }
    private boolean isok(int x,int y)
    {
        return x<=16 && x>0 && y<=13 && y>0;
    }


}
