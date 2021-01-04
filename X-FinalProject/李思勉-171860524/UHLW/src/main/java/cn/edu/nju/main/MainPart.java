package cn.edu.nju.main;
import cn.edu.nju.record.HLWRecord;
import cn.edu.nju.web.Connection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
// TODO:加入石头

public class MainPart extends Application {
    private BorderPane mainLayout;
    private BorderPane startLayout;
    private VBox loadingLayout;
    private VBox overLayout;

    private Scene mainScene;
    private Scene startScene;
    private Scene loadingScene;
    private Scene overScene;

    private Stage primaryStage;
    private Stage loadingStage;
    private Stage overStage;

    private LoadingController loadingController;
    private StartGameController startGameController;
    private Controller controller;
    private GameOverController gameOverController;
    private Connection connection;



    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Under HuLuWa");
        primaryStage.getIcons().add(new Image("/img/sans.png"));
        FXMLLoader mainLoader = new FXMLLoader();
        FXMLLoader startLoader = new FXMLLoader();
        FXMLLoader loadingLoader = new FXMLLoader();
        FXMLLoader overLoader = new FXMLLoader();

        loadingLoader.setLocation(getClass().getResource("/fxml/Loading.fxml"));
        mainLoader.setLocation(getClass().getResource("/fxml/MainUI.fxml"));
        startLoader.setLocation(getClass().getResource("/fxml/StartGame.fxml"));
        overLoader.setLocation(getClass().getResource("/fxml/GameOver.fxml"));

        mainLayout = mainLoader.load();
        startLayout = startLoader.load();
        loadingLayout = loadingLoader.load();
        overLayout = overLoader.load();

        loadingController = loadingLoader.getController();
        startGameController = startLoader.getController();
        controller = mainLoader.getController();
        gameOverController = overLoader.getController();


        startGameController.setMainPart(this);
        gameOverController.setMainPart(this);
        controller.setMainPart(this);

        mainScene = new Scene(mainLayout);
        startScene = new Scene(startLayout);
        loadingScene = new Scene(loadingLayout);
        loadingScene.setFill(null);
        overScene = new Scene(overLayout);
        overScene.setFill(null);



        loadingStage = new Stage();
        loadingStage.setScene(loadingScene);
        loadingStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.initStyle(StageStyle.TRANSPARENT);
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.initOwner(primaryStage);

        overStage = new Stage();
        overStage.setScene(overScene);
        overStage.initStyle(StageStyle.UNDECORATED);
        overStage.initStyle(StageStyle.TRANSPARENT);
        overStage.initModality(Modality.APPLICATION_MODAL);
        overStage.initOwner(primaryStage);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("关闭游戏主窗口");
                if(connection!=null){
                    connection.close();
                    HLWRecord.endRecord();
                    Platform.exit();
                }
                primaryStage.close();
            }
        });

        HLWRecord.setController(controller);
        HLWRecord.setMainPart(this);

        showStartUI();
    }
    public void showMainUI(int playerID)throws Exception{
        controller.setConnection(connection);
        controller.setPlayerID(playerID);
        primaryStage.setScene(mainScene);
    }
    public void showStartUI()throws Exception{
        primaryStage.setScene(startScene);
        primaryStage.show();
    }
    public void showLoadingUI(Stage parent, String name)throws Exception{
        loadingController.setLoadingName(name);

        loadingStage.setWidth(name.length() * 12 + 10);
        loadingStage.setHeight(100);
        double x = parent.getX() + (parent.getWidth() - loadingStage.getWidth()) / 2;
        double y = parent.getY() + (parent.getHeight() - loadingStage.getHeight()) / 2;
        loadingStage.setX(x);
        loadingStage.setY(y);
        loadingStage.show();
    }
    public void showOverUI(Stage parent, String state)throws  Exception{
        gameOverController.setState(state);
        overStage.setWidth(500);
        overStage.setHeight(350);
        double x = parent.getX() + (parent.getWidth() - overStage.getWidth()) / 2;
        double y = parent.getY() + (parent.getHeight() - overStage.getHeight()) / 2;
        overStage.setX(x);
        overStage.setY(y);
        overStage.show();
    }
    public void showRelayUI(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("选择回放记录");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SAV","*.sav"));
        File filepath = new File("record");
        if(!filepath.exists()){
            filepath.mkdir();
        }
        fileChooser.setInitialDirectory(filepath);
        File newFolder = fileChooser.showOpenDialog(primaryStage);
        if(newFolder!=null){
            primaryStage.setScene(mainScene);
            HLWRecord.startPlay(newFolder.getPath());
        }

    }


    public void showAlert(String header, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initOwner(primaryStage);
        alert.show();
    }

    public void closeLoadingUI(){
        loadingStage.close();
    }
    public void closeOverUI(){
        overStage.close();
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Stage getPrimaryStage(){return  primaryStage;}
    public Scene getStartScene(){return startScene;}

    public static void main(String[] args) {
        launch(args);
    }
}
