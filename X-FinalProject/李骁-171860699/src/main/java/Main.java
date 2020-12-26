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
        //scene.setFill(Color.BLACK); //黑底，有button不生效，why？
        MainCanvas mainCanvas = new MainCanvas(root, 800, 600);
        //root.getChildren().add(mainCanvas); //这一句加了直接导致此前在构造函数中生成的Node不生效，只有之后生成的Node才生效
        //root.setStyle("-fx-background:red;"); //设置pane的style

        //Setting the title to Stage.
        primaryStage.setTitle("葫芦娃大战妖精");

        //Adding the scene to Stage
        primaryStage.setScene(scene);

        //设置关闭窗口时结束线程
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
                MainCanvas.exec.shutdownNow();
                MainCanvas.runwayField.getRunways().forEach(runway->runway.removeAllCreatures()); //关闭计时器线程
            }
        });

        //Displaying the contents of the stage
        primaryStage.show();

    }    
    public static void main(String args[]){          
       launch(args);     
    }    
}

/*
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.image.*;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        AnchorPane root = new AnchorPane();
        Controller controller = new Controller();
        ImageView iv = new ImageView();
        URL url = getClass().getClassLoader().getResource("huluwa_card.png");
        System.out.println(url);
        iv.setImage(new Image(url.toString()));
        iv.setX(400);
        iv.setY(400);
        iv.setFitWidth(100);
        iv.setFitHeight(100);
        controller.setDraggable(iv);
        root.getChildren().add(iv);
        controller.InitUi();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    class Controller {

        private ImageView draggable;

        private Double lastX = null;
        private Double lastY = null;

        public void setDraggable(ImageView iv) {
            draggable = iv;
        }

        public void InitUi() {
            if (this.draggable != null) {
                this.draggable.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        System.out.println("drag over, scenex: " + event.getSceneX() + ", sceney: " + event.getSceneY()
                        + ", x: " + event.getX() + ", y: " + event.getY());
                        //draggable.setX(event.getSceneX());
                        //draggable.setY(event.getSceneY());
                        HandleMouseMovement(event.getSceneX(), event.getSceneY());
                    }
                });

                this.draggable.setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        System.out.println("drag detected, scenex: " + event.getSceneX() + ", sceney: " + event.getSceneY()
                        + ", x: " + event.getX() + ", y: " + event.getY());
                        Dragboard db = draggable.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        content.putString("Does not matter");
                        db.setContent(content);
                        event.consume();

                        lastX = event.getSceneX(); 
                        lastY = event.getSceneY();
                    }
                });
            }
        }

        private synchronized void HandleMouseMovement(double sceneX, double sceneY) {
            double deltaX = sceneX - lastX;
            double deltaY = sceneY - lastY;

            lastX = sceneX;
            lastY = sceneY;

            double currentXAnchor = AnchorPane.getLeftAnchor(this.draggable);
            double currentYAnchor = AnchorPane.getTopAnchor(this.draggable);

            AnchorPane.setLeftAnchor(this.draggable, currentXAnchor + deltaX * 1);
            AnchorPane.setTopAnchor(this.draggable, currentYAnchor + deltaY * 1);
        }
    }
}*/

/*
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

    double lastEventX, lastEventY, lastX, lastY;

    double initX=300, initY=400;

    @Override
    public void start(Stage ps) throws Exception {
        AnchorPane pane = new AnchorPane();
        Image im = new Image("huluwa.png");
        ImageView imageView = new ImageView(im);
        double fact = im.getWidth() / im.getHeight();

        imageView.setX(initX);
        imageView.setY(initY);

        //view.setFitHeight(300);
        //view.setFitWidth(300 * fact);

        imageView.setOnMousePressed(e->{
            lastX = imageView.getTranslateX();
            lastY = imageView.getTranslateY();
            lastEventX = e.getSceneX();
            lastEventY = e.getSceneY();
            System.out.println("mouse pressed, v.x: " + lastX + ", v.y: "
            + lastY + ", e.x: " + lastEventX + ", e.y: " + lastEventY);
        });

        imageView.setOnMouseDragged(e->{
            //System.out.println("mouse dragged");
            double dx = lastEventX - e.getSceneX();
            double dy = lastEventY - e.getSceneY();
            double nx = lastX - dx;
            double ny = lastY - dy;
            System.out.println("nx: " + nx + ", ny: " + ny);
            imageView.setTranslateX(nx);
            imageView.setTranslateY(ny);
        });

        imageView.setOnMouseReleased(e->{
            System.out.println("mouse released, sceney: " + e.getSceneY());

            if (e.getSceneY() < 120 && e.getSceneY() > 100) {
                //do nothing
            }
            else {
                imageView.setTranslateX(0);
                imageView.setTranslateY(0);
                //imageView.setX(initX);
                //imageView.setY(initY);
            }
        });
        

        pane.getChildren().add(imageView);

        Scene scene = new Scene(pane, 500, 500);

        ps.setScene(scene);
        ps.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}*/