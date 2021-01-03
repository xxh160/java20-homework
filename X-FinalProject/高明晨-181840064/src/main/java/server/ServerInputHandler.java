package server;

import java.io.DataInputStream;
import java.io.InputStream;

import game.BattleGround;
import javafx.scene.input.KeyCode;
import tool.Direction;
import tool.connection.Message;
import tool.log.LogWriter;

public class ServerInputHandler extends Thread {
    private InputStream in;
    private LogWriter logout;

    public ServerInputHandler(InputStream in, LogWriter logout) {
        this.in = in;
        this.logout = logout;
    }

    @Override
    public void run() {

        try {
            DataInputStream obj = new DataInputStream(in);
            while (true) {
                if (obj.available() < 4) {
                    continue;
                }
                int num = obj.readInt();
                // System.out.println("len "+num);
                while (obj.available() < num) {
                    continue;
                }
                Message msg = new Message();
                msg.readObject(obj);
                // System.out.println(msg.state);
                // TODO:deal msg
                switch (msg.state) {
                    case MOVEDOWN:
                        if (BattleGround.character[msg.index].move(Direction.Down))
                            logout.write(msg.index, KeyCode.S);
                        break;
                    case MOVEUP:
                        if (BattleGround.character[msg.index].move(Direction.Up))
                            logout.write(msg.index, KeyCode.W);
                        break;
                    case MOVELEFT:
                        if (BattleGround.character[msg.index].move(Direction.Left))
                            logout.write(msg.index, KeyCode.A);
                        break;
                    case MOVERIGHT:
                        if (BattleGround.character[msg.index].move(Direction.Right))
                            ;
                        logout.write(msg.index, KeyCode.D);
                        break;
                    case ATTACT:
                        BattleGround.character[msg.index].commonAttack();
                        logout.write(msg.index, KeyCode.J);
                        break;
                    case SKILL:
                        BattleGround.character[msg.index].specialAttack();
                        logout.write(msg.index, KeyCode.K);
                        break;
                    case END:
                        // TODO END
                        // System.out.println("receive end");
                        logout.close();
                        return;
                    default:
                        break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}