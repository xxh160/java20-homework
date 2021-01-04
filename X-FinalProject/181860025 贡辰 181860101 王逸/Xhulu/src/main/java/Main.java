import BattleField.Battlefield;
import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/sample.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Xhulu");
        primaryStage.getIcons().add(new Image(
                "/icon.png"));
        Controller controller = fxmlLoader.getController();
        controller.initController(primaryStage);
        primaryStage.setScene(new Scene(root, 1020, 900));


        primaryStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
