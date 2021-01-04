package cn.edu.nju.effect;

import cn.edu.nju.role.Creature;

public class Buff {
    public static int ATK = 0;
    public static int AP = 1;
    public static int AD = 2;
    public static int MD = 3;
    public static int DE = 4;
    public static int TAUNT = 5;

    int buffType;
    int buffRound;
    int buffEffect;

    public Buff(int type, int round,int effect){
        buffType = type;
        buffRound = round;
        buffEffect = effect;
    }
    public void apply(Creature c){
        switch (buffType){
            case 0:{
                c.setATK(c.getATK()+buffEffect);
                break;
            }
            case 1:{
                c.setAP(c.getAP()+buffEffect);
                break;
            }
            case 2:{
                c.setAD(c.getAD()+buffEffect);
                break;
            }
            case 3:{
                c.setMD(c.getMD()+buffEffect);
                break;
            }
        }
    }
    public boolean subRound(){
        buffRound--;
        return buffRound <= 0;
    }
    public void  remove(Creature c){
        switch (buffType){
            case 0:{
                c.setATK(c.getATK()-buffEffect);
                break;
            }
            case 1:{
                c.setAP(c.getAP()-buffEffect);
                break;
            }
            case 2:{
                c.setAD(c.getAD()-buffEffect);
                break;
            }
            case 3:{
                c.setMD(c.getMD()-buffEffect);
                break;
            }
        }
    }

    public int getBuffType() {
        return buffType;
    }

    public void setBuffType(int buffType) {
        this.buffType = buffType;
    }

    public int getBuffRound() {
        return buffRound;
    }

    public void setBuffRound(int buffRound) {
        this.buffRound = buffRound;
    }

    public int getBuffEffect() {
        return buffEffect;
    }

    public void setBuffEffect(int buffEffect) {
        this.buffEffect = buffEffect;
    }
}
