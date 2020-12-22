import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.image.*;

import creature.*;
import view.*;

public class Main extends Application {
    @Override     
    public void start(Stage primaryStage) throws Exception {            
        
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.BLACK); //黑底，有button不生效，why？
        MainCanvas mainCanvas = new MainCanvas(root, 800, 600);
        root.getChildren().add(mainCanvas);
        root.setStyle("-fx-background:red;"); //设置pane的style

        //Setting the title to Stage.
        primaryStage.setTitle("葫芦娃大战妖精");

        //Adding the scene to Stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();

    }    
    public static void main(String args[]){          
       launch(args);     
    }    
}