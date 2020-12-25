package card;

import view.MainCanvas;

public class PropCardCostMinusN extends Card implements PropAction {

    private int n = 1;

    public PropCardCostMinusN(int n) {
        this.n = n;

        loadImage(this.getClass().getSimpleName() + ".png");
        
        // 鼠标点击
        imageView.setOnMousePressed(e -> {
            propAction();
            MainCanvas.cardField.removeCard(this); //移除这张牌
        });
    }

    public void propAction() {
        //调用自己的cardField的cardsCostMinus(1)方法
        MainCanvas.cardField.cardsCostMinusN(n);
    }
}