package BattleField;

public class Filerecord
{
    int x;
    int y;
    int time;
    Message msg;

    public Filerecord(String str)
    {
        String[] strbuf=str.split(" ");
        time=Integer.parseInt(strbuf[0]);
        x=Integer.parseInt(strbuf[1]);
        y=Integer.parseInt(strbuf[2]);
        switch (strbuf[3]) {
            case "UP":
                msg=Message.UP;
                break;
            case "DOWN":
                msg=Message.DOWN;
                break;
            case "LEFT":
                msg=Message.LEFT;
                break;
            case "RIGHT":
                msg=Message.RIGHT;
                break;
            case "A":
                msg=Message.ATTACK;
                break;
            case "Q":
                msg=Message.SKILL;
                break;
            default:break;
        }
    }

    public int getTime() {
        return time;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Message getMsg() {
        return msg;
    }
}
