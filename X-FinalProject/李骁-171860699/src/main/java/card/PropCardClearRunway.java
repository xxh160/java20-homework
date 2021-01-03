package card;

import view.MainCanvas;
import runway.Runway;

public class PropCardClearRunway extends DraggableCard {

    public PropCardClearRunway() {
        loadImage(this.getClass().getSimpleName() + ".png");
    }

    @Override
    protected void cardAction() {
        runway.removeAllCreatures();
        MainCanvas.sendMessage("clearRunway," + runway.getId());
    }
    
}