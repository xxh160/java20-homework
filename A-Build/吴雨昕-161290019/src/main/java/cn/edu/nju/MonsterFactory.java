package cn.edu.nju;
public class MonsterFactory implements CreatureFactory<Monster>{
    @Override
    public Monster create() {
        return new Monster();
    }
}