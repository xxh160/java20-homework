package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import game.BattleGround;
import javafx.scene.layout.Pane;
import tool.Camp;
import tool.log.LogWriter;

public class Server {
    private ReentrantLock startLock;
    private Pane root;
    private LogWriter logout;

    public Server(Pane root) {
        this.root = root;
        BattleGround.setPane(root);
        BattleGround.setCamp(Camp.Hulu);
        BattleGround.setFlag();
        startLock = new ReentrantLock();
        Random rand = new Random();
        int x = rand.nextInt(2);
        Camp camp[] = new Camp[2];
        if (x == 0) {
            camp[0] = Camp.Yaojing;
            camp[1] = Camp.Hulu;
        } else {
            camp[0] = Camp.Yaojing;
            camp[1] = Camp.Hulu;
        }
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String fileName = df.format(day) + ".log";
        logout = new LogWriter(fileName);
        int portNumber = 10086;
        startLock.lock();
        try (ServerSocket server = new ServerSocket(10086)) {
            System.out.println("等待连接...");

            int count = 0;
            while (true) {
                Socket socket = server.accept();

                count = count + 1;
                System.out.println("第" + count + "个客户端连接成功！！");

                ServerHandler ct = new ServerHandler(socket, logout, camp[count - 1], startLock);
                ct.start();
                if (count == 2) {
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            startLock.unlock();
        }

    }
    /*
     * public static void main(String[] args){ new Server(); }
     */
}
