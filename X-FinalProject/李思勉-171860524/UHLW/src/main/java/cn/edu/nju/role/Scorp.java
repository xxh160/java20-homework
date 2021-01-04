package cn.edu.nju.role;

import javafx.scene.image.Image;

public class Scorp extends  Creature{
    public Scorp(){
        ID = count++;
        head = new Image("/img/head/xiezi.jpeg");
        walkMap = Creature.getWalkMap("/img/walk/xie/");
        name = "龐骸";
        gender = 0;
        creatureList.add(this);
        init();
        initSkill();
    }
    void initSkill(){
        Image skill2 = new Image("/img/skill/xiezi/1.png",40,40,false,false);
        Image  skill3 = new Image("/img/skill/xiezi/2.png",40,40,false,false);
        String text0 = "普通攻击\n威力：武力值+0\n距离：1\n造成基于武力值的物理伤害";
        skill[0] = new Skill(attack,text0);
        String text1 = "防御姿态\n降低接下来一回合自身所受到的所有伤害，如果本回合不想行动，可使用此技能";
        skill[1] = new Skill(defend,text1);
        String text2 = "寸劲·开天\n威力：武力值+80 \n距离：1\n消耗：5MP\n从劲夫处偷学的绝密拳法，对前方单个敌人造成巨额物理伤害\n必须要有一个巫妖王！";
        skill[2] = new Skill(skill2,text2);
        skill[2].initEffect("/img/skill/xiezi/1",8);
        skill[2].add = 80;
        skill[2].range = 1;
        skill[2].mpConsume = 5;
        String text3 = "狩猎直觉\n消耗：100MP\n范围：4\n向目标地点发起冲锋\n啊啊啊好急！，我打不到人啊啊啊啊，蝎子精也要学会闪现！！\n这个蝎子只不过是突然学会了前后移动而已。";
        skill[3] = new Skill(skill3,text3);
        skill[3].initEffect("/img/move",8);
        skill[3].range = 3;
        skill[3].skillType = Skill.MOVE;
        skill[3].mpConsume = 100;

        skill[0].initEffect("/img/attack/1",4);
        skill[1].setSkillType(Skill.BUFF);
        skill[1].setAddType(Skill.DE);
        skill[1].setAdd(20);
        skill[1].setRange(0);
        skill[1].initEffect("/img/defend",13);
    }
    public void init(){
        specie = SpecieType.Monster;
        HP = 600;
        MP = 200;
        ATK = 40;
        AP = 0;
        AD = 50;
        MD = 10;
        SP = 4;
        maxHP = HP;
        maxMP = MP;
        maxSP = SP;
        action = true;
        isSkiped = false;
        drawable = true;
        buffList.clear();
    }
}
