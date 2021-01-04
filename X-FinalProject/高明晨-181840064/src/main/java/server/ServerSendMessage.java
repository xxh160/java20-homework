package server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;

import game.view.SceneView;
import tool.connection.Message;

public class ServerSendMessage extends Thread {
    private OutputStream out;
    private Message msg;
    private ReentrantLock outLock;

    public ServerSendMessage(OutputStream out, Message msg, ReentrantLock outlLock) {
        this.out = out;
        this.msg = msg;
        this.outLock = outlLock;
    }

    @Override
    public void run() {
        outLock.lock();
        try {
            DataOutputStream obj = new DataOutputStream(out);
            SceneView sv = new SceneView();
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            DataOutputStream data = new DataOutputStream(byteStream);
            // System.out.println("send msg "+msg.state);
            msg.writeObject(data);
            byte[] buf = byteStream.toByteArray();

            // System.out.println("msg len "+buf.length);
            obj.writeInt(buf.length);
            obj.write(buf);
            obj.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        outLock.unlock();
    }
}