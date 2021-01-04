package cn.edu.nju.main;

import cn.edu.nju.web.Connection;
import com.sun.tools.javac.Main;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartGameController {
    @FXML
    private Button load;
    @FXML
    private Button create;
    @FXML
    private Button join;
    private Connection connection;
    private MainPart mainPart;

    @FXML
    private void initialize (){
        System.out.println("初始化游戏选择界面");
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    onCreate(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        join.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    onJoin(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onLoad();
            }
        });
    }

    private void onCreate(ActionEvent event) throws Exception{
       String name = "等待他人加入游戏...";
       connection = new Connection();
       Task task = connection.getCreateTask();

       task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
           @Override
           public void handle(WorkerStateEvent event) {
               mainPart.closeLoadingUI();
               try {
                   mainPart.setConnection(connection);
                   mainPart.showMainUI(0);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       });
       mainPart.showLoadingUI(mainPart.getPrimaryStage(),name);
       new Thread(task).start();
    }

    private void onJoin (ActionEvent event)throws Exception{
        String name = "寻找房间中...";
        connection = new Connection();
        Task task = connection.getFindTask();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                boolean res = (Boolean) task.getValue();
                mainPart.closeLoadingUI();
                if(res){
                    try {
                        mainPart.setConnection(connection);
                        mainPart.showMainUI(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    mainPart.showAlert("错误","无法找到玩伴");
                }

            }
        });
        mainPart.showLoadingUI(mainPart.getPrimaryStage(),name);
        new Thread(task).start();
    }

    private void onLoad(){
        mainPart.showRelayUI();
    }

    public  void setMainPart(MainPart mainPart){
        this.mainPart = mainPart;
    }

}
