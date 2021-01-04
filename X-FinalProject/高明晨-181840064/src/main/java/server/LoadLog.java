package server;

import java.util.HashSet;
import java.util.Iterator;

import game.BattleGround;
import game.view.BulletView;
import game.view.CharacterView;
import game.view.SceneView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import tool.Direction;
import tool.log.LogEntry;
import tool.log.LogReader;

class UpdateScene extends Thread {
    Pane root;

    public UpdateScene(Pane root) {
        this.root = root;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        SceneView lastSV = null;
        while (true) {
            BattleGround.bulletViewSetLock.lock();
            HashSet<BulletView> H = new HashSet<>(BattleGround.bulletViewSet);
            BattleGround.bulletViewSetLock.unlock();
            for (Iterator<BulletView> iterator = H.iterator(); iterator.hasNext();) {
                BulletView bv;
                bv = iterator.next();
                if (bv.isDead()) {
                    BattleGround.bulletViewSetLock.lock();
                    BattleGround.bulletViewSet.remove(bv);
                    BattleGround.bulletViewSetLock.unlock();
                    Platform.runLater(() -> {
                        bv.removeScene(BattleGround.root);
                    });
                } else {
                    bv.update();
                }
            }

            for (int i = 0; i < 12; i++) {
                if (BattleGround.character[i] == null) {
                    continue;
                }
                CharacterView cv = BattleGround.characterView[i];
                if (BattleGround.character[i].isDead() && cv != null) {
                    Platform.runLater(() -> {
                        cv.removeScene(BattleGround.root);
                    });

                    BattleGround.characterView[i] = null;
                } else if (cv != null) {
                    /*
                     * System.out.println(i);
                     * System.out.println(BattleGround.character[i].isDead());
                     * System.out.println(BattleGround.characterView[i]);
                     */
                    BattleGround.characterView[i].update();
                }
            }
            int HuluNum = 0;
            int YaojingNum = 0;
            for (int i = 0; i < 6; i++) {
                if (BattleGround.characterView[i] != null)
                    HuluNum = HuluNum + 1;

            }
            for (int i = 6; i < 12; i++) {
                if (BattleGround.characterView[i] != null)
                    YaojingNum = YaojingNum + 1;
            }

            BattleGround.bulletViewSetLock.lock();
            H = new HashSet<>(BattleGround.bulletViewSet);
            BattleGround.bulletViewSetLock.unlock();
            SceneView sv = new SceneView(H, BattleGround.characterView);
            if (lastSV != null) {
                lastSV.erase(root);
            }
            sv.show(root);
            lastSV = sv;
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            if (HuluNum == 0 || YaojingNum == 0) {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.titleProperty().set("消息");
                        alert.headerTextProperty().set("加载结束");
                        alert.showAndWait();
                    });
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                // System.out.println("update break");
                break;

            }

        }
    }

}

public class LoadLog extends Thread {
    private LogReader login;
    private Pane root;

    public LoadLog(LogReader login, Pane root) {
        this.login = login;
        this.root = root;
    }

    public void exec(LogEntry entry) {
        switch (entry.key) {
            case S:
                BattleGround.character[entry.index].move(Direction.Down);
                break;
            case W:
                BattleGround.character[entry.index].move(Direction.Up);
                break;
            case A:
                BattleGround.character[entry.index].move(Direction.Left);
                break;
            case D:
                BattleGround.character[entry.index].move(Direction.Right);
                break;
            case J:
                BattleGround.character[entry.index].commonAttack();
                break;
            case K:
                BattleGround.character[entry.index].specialAttack();
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
        }

        UpdateScene us = new UpdateScene(root);
        us.start();
        LogEntry entry;
        long lastTime = 0;
        entry = login.read();
        exec(entry);
        lastTime = entry.time;
        // System.out.println(entry.key+" "+entry.time);
        while ((entry = login.read()) != null) {
            // System.out.println(entry.key+" "+entry.time+" "+(entry.time-lastTime));
            try {
                Thread.sleep(entry.time - lastTime);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            lastTime = entry.time;
            exec(entry);
        }
    }
}