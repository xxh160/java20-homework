package cn.edu.nju.role;
import cn.edu.nju.field.BattleField;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OldMan extends Creature{
    public OldMan(){
        ID =count++;
        head = new Image("img/head/yeye.jepg");
        walkMap = Creature.getWalkMap("/img/walk/yeye/");
        this.specie = SpecieType.OldMan;
        drawable = true;
        posx = 0;
        posy = 0;
        name = "OldMan";
        creatureList.add(this);
    }
    public void walkTo(BattleField<Creature> battleField, int row, int column){
        System.out.println("爷爷:'我的老寒腿啊'");
    }
    public void countOff(BattleField<Creature> battleField,int row){
        ArrayList<Creature> array = battleField.getCreature(row);
        //int count = 1;
        for (Creature c:array){
            if(c.specie==SpecieType.HuLuWa){
                HuLuWa huLuWa = (HuLuWa)c;
                //System.out.print(String.valueOf(count++)+" ");
                huLuWa.tellName();
            }
        }
        System.out.println();
    }
}
