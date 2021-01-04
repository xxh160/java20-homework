package card;

import view.MainCanvas;
import runway.Runway;

public class PropCardFreeze extends DraggableCard {

    public PropCardFreeze() {
        loadImage(this.getClass().getSimpleName() + ".png");
    }

    @Override
    protected void cardAction() {
        //TODO 发包给对面
        runway.freezeEnemyCreatures();
        MainCanvas.sendMessage("freezeRunway," + runway.getId());
        MainCanvas.recorder.recordOperation("enemy", "freezeRunway", runway.getId());
    }
}