package cn.edu.nju.field;
import cn.edu.nju.role.Creature;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;

public class BattleField <T extends Creature>{
    private int rowNumInit = 50;
    private int columnNumInit = 50;
    private final ArrayList <ArrayList<T>> creatureGrid = new ArrayList<>();
    private final ArrayList <ArrayList<Terrain>> terrainGrid = new ArrayList<>();
    private Class<T> kind;
    public BattleField(Class<T> kind) {
        this.kind = kind;
        for(int i = 0; i< rowNumInit; i++){
            addColumn();
        }
    }
    public BattleField(Class<T> kind,int row,int column){
        rowNumInit = row;
        columnNumInit = column;
        this.kind = kind;
        for(int i = 0; i< rowNumInit; i++){
            addColumn();
        }
    }

    public void addColumn(){
        ArrayList<T> newColumn = new ArrayList<>();
        ArrayList<Terrain> newTColumn = new ArrayList<>();
        try {
            for(int i = 0; i< columnNumInit; i++){
                    int rowIndex = creatureGrid.size();
                    T c = kind.newInstance();
                    c.setPos(rowIndex,i);
                    newColumn.add(c);
                    newTColumn.add(new Terrain(Terrain.TerrainType.Plain));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        creatureGrid.add(newColumn);
        terrainGrid.add(newTColumn);
    }
    public  void  set(int row,int column, T c){
        ArrayList<T> array = creatureGrid.get(row);
        array.set(column,c);
        c.setPos(row,column);
    }
    public Creature getCreature(int row, int column){
        ArrayList<T> array = creatureGrid.get(row);
        return array.get(column);
    }
    public ArrayList<T> getCreature(int row){
        return creatureGrid.get(row);
    }
    public Terrain getTerrain(int row,int column){
        ArrayList<Terrain> array = terrainGrid.get(row);
        return array.get(column);
    }
    public ArrayList<Terrain> getTerrain(int row){
        return terrainGrid.get(row);
    }
    public int getRowNum(){
        return creatureGrid.size();
    }
    public int getColNum() { return creatureGrid.get(0).size();}
    public void clear(){
        for(int i = 0;i<rowNumInit;i++){
            clear(i);
        }
    }
    public void clear(int column){
        ArrayList<T> array = creatureGrid.get(column);
        for(T c:array){
            c.clear();
        }
    }
    public ArrayList<T> getSpecieArray(int row, Creature.SpecieType specie){
        ArrayList<T> rowArray = creatureGrid.get(row);
        ArrayList<T> array = (ArrayList<T>) CollectionUtils.select(rowArray, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                T t = (T)o;
                return t.getSpecie()==specie;
            }
        });
        return array;
    }
    public boolean isTargetWalkable(int x,int y){
        if(x>=0&&x<columnNumInit&&y>=0&&y<rowNumInit){
            Creature c = getCreature(y,x);
            if(c.isAir()){
                return true;
            }
        }
        return false;
    }
}
