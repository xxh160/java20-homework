package client;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;

import game.view.SceneView;
import tool.connection.Message;

public class ClientSendMessage extends Thread {
    private OutputStream out;
    private Message msg;
    private ReentrantLock outLock;

    public ClientSendMessage(OutputStream out, Message msg, ReentrantLock outLock) {
        this.out = out;
        this.msg = msg;
        this.outLock = outLock;
    }

    @Override
    public void run() {
        outLock.lock();
        try {
            DataOutputStream obj = new DataOutputStream(out);
            SceneView sv = new SceneView();
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            DataOutputStream data = new DataOutputStream(byteStream);
            msg.writeObject(data);
            byte[] buf = byteStream.toByteArray();

            // System.out.println();
            obj.writeInt(buf.length);
            obj.write(buf);
            obj.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        outLock.unlock();
    }
}