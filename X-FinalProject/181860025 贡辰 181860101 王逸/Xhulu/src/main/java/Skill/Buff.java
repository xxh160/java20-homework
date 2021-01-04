package Skill;

public class Buff
{
    Bufftype bufftype;
    int value;
    public Buff()
    {
        bufftype=null;
        value=0;
    }

    public Buff(Bufftype bufftype,int value)
    {
        this.value=value;
        this.bufftype=bufftype;
    }

    public Bufftype getBufftype() {
        return bufftype;
    }

    public int getValue() {
        return value;
    }

    public void setBufftype(Bufftype bufftype) {
        this.bufftype = bufftype;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
