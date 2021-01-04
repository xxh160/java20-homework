package client;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;

import game.BattleGround;
import game.view.SceneView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import tool.Camp;
import tool.connection.Message;
import tool.connection.MessageType;
public class ClientInputHandler extends Thread{
    private InputStream in;
    private Pane root;
    private OutputStream out;
    private ReentrantLock outLock;
    public ClientInputHandler(Pane root,InputStream  in,OutputStream out,ReentrantLock outLock){
        this.root=root;
        this.in=in;
        this.out=out;
        this.outLock=outLock;
    }
    @Override
    public void run() {
        try{        
            DataInputStream obj=new DataInputStream(in);  
            SceneView sv=null;
            while (true) {
                if(obj.available()<4){
                    continue;
                }
                int num=obj.readInt();
                //System.out.println("len "+num);
                while(obj.available()<num){
                    continue;
                }
                Message msg=new Message();
                msg.readObject(obj);

                //TODO:deal msg
              
                //System.out.println("receive "+msg.state);
                switch (msg.state) {
                    case YAOJING:
                        BattleGround.setCamp(Camp.Yaojing);
                        BattleGround.setFlag();
                        break;
                    case HULU:
                        BattleGround.setCamp(Camp.Hulu); 
                        BattleGround.setFlag();
                        break;
                    case START:
                        BattleGround.setCharacterView();
                       
                        break;
                    case DRAW:
                        if(sv!=null){
                            sv.erase(root);
                        }
                        sv=msg.sv;
                        sv.show(root);
                        break;
                    case END:
                        //TODO: END
                        ClientSendMessage csm=new ClientSendMessage(out,new Message(MessageType.END,0,null),outLock);
                        csm.start();
                        //System.out.println("receive end");
                        Platform.runLater(()->{
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.titleProperty().set("消息");
                            alert.headerTextProperty().set("游戏结束");
                            alert.showAndWait();                        
                        });

                        return;
                    default:
                        break;
                }

            }
            
        }catch(Exception  e){
            e.printStackTrace();
        }
    }
}