package app;

import java.io.File;
import java.util.Optional;

import client.Client;
import game.BattleGround;
import game.model.Character;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.LoadLog;
import server.Server;
import tool.connection.Message;
import tool.connection.MessageType;
import tool.log.LogErrorException;
import tool.log.LogReader;

public class APP extends Application {
    private Pane root;
    private Scene scene;
    private Client client;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        Button btn1 = new Button();
        btn1.setLayoutX(100);
        btn1.setLayoutY(100);
        btn1.setText("作为客户端启动");
        root.getChildren().add(btn1);

        Button btn2 = new Button();
        btn2.setLayoutX(100);
        btn2.setLayoutY(200);
        btn2.setText("作为服务器启动");
        root.getChildren().add(btn2);

        Button btn3 = new Button();
        btn3.setLayoutX(100);
        btn3.setLayoutY(300);
        btn3.setText("  加载存档日志  ");
        root.getChildren().add(btn3);

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(btn1);
                root.getChildren().remove(btn2);
                root.getChildren().remove(btn3);
                ClientAction(primaryStage);
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(btn1);
                root.getChildren().remove(btn2);
                root.getChildren().remove(btn3);
                ServerAction(primaryStage);
            }
        });
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(btn1);
                root.getChildren().remove(btn2);
                root.getChildren().remove(btn3);
                LoadAction(primaryStage);
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.L){
                    root.getChildren().remove(btn1);
                    root.getChildren().remove(btn2);
                    root.getChildren().remove(btn3);
                    LoadAction(primaryStage);
                }
            }
        });
        scene = new Scene(root, 400, 400);
        scene.getRoot().requestFocus();
        primaryStage.setTitle("葫芦娃大战妖精");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initUI() {
        // Scene(root, 1400, 1000);
        scene.getWindow().setWidth(1400);
        scene.getWindow().setHeight(1000);
        scene.getWindow().centerOnScreen();
        Image back_img = new Image(getClass().getClassLoader().getResource("Background.png").toString());
        ImageView backimageView = new ImageView();
        backimageView.setImage(back_img);
        root.getChildren().add(backimageView);
        backimageView.setLayoutX(93);
        backimageView.setLayoutY(90);
        BattleGround.setPane(root);

    }

    public void ClientAction(Stage primaryStage) {

        TextInputDialog dialog = new TextInputDialog("127.0.0.1");
        dialog.setTitle("IP地址");
        dialog.setHeaderText("请输入服务器IP地址:");
        dialog.setContentText("请输入服务器IP地址:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        String str = null;
        initUI();
        if (result.isPresent()) {
            str = result.get();
            // System.out.println("服务器IP地址 " + str);
        }

        client = new Client(root, str);
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Character c = BattleGround.getNowCharacter();
                KeyCode key = event.getCode();
                // System.out.println(key);
                if (key == KeyCode.W) {
                    client.sendMessage(new Message(MessageType.MOVEUP, BattleGround.getNOwCharacterIndex(), null));
                } else if (key == KeyCode.S) {
                    client.sendMessage(new Message(MessageType.MOVEDOWN, BattleGround.getNOwCharacterIndex(), null));
                } else if (key == KeyCode.A) {
                    client.sendMessage(new Message(MessageType.MOVELEFT, BattleGround.getNOwCharacterIndex(), null));
                } else if (key == KeyCode.D) {
                    client.sendMessage(new Message(MessageType.MOVERIGHT, BattleGround.getNOwCharacterIndex(), null));
                } else if (key == KeyCode.J) {
                    client.sendMessage(new Message(MessageType.ATTACT, BattleGround.getNOwCharacterIndex(), null));
                } else if (key == KeyCode.K) {
                    client.sendMessage(new Message(MessageType.SKILL, BattleGround.getNOwCharacterIndex(), null));
                } else if (key == KeyCode.H) {
                    BattleGround.lastFlag();
                } else if (key == KeyCode.L) {
                    BattleGround.nextFlag();
                }
            }
        });
        scene.getRoot().requestFocus();
    }

    public void ServerAction(Stage primaryStage) {
        new Server(root);
    }

    public void LoadAction(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择存档文件");
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("LOG", "*.log"));
        File file = null;
        LogReader login = null;
        while (true) {
            file = fileChooser.showOpenDialog(primaryStage.getOwner());
            if (file == null) {
                return;
            }
            try {
                login = new LogReader(file.getAbsolutePath());
                break;
            } catch (LogErrorException e) {
                // TODO: handle wrong log
                Alert alert = new Alert(AlertType.ERROR);
                alert.titleProperty().set("错误");
                alert.headerTextProperty().set("日志文件错误");
                alert.showAndWait();
                // System.out.println("error");
            }
        }
        initUI();
        BattleGround.setCharacterView();
        scene.getRoot().requestFocus();
        LoadLog load = new LoadLog(login, root);
        load.start();

        // System.out.println(file);
    }

    public static void main(String[] args) {
        launch(args);
    }
}