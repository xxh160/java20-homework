package cn.edu.nju.role;
import cn.edu.nju.field.BattleField;
import javafx.scene.image.Image;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class HuLuWa extends Creature{
    private static int totalRank = 0;
    private static ArrayList<HuLuWa> huLuWaList = new ArrayList<>();
    private final int rank;
    private final Random random = new Random();
    private final static Image head1 = new Image("/img/head/HuLuWaT_1.jpg");
    private final static Image head2 = new Image("/img/head/HuLuWaT_2.jpg");
    private final static Image head3 = new Image("/img/head/HuLuWaT_3.jpg");
    private final static Image head4 = new Image("/img/head/HuLuWaT_4.jpg");
    private final static Image head5 = new Image("/img/head/HuLuWaT_5.jpg");
    private final static Image head6 = new Image("/img/head/HuLuWaT_6.jpg");
    private final static Image head7 = new Image("/img/head/HuLuWaT_7.jpg");
    private final static Image[] headList = new Image[]{head1,head2,head3,head4,head5,head6,head7};
    private final static int[] HPList = new int[]{100,50,130,50,50,75,75};
    private final static int[] MPList = new int[]{50,75,25,100,100,75,75};
    private final static int[] ATKList = new int[]{20,30,12,10,10,75,40};
    private final static int[] APList = new int[]{10,10,5,50,50,10,10};
    private final static int[] MDList = new int[]{10,5,20,50,50,5,5};
    private final static int[] ADList = new int[]{12,0,22,0,0,0,0};
    private final static int[] SPList = new int[]{3,3,3,3,3,4,4};
    private final static int[] RGList = new int[]{1,3,1,2,2,1,1};
    private final static String[] NameList = new String[]{"大娃","二娃","三娃","四娃","五娃","六娃","七娃"};
    private static ArrayList<Map<String,Image>> walkArray = new ArrayList<>();
    static{
        String []nameList = new String[]{"d1","d2","d3","l1","l2","l3","r1","r2","r3","u1","u2","u3"};
        for(int i=1;i<=7;i++){
            Map<String,Image> walkMap = Creature.getWalkMap("/img/walk/"+i+"/");
            walkArray.add(walkMap);
        }
    }
    public HuLuWa(){
        ID = count++;
        specie = SpecieType.HuLuWa;
        head = headList[totalRank];
        walkMap = walkArray.get(totalRank);
        rank = totalRank++;
        name = NameList[rank];
        gender = random.nextInt(2);
        HP = HPList[rank];
        MP = MPList[rank];
        maxHP = HP;
        maxMP = MP;
        ATK = ATKList[rank];
        AP = APList[rank];
        AD = ADList[rank];
        MD = MDList[rank];
        SP = SPList[rank];
        maxSP = SP;
        RG = RGList[rank];
        drawable = true;
        initSkill();
        huLuWaList.add(this);
        creatureList.add(this);
    }

    public void initSkill(){
       Image skill2 = new Image("/img/skill/"+(rank+1)+"/"+"1.png",40,40,false,false);
       Image  skill3 = new Image("/img/skill/"+(rank+1)+"/"+"2.png",40,40,false,false);
       switch (rank){
           case 0:{
               String text0 = "普通攻击\n威力：武力值*1\n距离：1\n造成基于武力值的物理伤害";
               skill[0] = new Skill(attack,text0);
               String text1 = "防御姿态\n降低接下来一回合自身所受到的所有伤害，如果本回合不想行动，可使用此技能";
               skill[1] = new Skill(defend,text1);
               String text2 = "贯星长枪\n威力：武力值+20\n 伤害类型：物理伤害\n距离：3\n消耗：10MP\n斗气化枪，对前方三个单位距离的一名敌人造成物理伤害";
               skill[2] = new Skill(skill2,text2);
               skill[2].setRange(3);
               skill[2].setAdd(20);
               skill[2].setMpConsume(10);
               skill[2].initEffect("/img/skill/1/1",4);
               skill[2].setLength(3);
               String text3 = "大荒星陨\n威力：武力值+150\n 伤害类型：物理伤害物理伤害\n距离：10\n消耗：40MP,50HP\n牺牲自己，将自身巨大化为陨石，蓄力跳跃致目标地点，对附近一个单位敌人造成巨额伤害";
               skill[3] = new Skill(skill3,text3);
               skill[3].setRange(10);
               skill[3].setSkillType(Skill.MOVE);
               skill[3].setScope(1);
               skill[3].setHpConsume(50);
               skill[3].setMpConsume(40);
               skill[3].setAdd(150);
               skill[3].initEffect("/img/move",8);
               break;
           }
           case 1:{
               String text0 = "普通攻击\n威力：武力值*1\n距离：3\n造成基于武力值的物理伤害";
               skill[0] = new Skill(attack,text0);
               skill[0].setRange(3);
               String text1 = "防御姿态\n降低接下来一回合自身所受到的所有伤害，如果本回合不想行动，可使用此技能";
               skill[1] = new Skill(defend,text1);
               String text2 = "射手专注\n消耗：10MP\n三回合内，提升自己20的武力值";
               skill[2] = new Skill(skill2,text2);
               skill[2].setSkillType(Skill.BUFF);
               skill[2].setMpConsume(10);
               skill[2].setRound(3);
               skill[2].setRange(0);
               skill[2].setAdd(20);
               skill[2].focus = false;
               skill[2].initEffect("/img/defend",13);
               String text3 = "伪·罗玄箭\n威力：武力值+100\n 伤害类型： 物理伤害\n距离：10\n消耗：20MP\n投影出古代，现在，未来中最强的一箭向敌方射出，射程极远\n\"ryuu ga wa ga te kio kura u!\"";
               skill[3] = new Skill(skill3,text3);
               skill[3].initEffect("/img/skill/2/1",8);
               skill[3].setSkillType(Skill.ATTACK);
               skill[3].mpConsume = 20;
               skill[3].range = 10;
               skill[3].length = 3;
               skill[3].add = 100;
               break;
           }
           case 2:{
               String text0 = "普通攻击\n威力：武力值*1\n距离：1\n造成基于武力值的物理伤害";
               skill[0] = new Skill(attack,text0);
               String text1 = "防御姿态\n降低接下来一回合自身所受到的所有伤害，如果本回合不想行动，可使用此技能";
               skill[1] = new Skill(defend,text1);
               String text2 = "刚毅\n消耗：0MP\n两回合内，提高自身100%的护甲和魔抗";
               skill[2] = new Skill(skill2,text2);
               skill[2].range = 0;
               skill[2].round = 2;
               skill[2].add = 30;
               skill[2].skillType = Skill.BUFF;
               skill[2].addType = Skill.DE;
               skill[2].initEffect("/img/defend",13);
               skill[2].focus = false;
               String text3 = "盾牌猛击\n威力：护甲值+魔抗值+10\n 伤害类型：物理\n消耗：10MP\n距离：1\n对目标敌人造成基于护甲值的伤害，并使得目标敌人的下一回合无法使用任何技能";
               skill[3] = new Skill(skill3,text3);
               skill[3].range = 1;
               skill[3].addType = Skill.DE;
               skill[3].skillType = Skill.DONG;
               skill[3].add = 10;
               skill[3].mpConsume = 10;
               skill[3].initEffect("/img/skill/3/1",7);
               break;
           }
           case 3:{
               String text0 = "普通攻击\n威力：武力值*1\n距离：1\n造成基于武力值的物理伤害";
               skill[0] = new Skill(attack,text0);
               String text1 = "防御姿态\n降低接下来一回合自身所受到的所有伤害，如果本回合不想行动，可使用此技能";
               skill[1] = new Skill(defend,text1);
               String text2 = "火球\n威力：法强+30\n伤害类型： 魔法伤害\n距离：3\n消耗：10MP\n哪个火法不会火球呢？";
               skill[2] = new Skill(skill2,text2);
               skill[2].mpConsume = 10;
               skill[2].range = 3;
               skill[2].addType = Skill.AP;
               skill[2].add = 20;
               skill[2].focus = false;
               skill[2].initEffect("/img/skill/4/1",19);

               String text3 = "炎爆术\n威力：法强+60\n伤害类型： 魔法伤害\n距离：3\n消耗：25MP\n你等着，等到我有25点法力值的时候！";
               skill[3] = new Skill(skill3,text3);
               skill[3].mpConsume = 25;
               skill[3].range = 3;
               skill[3].addType = Skill.AP;
               skill[3].add = 40;
               skill[3].initEffect("/img/skill/4/1",19);
               skill[3].focus = false;
               break;
           }
           case 4:{
               String text0 = "普通攻击\n威力：武力值*1\n距离：1\n造成基于武力值的物理伤害";
               skill[0] = new Skill(attack,text0);
               String text1 = "防御姿态\n降低接下来一回合自身所受到的所有伤害，如果本回合不想行动，可使用此技能";
               skill[1] = new Skill(defend,text1);
               String text2 = "水疗\n威力：法强+10 恢复HP\n距离：3\n消耗：10MP\n我需要治疗！";
               skill[2] = new Skill(skill2,text2);
               skill[2].initEffect("/img/defend",13);
               skill[2].skillType = Skill.RECOVERY;
               skill[2].addType = Skill.HP;
               skill[2].add = 10;
               skill[2].range = 3;
               skill[2].mpConsume = 10;
               skill[2].focus = false;
               String text3 = "修养\n威力：法强+10 恢复MP\n距离：3\n消耗：25MP\n哦，一个合格的奶妈！等等，心灵震爆哪去了？";
               skill[3] = new Skill(skill3,text3);
               skill[3].initEffect("/img/defend",13);
               skill[3].skillType = Skill.RECOVERY;
               skill[3].addType = Skill.MP;
               skill[3].add = 10;
               skill[3].range = 3;
               skill[3].focus = false;
               skill[3].mpConsume = 25;
               break;
           }
           case 5:{
               String text0 = "普通攻击\n威力：武力值*1\n距离：1\n造成基于武力值的物理伤害";
               skill[0] = new Skill(attack,text0);
               String text1 = "防御姿态\n降低接下来一回合自身所受到的所有伤害，如果本回合不想行动，可使用此技能";
               skill[1] = new Skill(defend,text1);
               String text2 = "闪现\n距离：4\n消耗：1MP\n立即闪现到目标位置\n一种更加方便无害的大荒星陨,也许本质是THE WORLD";
               skill[2] = new Skill(skill2,text2);
               skill[2].mpConsume = 1;
               skill[2].range = 4;
               skill[2].initEffect("/img/move",8);
               skill[2].skillType = Skill.MOVE;
               String text3 = "正义的飞刀\n威力：武力值+40 物理伤害\n距离：2\n消耗：10MP\n来骗，来偷·袭！我69岁的老蛇妖！这好吗？这不好。";
               skill[3] = new Skill(skill3,text3);
               skill[3].initEffect("/img/attack/1",4);
               skill[3].range = 2;
               skill[3].add = 40;
               skill[3].mpConsume = 10;
               break;
           }
       }
       skill[0].initEffect("/img/attack/1",4);
       skill[1].setSkillType(Skill.BUFF);
       skill[1].setAddType(Skill.DE);
       skill[1].setAdd(20);
       skill[1].setRange(0);
       skill[1].initEffect("/img/defend",4);
       skill[1].focus=false;
    }
    public void init(){
        specie = SpecieType.HuLuWa;
        HP = HPList[rank];
        MP = MPList[rank];
        maxHP = HP;
        maxMP = MP;
        ATK = ATKList[rank];
        AP = APList[rank];
        AD = ADList[rank];
        MD = MDList[rank];
        SP = SPList[rank];
        action = true;
        isSkiped = false;
        drawable = true;
        buffList.clear();
    }
}
