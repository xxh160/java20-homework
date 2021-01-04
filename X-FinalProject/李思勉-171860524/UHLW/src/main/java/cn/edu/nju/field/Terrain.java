package cn.edu.nju.field;

import cn.edu.nju.role.Creature;
import javafx.scene.image.Image;

public  class Terrain {
    public enum TerrainType{
        Plain,Ice,Fire,Wall,RedBuff,BlueBuff,Eyes
    };
    private TerrainType type = TerrainType.Plain;
    public static Image plain = new Image("/img/floor/floor1.png");
    public static Image ice = new Image("/img/floor/floor20.png");
    public static Image[] imgList = new Image[]{plain,ice};
    public Terrain(){
        this.type = TerrainType.Plain;
    }
    public Terrain(TerrainType type){
        this.type = type;
    }
    public int getTypeInt(){
        return this.type.ordinal();
    }
    public TerrainType getType(){
        return this.type;
    }
    public boolean isWalkable(Creature.SpecieType specie){
        return false;
    }
    public void effect(){
    }






}
