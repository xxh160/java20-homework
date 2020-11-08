public class Huluwa implements Comparable<Huluwa>{
    private String name;
    private int level;
    Huluwa(){
        name=null;
        level=0;
    }
    Huluwa(String name,int level){
        this.name=name;
        this.level=level;
    }

    public String getName(){
        return name;
    }
    public int getLevel(){
        return level;
    }

    @Override
    public int compareTo(Huluwa o) {
        return this.level-o.level;
    }

}