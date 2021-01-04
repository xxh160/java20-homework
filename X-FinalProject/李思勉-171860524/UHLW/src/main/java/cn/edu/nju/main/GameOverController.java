package cn.edu.nju.main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class GameOverController {
    @FXML
    private Label stateLabel;
    @FXML
    private VBox overVBox;
    private MainPart mainPart;

    public void initialize (){
        //overVBox.setSpacing(10);
        CornerRadii cornerRadii = new CornerRadii(5);
        BackgroundFill backgroundFill = new BackgroundFill( Color.rgb(144,147,153,0.5),cornerRadii,null);
        overVBox.setBackground(new Background(backgroundFill));
        overVBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    mainPart.closeOverUI();
                    mainPart.showStartUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void setState(String name){
        stateLabel.setText(name);
    }
    public void setMainPart(MainPart mainPart){
        this.mainPart = mainPart;
    }
}
