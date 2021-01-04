package cn.edu.nju.role;


import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class Skill {
    public final static int ATTACK = 0;
    public final static int BUFF = 1;
    public final static int MOVE = 2;
    public final static int RECOVERY = 3;
    public final static int DONG = 4;

    public final static int ATK = 0;
    public final static int AP = 1;
    public final static int AD = 2;
    public final static int MD = 3;
    public final static int DE = 4;
    public final static int HP = 5;
    public final static int MP = 6;

    int range;
    int scope;
    int hpConsume;
    int mpConsume;
    int length;
    int round;
    boolean focus;

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    int addType; // 武力值，法强值
    int skillType; // 普通攻击，防御,贯穿，跳跃，闪现，治疗，召唤, buff...
    int add;

    public void setAdd(int add) {
        this.add = add;
    }

    ArrayList<Image> effectList = new ArrayList<>();
    Image img;
    String text;
    Skill(Image img){
        this.img = img;
        text = null;
    }
    Skill(Image image,String string){
        range = 1;
        addType = ATK;
        add = 0;
        skillType = ATTACK;
        img = image;
        text = string;
        round = 1;
        focus = true;
    }
    int computeDamage(int ATK,int AP,int AD,int MD){
        double res;
        if(addType == Skill.ATK){
            res = (ATK+add)*(1-(float)AD/100);
        }
        else if(addType == Skill.AP){
            res = (AP+add)*(1-(float)MD/100);
        }
        else{
            res = (AD+MD+add)*(1-(float)MD/100);
        }
        return (int)res;
    }
    void setText(String str){
        this.text = str;
    }
    public Image getImg(){
        return img;
    }
    public String getText(){
        return text;
    }
    public int getRange(){return range;}
    public int getSkillType(){
        return skillType;
    }
    public int getFrameNum(){
        return effectList.size();
    }
    public Image getFrame(int index){
        return effectList.get(index);
    }


    public void setRange(int range) {
        this.range = range;
    }

    public int getAddType() {
        return addType;
    }

    public void setAddType(int addType) {
        this.addType = addType;
    }

    public void setSkillType(int skillType) {
        this.skillType = skillType;
    }


    public int getHpConsume() {
        return hpConsume;
    }

    public void setHpConsume(int hpConsume) {
        this.hpConsume = hpConsume;
    }

    public int getMpConsume() {
        return mpConsume;
    }

    public void setMpConsume(int mpConsume) {
        this.mpConsume = mpConsume;
    }

    public void initEffect(String path,int frameNum){
        for (int i = 1; i <= frameNum; i ++) {
            String imgPath = path+"/"+i+".png";
            Image img = new Image(imgPath);
            effectList.add(img);
        }

    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
    public Boolean getFocus(){
        return focus;
    }
}
