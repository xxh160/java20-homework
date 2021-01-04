package cn.edu.nju.web;

import cn.edu.nju.record.HLWRecord;
import javafx.concurrent.Task;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class Connection {
    private String hostName;
    private PrintWriter out;
    private BufferedReader in;
    private Task createTask;
    private Task findTask;
    private int port = 8081;
    private  ServerSocket serverSocket;
    private  Socket connectSocket;
    private boolean recordMode;

    public Connection(){
        try {
            recordMode = false;
            hostName = getLocalHostLANAddress().getHostAddress();
            File file = new File("webConfig.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedReader config = new BufferedReader(new InputStreamReader(new FileInputStream("webConfig.txt")));
            String webConfig = config.readLine();
            if(webConfig!=null){
                hostName = webConfig;
            }
            createTask = new Task() {
                @Override
                protected Object call() throws Exception {
                    createConnection();
                    return null;
                }
            };
            findTask = new Task() {
                @Override
                protected Boolean call() throws Exception {
                    return findConnection();
                };
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createConnection(){
        try {
            serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            connectSocket = clientSocket;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while (true){
                inputLine = in.readLine();
                System.out.println(inputLine);
                if(inputLine.equals("UHLW?")){
                    break;
                }
            };
            out.println("UHLW!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean findConnection(){
        String hostRange = hostName.substring(0,hostName.lastIndexOf('.')+1);
        //int hostNum =  Integer.parseInt(hostName.substring(hostName.lastIndexOf('.')+1));
        for(int i=1;i<=255;i++){
            hostName = hostRange+i;
            try{
                System.out.println(hostName);
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(hostName, port),50);
                connectSocket = socket;
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("UHLW?");
                if(in.readLine().equals("UHLW!")){
                    return true;
                }
            }
            catch (IOException ignored){ }
        }
        System.out.println("ERROR:局域网中未发现任何房间！");
        return false;
    }
    //此处找IP的代码参考了https://blog.csdn.net/u011809209/article/details/77236602
    private InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    public Task getCreateTask() {
        return createTask;
    }

    public Task getFindTask() {
        return findTask;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public String getMessage() throws Exception{
        String message = in.readLine();
        System.out.println("收到 "+message);
        if(recordMode){
            HLWRecord.write(message);
        }
        return message;
    }
    public void sendMessage(String message) throws Exception{
        System.out.println("发送："+message);
        out.println(message);
        if(recordMode){
            HLWRecord.write(message);
        }
    }
    public void sendMoveMessage(int creatureIndex,int targetX,int targetY) throws Exception {
        String message = "move";
        message += " " + creatureIndex;
        message += " " + targetX;
        message += " " + targetY;
        sendMessage(message);
    }
    public void sendSkillMessage(int creatureIndex,int skillIndex,int targetX,int targetY) throws Exception{
        String message = "skill";
        message += " " + creatureIndex;
        message += " " + skillIndex;
        message += " " + targetX;
        message += " " + targetY;
        sendMessage(message);
    }
    public void sendOverMessage ()throws Exception{
        sendMessage("game over");
    }

    public Boolean isConnect(){
        return connectSocket.isConnected();
    }
    public void close(){
        if(connectSocket!=null){
            try {
                connectSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(serverSocket!=null){
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void setRecordMode(boolean mode){
        recordMode = mode;
    }
    public boolean getRecordMode(){
        return recordMode;
    }

}
