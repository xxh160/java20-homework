package card;

import view.MainCanvas;

public class PropCardCostMinusN extends ClickableCard {

    private int n = 1;

    public PropCardCostMinusN(int n) {
        this.n = n;
        loadImage(this.getClass().getSimpleName() + ".png");
    }

    @Override
    protected void cardAction() {
        //调用自己的cardField的cardsCostMinus(1)方法
        MainCanvas.cardField.cardsCostMinusN(n);
    }
}