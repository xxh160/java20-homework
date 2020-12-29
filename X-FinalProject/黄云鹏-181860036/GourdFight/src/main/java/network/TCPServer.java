package network;

import java.io.IOException;
import java.net.*;

public class TCPServer { // TCP服务器类
	
	ServerSocket serverSocket; // 服务器总套接字
	private TCPWorker worker; // 工作线程
	
	private String serverIP; // 服务器IP地址
	private String clientIP; // 客户端IP地址
	
	private int serverPort; // 服务器端口号
	private int clientPort; // 客户端端口号
	
	private boolean isAccept; // 客户端是否连接上了服务器
	
	// 初始化
	public TCPServer(String serverIP, int serverPort) {
		
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.isAccept = false;
	}
	
	// Getter
	public String getServerIP() { // 获取服务器IP地址
		return serverIP;
	}
	
	public int getServerPort() { // 获取服务器端口号
		return serverPort;
	}
	
	public String getClientIP() { // 获取客户端IP地址
		return clientIP;
	}
	
	public int getClientPort() { // 获取客户端端口号
		return clientPort;
	}
	
	public boolean isAccept() { // 判断客户端是否连上服务器
		return isAccept;
	}
	
	public Packet getReceivePakcet() { // 获取接受包
		return worker.getReceivePacket();
	}
	
	// Setter
	public void setClientIP(String clientIP) { // 设置客户端IP地址
		this.clientIP = clientIP;
	}
	
	public void setClientPort(int clientPort) { // 设置客户端端口号
		this.clientPort = clientPort;
	}
	
	public void setSendPacket(Packet p) { // 设置发送包
		worker.setSendPacket(p);
	}
	
	// 启动服务器
	public void start() {
		try {
			serverSocket = new ServerSocket(serverPort);
			serverIP = serverSocket.getInetAddress().getHostAddress(); // 自动设置本地IP
			
			Socket socket = serverSocket.accept(); // 等待客户端连接上(阻塞状态)
			
			isAccept = true; // 客户端已经连接上
			
			worker = new TCPWorker(socket);
			
			new Thread(worker).start(); // 启动工作线程
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 关闭服务器
	public void shutDown() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
