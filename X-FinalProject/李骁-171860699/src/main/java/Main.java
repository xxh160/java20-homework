import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.application.Platform;

import java.util.concurrent.ExecutorService;

import creature.*;
import view.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 800, 600);
        
        // scene.setFill(Color.BLACK); //黑底，有button不生效，why？
        MainCanvas mainCanvas = new MainCanvas(primaryStage, root, 800, 600);
        // root.getChildren().add(mainCanvas);
        // //这一句加了直接导致此前在构造函数中生成的Node不生效，只有之后生成的Node才生效
        // root.setStyle("-fx-background:red;"); //设置pane的style

        // Setting the title to Stage.
        primaryStage.setTitle("葫芦娃大战妖精");

        // Adding the scene to Stage
        primaryStage.setScene(scene);

        // 设置关闭窗口时结束线程
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                MainCanvas.exec.shutdownNow();
                MainCanvas.runwayField.getRunways().forEach(runway -> runway.removeAllCreatures()); // 关闭计时器线程
                if (MainCanvas.server != null) {
                    MainCanvas.server.close();
                }
                if (MainCanvas.client != null) {
                    MainCanvas.client.close();
                }
                MainCanvas.recorder.close();
                //MainCanvas.close();
            }
        });

        // Displaying the contents of the stage
        primaryStage.show();

    }

    public static void main(String args[]) {
        launch(args);
    }
}