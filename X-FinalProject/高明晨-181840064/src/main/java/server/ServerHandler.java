package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

import game.BattleGround;
import game.view.BulletView;
import game.view.CharacterView;
import game.view.SceneView;
import javafx.application.Platform;
import tool.Camp;
import tool.connection.Message;
import tool.connection.MessageType;
import tool.log.LogWriter;

public class ServerHandler extends Thread {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private Camp camp;
    private LogWriter logout;
    private ReentrantLock outLock;
    private ReentrantLock startLock;

    public ServerHandler(Socket socket, LogWriter logout, Camp camp, ReentrantLock startLock) {
        super();
        this.socket = socket;
        this.camp = camp;
        this.outLock = new ReentrantLock();
        this.startLock = startLock;
        this.logout = logout;
        try {
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message msg) {
        ServerSendMessage sendMessage = new ServerSendMessage(out, msg, outLock);
        sendMessage.start();
    }

    @Override
    public void run() {

        ServerInputHandler inputHandler = new ServerInputHandler(in, logout);
        inputHandler.start();
        if (camp == Camp.Yaojing) {
            sendMessage(new Message(MessageType.YAOJING, 0, null));
        } else {
            sendMessage(new Message(MessageType.HULU, 0, null));
        }
        sendMessage(new Message(MessageType.WAIT, 0, null));

        startLock.lock();
        startLock.unlock();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendMessage(new Message(MessageType.START, 0, null));

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

            sendMessage(new Message(MessageType.DRAW, 0, new SceneView(H, BattleGround.characterView)));
            if (HuluNum == 0 || YaojingNum == 0) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

                sendMessage(new Message(MessageType.END, 0, new SceneView(H, BattleGround.characterView)));
                // TODO END
                break;
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}