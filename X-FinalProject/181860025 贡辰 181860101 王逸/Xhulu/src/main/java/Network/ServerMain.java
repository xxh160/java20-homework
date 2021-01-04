package Network;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain extends Thread {
    private static List<Socket> socketList = new ArrayList<>();
    private static InetAddress[] userIP = new InetAddress[2];
    private static String[] userID = new String[2];
    private static String[] userMsgToSend = new String[2];
    private static String serverMsgToSend = "";

    public ServerMain() {
        ExecutorService myES;
        int port = 2333;
        ServerSocket server;
        Socket client;
        try {
            server = new ServerSocket(port);
            myES = Executors.newCachedThreadPool();
            refreshLog("服务器开始运行，正在监听端口：" + port);
            int p=0;
            while(true) {
                client = server.accept();
                System.out.println("有新客户端连入服务器:"+client.getInetAddress());
                socketList.add(client);
                myES.execute(new Service(client,p));
                p++;
                p = p%2;
                if(socketList.size() >= 2) start_game(socketList.get(0),socketList.get(1));
            }
        } catch (Exception e) {
            refreshLog("服务器启动失败");
            e.printStackTrace();
        }
    }
    private static void start_game(Socket hulu,Socket monster) throws IOException {
        BufferedWriter bw_monster = new BufferedWriter(new OutputStreamWriter(monster.getOutputStream()));
        BufferedWriter bw_hulu = new BufferedWriter(new OutputStreamWriter(hulu.getOutputStream()));
        //BufferedReader br_monster = new BufferedReader(new InputStreamReader(monster.getInputStream()));
        //BufferedReader br_hulu = new BufferedReader(new InputStreamReader(hulu.getInputStream()));
        bw_monster.write("monster\n");
        bw_monster.flush();
        bw_hulu.write("hulu\n");
        bw_hulu.flush();
    }
    public static void main(String[] args) {
        new ServerMain();// 匿名构造方法，启动服务主体
    }
    private static void refreshLog(String newStringLine) {
        System.out.println("---" + getCurrentTimeString() + "--- " + newStringLine);
        /*try {
            File file = new File("对战日志:"+".txt");
            FileWriter out = new FileWriter(file.getName(), true);
            if (!file.exists()) {
                file.createNewFile();
            }
            else{
                out.write(getCurrentTimeString()+"\n");
            }
            out.write(newStringLine+"\n");
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }*/
    }

    // 获取当前时间
    private static String getCurrentTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }


    //用户是否主动断开连接
    private static boolean getUserConnectionState(String newJSONString) {
        try {
            JSONObject get_root = new JSONObject(newJSONString);
            return get_root.getBoolean("userConnectionState");
        } catch (JSONException e) {
            return false;
        }
    }

    // 解析并显示Json数据
    private static String getJSONContent(String newJSONString) {
        try {
            JSONObject get_root = new JSONObject(newJSONString);
            return get_root.getString("msgContent");
        } catch (JSONException e) {
            e.printStackTrace();
            return "处理消息出错";
        }
    }

    private static String setJSONContent(String msgTime, String senderName, String newMsgToJSON) {
        try {
            JSONObject set_root = new JSONObject();
            set_root.put("msgTime", msgTime);
            set_root.put("msgSender", senderName);
            set_root.put("msgContent", newMsgToJSON);
            return set_root.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "处理消息出错";
        }
    }

    // 为连接上服务端的客户端发送信息
    private void sendUserMsg(int id) {
        int id_op;
        if(id == 0)id_op=1;
        else id_op = 0;
        refreshLog(userID[id] + "：" + userMsgToSend[id]);
        Socket currentSocket = socketList.get(id_op);
        try {
            BufferedWriter pout = new BufferedWriter(new OutputStreamWriter(socketList.get(id_op).getOutputStream()));
            pout.write(userMsgToSend[id]+"\n");
            pout.flush();
        } catch (IOException e) {
            e.printStackTrace();
            refreshLog("服务器：消息发送失败，发送对象" + userID[id] + "位于" + userIP[id]);
        }
    }

    private void sendServerMsg() {
        refreshLog("服务器：" + serverMsgToSend);
        for (int i=0;i<socketList.size();++i) {
            try {
                BufferedWriter pout = new BufferedWriter(new OutputStreamWriter(socketList.get(i).getOutputStream()));
                pout.write(serverMsgToSend+"\n");
                pout.flush();
            } catch (IOException e) {
                e.printStackTrace();
                refreshLog("服务器：消息发送失败，发送对象" + userID[i] + "位于" + userIP[i]);
            }
        }
    }

    class Service implements Runnable {// 实现Runnable接口，用于实现多线程功能
        private Socket socket;// Service类私有socket对象，用于服务器存储用户的socket// 输入流
        //private Msg my_Msg;
        private boolean ifNewMsg = false;
        private int id_op;
        private int id;
        private BufferedReader in;// 输入流

        public int getId() {
            return id;
        }

        Service(Socket socket, int _id_op) throws IOException {// 构造方法
            this.socket = socket;
            id = _id_op;
            if (id == 0) _id_op = 1;
            else _id_op = 0;

        }

        @Override
        public void run() {// 线程运行方法
            try {
                // 等号左边是本类Service类私有对象，等号右边是ServerMain类的构造方法ServerMain()传来的参数
                // 输入流
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                // IP地址
                userIP[id] = this.socket.getInetAddress();
                // 用户名
                userID[id] = String.valueOf(in.readLine());// 用户第一次的输入流是用户名
                // 服务器准备发出消息，新用户连接
                serverMsgToSend = userID[id] + "加入对战平台，当前" + socketList.size() + "人在线";
                //sendServerMsg();// 发送消息
                System.out.println("serverMsgToSend");

                String userMsgReceived;
                while (true) {
                    try {
                        //socket.setSoTimeout(0);//等待事件无限长

                        userMsgReceived = in.readLine();// 不断等待用户的输入
                        //System.out.println("111111111111111111");
                    /*if(ifNewMsg==false)
                        continue;
                    userMsgReceived = my_Msg.toString();// 不断等待用户的输入

                    ifNewMsg = false;
                     */
                        System.out.println(userMsgReceived);
                        //if (getUserConnectionState(userMsgReceived)) {
                        userMsgToSend[id] = userMsgReceived;// 消息临时存储
                        sendUserMsg(id);
                       /* } else {
                            try {
                                in.close();
                                socket.close();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            socketList.remove(socket);
                            serverMsgToSend = userID[id] + "退出战斗平台，当前" + socketList.size() + "人在线";
                            sendServerMsg();
                            break;
                        }*/
                    } catch (Exception e) {
                        try {
                            socket.close();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        socketList.remove(socket);
                        serverMsgToSend = userID[id] + "退出平台，当前" + socketList.size() + "人在线";
                        sendServerMsg();
                        e.printStackTrace();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

