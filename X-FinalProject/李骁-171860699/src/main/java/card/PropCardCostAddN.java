package card;

import view.MainCanvas;

public class PropCardCostAddN extends Card implements PropAction {

    private int n = 1;

    public PropCardCostAddN(int n) {
        //super(); 
        this.n = n;
        System.out.println(this.getClass().getSimpleName());
        loadImage(this.getClass().getSimpleName() + ".png");
        

        // 鼠标点击
        imageView.setOnMousePressed(e -> {
            propAction();
            MainCanvas.cardField.removeCard(this); //移除这张牌
        });
    }

    public void propAction() {
        //TODO 给对方发包，然后让他调用他的cardField的cardsCostPlus(1)方法
        //
        //TODO 先试试自己的，等会删掉
        System.out.println("costAddN");
        MainCanvas.cardField.cardsCostPlusN(n);
    }
}