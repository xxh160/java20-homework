package BattleField;

import CreatrueClass.Creatrue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Position
{
    private int x;
    private int y;
    private Creatrue content;
    private ImageView speffect;
    private Image img;
    public Position ()
    {
        this.x=-1;
        this.y=-1;
        this.content=null;
        this.speffect=new ImageView();
        this.img=null;
    }

    public Position (int x, int y)
    {
        this.x=x;
        this.y=y;
        this.content=null;
        this.speffect=new ImageView();
        this.img=null;
    }

    public void setX(int X) { this.x=X; }

    public void setY(int Y) { this.y=Y; }

    public void putin(Image img) {this.img=img;update();}

    public void putin(Creatrue creatrue)
    {
        this.content=creatrue;
    }

    public void clear()
    {
        this.content=null;
    }

    public void cleareffect() {this.img=null;}

    public ImageView getSpeffect() {return speffect;}

    public Creatrue getcontent()
    {
        return content;
    }

    public boolean haseffect()
    {
        return this.img!=null;
    }
    private void update()
    {
        speffect.setImage(this.img);
        speffect.setFitHeight(54);
        speffect.setFitWidth(62.5);
    }
}
