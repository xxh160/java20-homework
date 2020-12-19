import controller.PlayAction;
import entity.FiveChess;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import view.ChessPane;

import javax.print.attribute.standard.Fidelity;

public class Test extends Application {

 public static void main(String[] args) {
 launch(args);
 }


 @Override
 public void start(Stage primaryStage) {
 FiveChess fiveChess = new FiveChess(20,20,28.0);
 ChessPane chesspane=new ChessPane(fiveChess);

 chesspane.setOnMouseClicked(new PlayAction(fiveChess,chesspane));//事件源绑定处理器

 Scene scene=new Scene(chesspane,800,700);
 primaryStage.setScene(scene);
 primaryStage.setTitle("五子棋游戏");
 primaryStage.show();

 }
}