package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import javafx.scene.layout.Pane;
import tool.connection.Message;

public class Client {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private ReentrantLock outLock;
    private Pane root;

    public Client(Pane root, String ipv4) {
        outLock = new ReentrantLock();
        this.root = root;
        try {
            socket = new Socket(ipv4, 10086);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            ClientInputHandler inputHandler = new ClientInputHandler(root, in, out, outLock);
            inputHandler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(Message msg) {
        ClientSendMessage sendMessage = new ClientSendMessage(out, msg, outLock);
        sendMessage.start();
    }
}
