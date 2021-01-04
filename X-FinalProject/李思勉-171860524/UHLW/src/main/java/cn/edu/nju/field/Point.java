package cn.edu.nju.field;

public  class Point{
    public int x;
    public int y;
    public int depth;
    public String direct;
    public Point(int x,int y,int d,String direct){
        this.x = x;
        this.y = y;
        this.depth = d;
        this.direct = direct;

    }
    public int hashCode()
    {
        return new Integer(x).hashCode();
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Point){
            Point p = (Point)o;
            return p.x==x&&p.y==y;
        }
        return false;
    }
}
