package Network;

import BattleField.Message;
import CreatrueClass.Creatrue;

public class Msg {
    private Message move;
    private int x;
    private int y;
    private String id;
    public Msg(String _id,int _x,int _y,Message _move){
        id = _id;
        move = _move;
        x = _x;
        y = _y;
    }
    public String toString(){
        return "("+x+","+y+")"+":"+move;
    }
    public String getId(){
        return id;
    }
}
