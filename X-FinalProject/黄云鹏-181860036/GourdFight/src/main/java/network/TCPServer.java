package network;

import java.io.IOException;
import java.net.*;

import framework.Constants;

/**
 * 
 * @author 黄云鹏
 * @version	2021.1.1
 * @inherit 
 * @functions 用于建立服务器端
 * @properties serverSocket
 * @methods  
 * 		start: 开启服务器
 * 		shutDown: 关闭服务器
 */

public class TCPServer { // TCP服务器类
	
	ServerSocket serverSocket; // 服务器总套接字
	
	private TCPWorker worker1; // 与玩家1交互的工作线程
	private TCPWorker worker2; // 与玩家2交互的工作线程
	
	private int clientNum = 0; // 客户端数量 
	
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
		
		this.worker1 = null;
		this.worker2 = null;

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
	
	public Packet getReceivePakcet() { // 获取接收包
//		if(worker != null) {
//			return worker.getReceivePacket();
//		}
		
		return null;
	}
	
	// Setter
	public void setClientIP(String clientIP) { // 设置客户端IP地址
		this.clientIP = clientIP;
	}
	
	public void setClientPort(int clientPort) { // 设置客户端端口号
		this.clientPort = clientPort;
	}
	
	public void setSendPacket(Packet p) { // 设置发送包
//		if(worker != null) {
//			worker.setSendPacket(p);
//		}
	}
	
	// 启动服务器
	public void start() {
		try {
			serverSocket = new ServerSocket(Constants.PORT);
			
			while(clientNum < 2) {
				Socket socket = serverSocket.accept(); // 等待客户端连接上(阻塞状态)
				System.out.println("Connected to the Client"+clientNum);
				
				isAccept = true; // 客户端已经连接上
				
				if(clientNum == 0)
				{
					worker1 = new TCPWorker(socket, 0);
					new Thread(worker1).start(); // 启动工作线程
				}
				else if(clientNum == 1) {
					worker2 = new TCPWorker(socket, 1);
					new Thread(worker2).start(); // 启动工作线程
				}
				clientNum++;
			}
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
