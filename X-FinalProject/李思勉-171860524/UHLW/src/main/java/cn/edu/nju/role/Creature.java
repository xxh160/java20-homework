package cn.edu.nju.role;

import cn.edu.nju.effect.Buff;
import cn.edu.nju.field.BattleField;
import cn.edu.nju.field.Point;
import cn.edu.nju.main.Controller;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundFill;

import java.time.chrono.ThaiBuddhistChronology;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Creature implements Action, Comparable  {
    public enum SpecieType{
        Air,HuLuWa,OldMan,Monster,Stone,Glass
    };
    protected SpecieType specie;
    protected int posx;
    protected int posy;
    protected int ID;
    protected int gender;
    protected String name;
    protected Image head;
    protected Map<String,Image> walkMap;
    protected int maxHP;
    protected int maxMP;
    protected int maxSP;
    protected int HP;
    protected int MP;
    protected int ATK;
    protected int AP;
    protected int AD;
    protected int MD;
    protected int SP;
    protected boolean isSkiped = false;
    protected int RG;
    protected boolean action;
    protected boolean drawable;
    protected String direct = "d";
    Skill skill[] = new Skill[4];
    protected final static Image attack = new Image("/img/skill/attack.png",40,40,false,false);
    protected final static Image defend = new Image("/img/skill/defend.png",40,40,false,false);
    static protected int count;
    static protected ArrayList<Creature> creatureList = new ArrayList<>();
    protected ArrayList<Buff> buffList = new ArrayList<>();
    static{
        count = 0;
    }
    public Creature(){
        gender = -1;
        specie = SpecieType.Air;
        posx = -1;
        posy = -1;
        name = "";
        head = null;
        walkMap = null;
        action = true;
        drawable = false;
        direct = "d";
    }
    public Creature(Creature c){
        ID = c.ID;
        specie = c.specie;
        head = c.head;
        walkMap = c.walkMap;
        name = c.name;
        HP = c.HP;
        MP = c.MP;
        maxHP = c.maxHP;
        maxMP = c.maxMP;
        ATK = c.ATK;
        AP = c.AP;
        AD = c.AD;
        MD = c.MD;
        SP = c.SP;
        maxSP = c.maxSP;
        RG = c.RG;
        drawable = c.drawable;
        action = c.action;
        skill = c.skill;
        direct = c.direct;
        posx = c.posx;
        posy = c.posy;
        for(Buff buff:c.buffList){
            buffList.add(new Buff(buff.getBuffType(),buff.getBuffRound(),buff.getBuffEffect()));
        }
    }
    public  SpecieType getSpecie(){
        return specie;
    }
    public void setPos(int row,int column){
        posy = row;
        posx = column;
    }
    public void walkTo(BattleField<Creature> battleField, int row, int column){
        if(hasMobility()){
            Creature c  = battleField.getCreature(row,column);
            int consume = Math.abs(row-this.posy)+Math.abs(column-this.posx);
            this.SP -= consume;
            if(row>this.posy){
                this.direct = "d";
            }
            else if (column>this.posx){
                this.direct = "r";
            }
            else if (column<this.posx){
                this.direct = "l";
            }
            else{
                this.direct = "u";
            }
            if(c.specie==SpecieType.Air){
                battleField.set(this.posy,this.posx,c);
                battleField.set(row,column,this);
            }
        }
    }
    public int getPosx(){
        return posx;
    }
    public int getPosy(){
        return posy;
    }

    public boolean isAction() {
        return action;
    }
    public int compareTo(Object o) {
        if(o instanceof Creature){
            return this.name.compareTo(((Creature) o).name);
        }
        System.out.println("Warning: Creature compare to an Object not Creature!");
        return 0;
    }
    public void clear(){
        specie = SpecieType.Air;
        drawable = false;

    }
    public void tellName(){
        if(hasMobility()) {
            System.out.print(name + " ");
        }
    }
    public boolean hasMobility(){
        if(specie == SpecieType.Air){
            return false;
        }
        return true;
    }
    public boolean isHuLuWa(){
        return specie == SpecieType.HuLuWa;
    }
    public boolean isDrawable(){
        return drawable;
    }
    public boolean isAir(){
        return specie==SpecieType.Air;
    }
    public boolean isAnimal(){
        if(specie!=SpecieType.Air&&specie!=SpecieType.Stone&&specie!=SpecieType.Glass){
            return true;
        }
        return false;
    }


    public void useSkill(Skill skill,Creature target){
        switch (skill.getSkillType()){
            case Skill.ATTACK:{
                int damage = skill.computeDamage(ATK,AP,target.AD,target.MD);
                target.HP = target.HP - damage;
                if(target.HP<=0){
                    target.HP = 0;
                    target.clear();
                }
                System.out.println("造成了伤害:"+ damage);
                break;
            }
            case Skill.BUFF:{
                if(skill.getAddType()==Skill.DE){
                    addBuff(Buff.AD,skill.getRound(),skill.add);
                    addBuff(Buff.MD, skill.getRound(), skill.add);
                }
                else{
                    addBuff(skill.getAddType(),skill.getRound(),skill.add);
                }
                break;
            }
            case Skill.DONG:{
                int damage = skill.computeDamage(ATK,AP,target.AD,target.MD);
                target.HP = target.HP - damage;
                if(target.HP<=0){
                    target.HP = 0;
                    target.clear();
                }
                target.isSkiped = true;
                System.out.println("造成了伤害:"+ damage);
                break;
            }
            case Skill.RECOVERY:{
                int recovery = AP + 10;
                if(skill.getAddType()==Skill.HP){
                    if(target.HP+recovery>target.maxHP){
                        target.HP = target.maxHP;
                    }
                    else{
                        target.HP = target.HP+recovery;
                    }
                }
                else{
                    if(target.MP+recovery>target.maxMP){
                        target.MP = target.maxMP;
                    }
                    else{
                        target.MP = target.MP+recovery;
                    }
                }
                break;
            }
        }
        HP -= skill.hpConsume;
        MP -= skill.mpConsume;
        action = !action;
    }
    public void useSkill(Skill skill, BattleField<Creature> battleField,int x, int y){
        switch (skill.getSkillType()){
            case Skill.MOVE:{
                int scope = skill.getScope();
                Map<Point,Point> map = Controller.getReadablePoints(x,y,scope,true);
                for(Point p:map.keySet()){
                    Creature c = battleField.getCreature(p.y,p.x);
                    if(c.isEnemy(this)){
                        int damage = skill.computeDamage(ATK,AP,c.AD,c.MD);
                        c.HP = c.HP - damage;
                        if(c.HP<=0){
                            c.HP = 0;
                            c.clear();
                        }
                    }
                }
                battleField.set(this.posy,this.posx,battleField.getCreature(y,x));
                battleField.set(y,x,this);
                break;
            }
        }
        HP -= skill.hpConsume;
        MP -= skill.mpConsume;
        action = !action;
    }

    public static void recovery(){
        for(Creature c:creatureList){
            c.setSP(c.getMaxSP());
            if(!c.isSkiped){
                c.action = true;
            }
            else{
                c.action = false;
                c.isSkiped = false;
            }
        }
    }
    public void addBuff(int type,int round,int effect){
        Buff buff = new Buff(type,round,effect);
        buff.apply(this);
        buffList.add(buff);
    }
    public void removeBuff(){
        for(Iterator<Buff> it = buffList.iterator(); it.hasNext();){
            Buff buff= it.next();
            if(buff.subRound()){
                buff.remove(this);
                it.remove();
            }
        }
        //System.out.println("剩余buff："+buffList.size());
    }

    protected static Map<String,Image> getWalkMap(String path){
        Map<String,Image> walkMap = new HashMap<>();
        String []nameList = new String[]{"d1","d2","d3","l1","l2","l3","r1","r2","r3","u1","u2","u3"};
        for(String name:nameList){
            String url =path+name+".png";
            Image img = new Image(url);
            walkMap.put(name,img);
        }
        return  walkMap;
    }
    public Map<String,Image>getWalkMap(){
        return walkMap;
    }
    public Image getHead(){
        return head;
    }
    public Image getSWalk(){
        return walkMap.get(direct+"2");
    }
    public void  setDrawable(boolean drawable){
        this.drawable = drawable;
    }
    public Skill getSkill(int index){
        return skill[index];
    }
    public static Creature getCreatureById(int id){
        return creatureList.get(id);
    }
    public int getATK(){return ATK;}
    public int getHP(){return HP;}
    public int getMP() {
        return MP;
    }
    public int getAP() {
        return AP;
    }
    public int getAD() {
        return AD;
    }
    public int getMD() {
        return MD;
    }
    public int getSP() {
        return SP;
    }
    public int getRG() {
        return RG;
    }
    public int getMaxHP() {
        return maxHP;
    }
    public int getMaxMP() {
        return maxMP;
    }
    public int getMaxSP() { return maxSP; }
    public int getID(){return ID;}
    public void setSP(int speed){
        this.SP = speed;
    }
    public boolean isEnemy(int playerID){
        if(playerID==0){
            return  specie==SpecieType.Monster;
        }
        else{
            return  specie==SpecieType.HuLuWa||specie==SpecieType.OldMan;
        }
    }
    public boolean isEnemy(Creature c){
        if(this.specie==SpecieType.Monster){
            return c.specie!=SpecieType.Monster;
        }
        else{
            return c.specie==SpecieType.Monster;
        }
    }
    public boolean isMonster(){
        return this.specie==SpecieType.Monster;
    }
    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public void setAP(int AP) {
        this.AP = AP;
    }

    public void setAD(int AD) {
        this.AD = AD;
    }

    public void setMD(int MD) {
        this.MD = MD;
    }
    public String getName(){
        return name;
    }
    public String getStatus(){
        String status = ""+HP+" "+MP+" "+ATK+" "+AP+" "+AD+" "+MD+" "+SP+" "+maxHP+" "+maxMP+" "+maxSP;
        status += " "+posx+" "+posy;
        for(Buff buff:buffList){
            status+=" " + buff.getBuffType()+" "+buff.getBuffRound()+" "+buff.getBuffEffect();
        }
        return status;
    }
    public void setStatus(BattleField<Creature> battleField,String status){
        String[] para = status.split(" ");
        HP = Integer.parseInt(para[0]);
        if(HP<=0){
            clear();
        }
        else{
            drawable = true;
        }
        MP = Integer.parseInt(para[1]);
        ATK = Integer.parseInt(para[2]);
        AP = Integer.parseInt(para[3]);
        AD = Integer.parseInt(para[4]);
        MD = Integer.parseInt(para[5]);
        SP = Integer.parseInt(para[6]);
        maxHP = Integer.parseInt(para[7]);
        maxMP = Integer.parseInt(para[8]);
        maxSP = Integer.parseInt(para[9]);

        int x = Integer.parseInt(para[10]);
        int y = Integer.parseInt(para[11]);
        Creature c = battleField.getCreature(y,x);
        battleField.set(posy,posx,c);
        battleField.set(y,x,this);

        int buffNum = (para.length - 12)/3;
        buffList.clear();
        for(int i=0;i<buffNum;i++){
            buffList.add(new Buff(Integer.parseInt(para[12+i]),Integer.parseInt(para[13+i]),Integer.parseInt(para[14+i])));
        }

        action = true;
        isSkiped = false;
    }
    public Boolean isStone(){
        return specie == SpecieType.Stone;
    }


}
