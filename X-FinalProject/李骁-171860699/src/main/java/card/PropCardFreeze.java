package card;

import view.MainCanvas;
import runway.Runway;

public class PropCardFreeze extends DraggableCard {

    public PropCardFreeze() {
        loadImage(this.getClass().getSimpleName() + ".png");
    }

    public void propAction() {
        //TODO 发包给对面
        runway.freezeEnemyCreatures();
    }
}