package tool.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import game.view.SceneView;

public class Message {
    public MessageType state;
    public int index;
    public SceneView sv;

    public Message() {
        sv = new SceneView();
    }

    public Message(MessageType state, int index, SceneView sv) {
        this.state = state;
        this.index = index;
        this.sv = sv;
    }

    public void writeObject(DataOutputStream out) throws IOException {
        out.writeInt(state.ordinal());
        out.writeInt(index);
        if (state == MessageType.DRAW) {
            sv.writeObject(out);
        }
    }

    public void readObject(DataInputStream in) throws IOException, ClassNotFoundException {
        state = MessageType.values()[in.readInt()];
        index = in.readInt();
        if (state == MessageType.DRAW) {
            sv.readObject(in);
        }
    }
}