package cn.edu.nju.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class LoadingController {
    @FXML
    Label loadingLabel;
    @FXML
    ProgressIndicator indicator;
    @FXML
    VBox vBox;
    private  MainPart mainPart;
    @FXML
    public void initialize (){
        vBox.setSpacing(10);
        CornerRadii cornerRadii = new CornerRadii(10);
        BackgroundFill backgroundFill = new BackgroundFill( Color.rgb(144,147,153,0.8),cornerRadii,null);
        vBox.setBackground(new Background(backgroundFill));
    }
    public void setLoadingName(String name){
        loadingLabel.setText(name);
    }
    public void setMainPart(MainPart mainPart){
        this.mainPart = mainPart;
    }


}
