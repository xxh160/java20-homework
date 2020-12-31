package card;

import view.MainCanvas;
import runway.Runway;

public abstract class DraggableCard extends Card {

    protected double lastEventX, lastEventY, lastX, lastY; // 控制卡片拖动的变量

    protected Runway runway; //要拖到的跑道

    protected DraggableCard() {
        setOnMousePressed();
        setOnMouseDragged();
        setOnMouseReleased();
    }

    protected void setOnMousePressed() {
        // 鼠标点击
        imageView.setOnMousePressed(e -> {
            if (MainCanvas.cardField.getMoney() >= price) {
                lastX = imageView.getTranslateX();
                lastY = imageView.getTranslateY();
                lastEventX = e.getSceneX();
                lastEventY = e.getSceneY();
                System.out.println("mouse pressed, v.x: " + lastX + ", v.y: " + lastY + ", e.x: " + lastEventX
                        + ", e.y: " + lastEventY);
            }
        });
    }

    protected void setOnMouseDragged() {
        // 鼠标拖动，钱够才允许拖
        imageView.setOnMouseDragged(e -> {
            if (MainCanvas.cardField.getMoney() >= price) {
                // 钱够了
                // System.out.println("mouse dragged");
                double dx = lastEventX - e.getSceneX();
                double dy = lastEventY - e.getSceneY();
                double nx = lastX - dx;
                double ny = lastY - dy;
                // System.out.println("nx: " + nx + ", ny: " + ny);
                imageView.setTranslateX(nx);
                imageView.setTranslateY(ny);
                //TODO 拖动时位于图形顶层，否则会被排在后面的卡牌覆盖
            }
        });
    }

    protected void setOnMouseReleased() {
        // 鼠标松开
        imageView.setOnMouseReleased(e -> {
            System.out.println("mouse released, sceney: " + e.getSceneY());
            // 能拖动的，且有钱，添加到跑道，或者回到原地
            double dx = e.getSceneX();
            double dy = e.getSceneY();
            boolean releaseOnRunway = false;
            int runwayIndex = 0; // 释放的跑道下标
            for (; runwayIndex < MainCanvas.runwayField.getRunwayFieldSize(); runwayIndex++) {
                int runwayX = MainCanvas.runwayField.getRunways().get(runwayIndex).getPosX();
                int runwayY = MainCanvas.runwayField.getRunways().get(runwayIndex).getPosY();
                int runwayWidth = MainCanvas.runwayField.getRunways().get(runwayIndex).getWidth();
                if (dy > runwayY && dy < runwayY + runwayWidth) {
                    releaseOnRunway = true;
                    break;
                }
            }
            if (releaseOnRunway == true && MainCanvas.cardField.getMoney() >= price) {
                System.out.println("释放在跑道" + runwayIndex);
                System.out.println("价格为" + price);
                this.runway = MainCanvas.runwayField.getRunways().get(runwayIndex); //定位跑道
                if (canReleaseOnRunway()) { //如果可以放在跑道上，默认可以
                    cardAction(); // 卡牌生效
                    MainCanvas.cardField.removeCard(this); // 从卡牌区移除这张卡
                    MainCanvas.cardField.setMoney(MainCanvas.cardField.getMoney() - price); // 扣掉金币数
                } else {
                    // 放回原位
                    imageView.setTranslateX(0);
                    imageView.setTranslateY(0);
                    // imageView.setX(initX);
                    // imageView.setY(initY);
                }
            } else {
                // 放回原位
                imageView.setTranslateX(0);
                imageView.setTranslateY(0);
                // imageView.setX(initX);
                // imageView.setY(initY);
            }
            
        });
    }

    protected boolean canReleaseOnRunway() {
        return true;
    }
}