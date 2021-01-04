import creature.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;

public class TestCreature extends Application {

    @Test
    public void testCollide() {
        Application.launch("1");
    }

    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 800, 600);
        // Setting the title to Stage.
        primaryStage.setTitle("葫芦娃大战妖精test");
        // Adding the scene to Stage
        primaryStage.setScene(scene);
        // Displaying the contents of the stage
        primaryStage.show();
        //primaryStage.hide();

        Dawa dawa = new Dawa();
        dawa.setPosX(50);
        dawa.setPosY(50);
        dawa.setFigureSize(50);
        Xiezijing xiezijing = new Xiezijing();
        xiezijing.setPosX(500);
        xiezijing.setPosY(50);
        xiezijing.setFigureSize(100);
        
        assertEquals(false, dawa.isCollide(xiezijing));
        xiezijing.setPosX(100);
        assertEquals(true, dawa.isCollide(xiezijing));
        xiezijing.setPosY(400);
        assertEquals(false, dawa.isCollide(xiezijing));
    }
}