package cn.edu.nju.role;

import javafx.scene.image.Image;

public class Snake extends Creature{
    public Snake(){
        ID = count++;
        specie = SpecieType.Monster;
        head = new Image("/img/head/sheyao.jpeg");
        walkMap = Creature.getWalkMap("/img/walk/she/");
        name = "蛇妖";
        gender = 0;
        init();
        creatureList.add(this);
        initSkill();
    }
    void initSkill(){
        Image skill2 = new Image("/img/skill/sheyao/1.png",40,40,false,false);
        Image  skill3 = new Image("/img/skill/sheyao/2.png",40,40,false,false);
        String text0 = "普通攻击\n威力：武力值*1\n距离：1\n造成基于武力值的物理伤害";
        skill[0] = new Skill(attack,text0);
        String text1 = "防御姿态\n降低接下来一回合自身所受到的所有伤害，如果本回合不想行动，可使用此技能";
        skill[1] = new Skill(defend,text1);
        String text2 = "邪·木法天照\n威力：法强+30 \n距离：3\n消耗：100MP\n轮回眼，天照。";

        skill[2] = new Skill(skill2,text2);
        skill[2].initEffect("/img/skill/sheyao/1",14);
        skill[2].range = 3;
        skill[2].mpConsume = 100;
        skill[2].addType = Skill.AP;
        skill[2].add = 30;
        skill[2].focus = false;
        String text3 = "伪·石化凝视\n威力：法强+40 \n距离：2\n消耗：250MP\n石化受该术法影响的敌方单位，他们在下一回合无法使用任何技能";
        skill[3] = new Skill(skill3,text3);
        skill[3].range = 2;
        skill[3].mpConsume = 250;
        skill[3].addType = Skill.AP;
        skill[3].skillType = Skill.DONG;
        skill[3].add = 40;
        skill[3].initEffect("/img/skill/sheyao/2",10);
        skill[3].focus = false;

        skill[0].initEffect("/img/attack/1",4);
        skill[1].setSkillType(Skill.BUFF);
        skill[1].setAddType(Skill.DE);
        skill[1].setAdd(20);
        skill[1].setRange(0);
        skill[1].initEffect("/img/defend",13);
        skill[1].focus = false;
    }
    public void init(){
        specie = SpecieType.Monster;
        HP = 400;
        MP = 600;
        ATK = 20;
        AP = 30;
        AD = 20;
        MD = 50;
        SP = 5;
        maxHP = HP;
        maxMP = MP;
        maxSP = SP;
        action = true;
        isSkiped = false;
        drawable = true;
        buffList.clear();
    }
}
