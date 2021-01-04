package Network;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class client implements Runnable{
    //private String host = "192.168.43.242";
    private String host = "localhost";
    private int port = 2333;
    private Socket s;
    private boolean flag;
    private boolean ifgaming = false;
    private boolean connect=true;
    private String id;
    private LinkedList<String> MsgReceived = new LinkedList<>();
    private LinkedList<String> MsgToSend = new LinkedList<>();
    private BufferedWriter bw;
    private BufferedReader br;
    private long time_start;
    FileWriter file;
    public client(String _id) throws IOException {
        id = _id;
        time_start = System.currentTimeMillis();
        file = new FileWriter("对战日志.txt");
        try {
            s = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            connect=false;
            e.printStackTrace();
        }

    }
    private void refreshLog(String newStringLine) {
        System.out.println("---" + getCurrentTimeString() + "--- " + newStringLine);

        try {

            file.write(getTime()+" "+newStringLine+"\r\n");
            file.flush();
        }catch (IOException e){
            connect=false;
            e.printStackTrace();
        }
    }
    private static String getCurrentTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    private long getTime(){
        long timeNew = System.currentTimeMillis();
        return (timeNew - time_start)/100;
    }
    public String getSide()
    {
        if (flag) return "Justice";
        return "Evil";
    }

    public void sendMsg(String str){
        MsgReceived.addLast(str);
    }
    public String getMsg() {
        //System.out.println("11111111111111");
        String res = MsgToSend.getFirst();
        MsgToSend.removeFirst();
        return res;
    }
    public boolean ifNewMsg(){
        if(MsgToSend.size()!=0)return true;
        else return false;
    }
    public boolean isConnect() {
        return connect;
    }
    @Override
    public void run() {
        try {
            InputStream is;
            OutputStream os;
            is = s.getInputStream();
            os = s.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(id + "\n");//发送用户名
            bw.flush();
            while (true) {
                String msg = br.readLine();
                if (msg.charAt(0) == '{') System.out.println(msg);
                else if (msg.equals("hulu")) {
                    flag = true;
                    break;
                } else if (msg.equals("monster")) {
                    flag = false;
                    break;
                } else System.out.println("其他信息：" + msg);
            }
            System.out.print("我的阵营为：");
            if (flag) System.out.println("葫芦娃");
            else System.out.println("妖精");

        /*try {
            s.setSoTimeout(0);
            if(br.readLine() == "start")
                ifgaming = true;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

            ifgaming = true;
            int i = 0;
            while (ifgaming) {
                //System.out.println("");

                try {
                    if(MsgReceived.size()!=0){
                        String tmp = (String) MsgReceived.getFirst();
                        MsgReceived.removeFirst();
                        bw.write( tmp + "\n");
                        bw.flush();
                        if(tmp.equals("endgame")){
                            MsgToSend.addLast("lose");
                            endGame();
                        }
                        else {
                            refreshLog(tmp);
                        }
                    }
                    else{
                        bw.write("nothing\n");
                        bw.flush();
                    }
                    if(ifgaming) {
                        String tmp = br.readLine();
                        if (tmp.equals("nothing")) {
                        } else {
                            if (tmp.equals("endgame")) {
                                MsgToSend.addLast("win");
                                endGame();
                                break;
                            } else {
                                MsgToSend.addLast(tmp);
                                refreshLog(tmp);
                            }

                            System.out.println(MsgToSend);
                        }
                    }
                    // System.out.println(MsgReceived);
                    // System.out.println(MsgToSend);

                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void endGame(){
        ifgaming = false;
    }

}
