package card;

import view.MainCanvas;

public class PropCardCostAddN extends ClickableCard {

    private int n = 1;

    public PropCardCostAddN(int n) {
        this.n = n;
        loadImage(this.getClass().getSimpleName() + ".png");
    }

    @Override
    protected void cardAction() {
        //TODO 给对方发包，然后让他调用他的cardField的cardsCostPlus(1)方法
        //TODO 先试试自己的，等会删掉
        System.out.println("costAddN");
        //MainCanvas.cardField.cardsCostPlusN(n);
        MainCanvas.sendMessage("costAddN," + n);
    }
}