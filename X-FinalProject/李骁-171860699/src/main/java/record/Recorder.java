package record;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.time.Duration;
import java.time.Instant;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import creature.Dawa;
import creature.*;

import java.util.TimerTask;
import java.util.Date;
import java.text.SimpleDateFormat;

import view.MainCanvas;

public class Recorder {

    private List<String> recordingList = new LinkedList<String>(); // 正在记录的列表

    private Instant recordingStartTime = null; // 游戏开始的时间，由启动客户端成功连上服务器的时间开始计算

    private ScheduledExecutorService executeService = Executors.newSingleThreadScheduledExecutor(); // 被冰冻的调度器，可以结束线程

    private Instant fileStartTime = null; // 读取文件的开始时间

    public Recorder() {
        initReadButton();
        initSaveButton();
    }

    public void start() { // 服务器客户端连接上后调用
        recordingStartTime = Instant.now();
    }

    public void initReadButton() {
        Button btnReadRecordFile = new Button("回放录像文件");
        btnReadRecordFile.setLayoutX(360);
        btnReadRecordFile.setLayoutY(550);
        btnReadRecordFile.setPrefWidth(100);
        btnReadRecordFile.setPrefHeight(40);
        btnReadRecordFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(MainCanvas.primaryStage);
                if (file != null) {
                    System.out.println("打开录像文件：" + file);
                    excuteRecordFile(file); // 执行录像文件
                }
            }
        });
        MainCanvas.addToPane(btnReadRecordFile);
    }

    public void initSaveButton() {
        Button btnReadRecordFile = new Button("保存本局录像");
        btnReadRecordFile.setLayoutX(520);
        btnReadRecordFile.setLayoutY(550);
        btnReadRecordFile.setPrefWidth(100);
        btnReadRecordFile.setPrefHeight(40);
        btnReadRecordFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // 按时间保存本局目前录像
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                fileChooser.setInitialFileName("record.txt");
                File file = fileChooser.showSaveDialog(MainCanvas.primaryStage);
                if (file != null) {
                    System.out.println("保存到文件：" + file);
                    saveRecordToFile(file);
                }
            }
        });
        MainCanvas.addToPane(btnReadRecordFile);
    }

    public void recordOperation(String iOrEnemy, String type, int param) {
        if (recordingStartTime != null) {
            long timeDure = Duration.between(recordingStartTime, Instant.now()).toMillis(); // 开始游戏到现在的时间差，单位秒
            String recordToAdd = new String(
                    Long.toString(timeDure) + " " + iOrEnemy + " " + type + " " + Integer.toString(param));
            System.out.println("记录：" + recordToAdd);
            recordingList.add(recordToAdd);
        } else {
            System.out.println("记录失败，记录器未启动");
        }
    }

    public void saveRecordToFile(File file) {
        try {
            if (!file.isFile()) {
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String line : recordingList) {
                writer.write(line + "\r\n");
            }
            writer.close();
            System.out.println("保存文件成功：" + file);
        } catch (IOException e) {
            System.out.println("保存文件失败：" + file);
        }
    }

    public void excuteRecordFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // 初始化开始时间
            fileStartTime = Instant.now();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
                excuteRecord(str);
            }

            // close
            inputStream.close();
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("读取文件失败：" + file);
        }
    }

    public void excuteRecord(String record) {
        // 拆分
        // 时间用来schedule将来的线程
        // timetask内容为执行命令

        String[] cmds = record.split(" ");
        if (cmds.length == 4) {
            final long ms = Long.parseLong(cmds[0]);
            final String iOrEnemy = cmds[1];
            final String type = cmds[2];
            final int n = Integer.parseInt(cmds[3]);

            TimerTask execTask = new TimerTask() {
                @Override
                public void run() {
                    doRecord(iOrEnemy, type, n);
                }
            };
            executeService.schedule(execTask, ms, TimeUnit.MILLISECONDS);
        } else {
            System.out.println("未识别的记录：" + record);
        }
    }

    public void doRecord(String iOrEnemy, String type, int n) {
        if (type.startsWith("add")) {
            String className = type.substring(3, type.length());
            System.out.println("添加敌人类型：" + className);
            Creature creature = null;
            if (className.equals(Chuanshanjia.class.getSimpleName())) {
                creature = new Chuanshanjia();
            } else if (className.equals(Dawa.class.getSimpleName())) {
                creature = new Dawa();
            } else if (className.equals(Huowa.class.getSimpleName())) {
                creature = new Huowa();
            } else if (className.equals(Shuiwa.class.getSimpleName())) {
                creature = new Shuiwa();
            } else if (className.equals(Xiezijing.class.getSimpleName())) {
                creature = new Xiezijing();
            } else if (className.equals(Shejing.class.getSimpleName())) {
                creature = new Shejing();
            } else if (className.equals(Wugongjing.class.getSimpleName())) {
                creature = new Wugongjing();
            } else if (className.equals(Qingwajing.class.getSimpleName())) {
                creature = new Qingwajing();
            } else {
                creature = null;
            }
            // TODO 增加更多类型

            if (creature != null) {
                if (iOrEnemy.equals("i")) {
                    MainCanvas.runwayField.getRunways().get(n).addToMyCreatures(creature);
                } else if (iOrEnemy.equals("enemy")) {
                    MainCanvas.runwayField.getRunways().get(n).addToEnemyCreatures(creature);
                } else {
                    System.out.println("未识别的人物：" + iOrEnemy);
                }
            } else {
                System.out.println("添加失败：" + className);
            }
        } else if (type.equals("clearRunway")) {
            MainCanvas.runwayField.getRunways().get(n).removeAllCreatures();

        } else if (type.equals("freezeRunway")) {
            if (iOrEnemy.equals("i")) {
                MainCanvas.runwayField.getRunways().get(n).freezeMyCreatures();
            } else if (iOrEnemy.equals("enemy")) {
                MainCanvas.runwayField.getRunways().get(n).freezeEnemyCreatures();
            } else {
                System.out.println("未识别的人物：" + iOrEnemy);
            }
        } else if (type.equals("killHead")) {
            if (iOrEnemy.equals("i")) {
                MainCanvas.runwayField.getRunways().get(n).killMyHead();
            } else if (iOrEnemy.equals("enemy")) {
                MainCanvas.runwayField.getRunways().get(n).killEnemyHead();
            } else {
                System.out.println("未识别的人物：" + iOrEnemy);
            }
        } else {
            System.out.println("未识别的消息");
        }

    }

    public void close() {
        // 关闭窗口时关闭
        executeService.shutdownNow();
    }
}