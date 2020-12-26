package card;

import view.MainCanvas;

public abstract class ClickableCard extends Card {

    protected boolean ready;

    protected ClickableCard() {
        setOnMousePressed();
        //setOnMouseReleased();
    }

    protected void setOnMousePressed() {
        // 鼠标点击
        imageView.setOnMousePressed(e -> {
            if (MainCanvas.cardField.getMoney() >= price) {
                cardAction(); //点击即生效
                MainCanvas.cardField.removeCard(this); //移除这张牌
            }
        });
    }

    /*protected void setOnMouseReleased() {
        imageView.setOnMouseReleased(e -> {
            if (MainCanvas.cardField.getMoney() >= price) {
                cardAction(); //点击即生效
                MainCanvas.cardField.removeCard(this); //移除这张牌
            }
        });
    }

    protected void setOnMouseExit() {
        imageView.setOnMouseExited(e-> {

        });
    }*/
}