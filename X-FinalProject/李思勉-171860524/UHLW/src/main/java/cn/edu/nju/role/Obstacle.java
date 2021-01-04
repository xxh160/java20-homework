package cn.edu.nju.role;


import javafx.scene.image.Image;

public class Obstacle extends Creature{
    Image img = new Image("/img/Entity/stone1.png");
    public Obstacle(){
        super();
        drawable = true;
        specie = SpecieType.Stone;
    }
    @Override
    public Image getSWalk(){
        return img;
    }
}
