package view;

import javax.swing.text.html.HTMLDocument.BlockElement;

import javafx.scene.text.Text;

public class Hero {

    private int blood = 10;

    private Text bloodText = new Text();

    private boolean belongToMe;

    public Hero(boolean belongToMe) {
        //MainCanvas.root.getChildren().add(bloodText);
        bloodText.setText((belongToMe == true? "我方": "敌方") + "血量：" + blood);
        MainCanvas.addToPane(bloodText);
        this.belongToMe = belongToMe;
    }

    public void setPosition(int posX, int posY) {
        bloodText.setLayoutX(posX);
        bloodText.setLayoutY(posY);
    }

    public void lossBlood(int n) {
        blood = blood - n;
        bloodText.setText((belongToMe == true? "我方": "敌方") + "血量：" + blood);
        if (blood <= 0) {
            //游戏结束
            if (belongToMe) {
                MainCanvas.enemyWin();
            }
            else {
                MainCanvas.iWin();
            }
        }
    }
}