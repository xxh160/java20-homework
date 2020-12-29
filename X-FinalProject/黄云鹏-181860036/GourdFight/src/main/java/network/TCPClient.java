package network;

import java.net.InetAddress;
import java.net.Socket;

public class TCPClient { // TCP客户端类

	private TCPWorker worker; // 工作线程
	
	private String serverIP; // 服务器IP地址
	private String clientIP; // 客户端IP地址
	
	private int serverPort; // 服务器端口号
	private int clientPort; // 客户端端口号
	
	private boolean isServerStart; // 服务器是否启动
	
	// 初始化
	public TCPClient(String clientIP, int clientPort) {
		this.clientIP = clientIP;
		this.clientPort = clientPort;
		worker = null;
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
	
	public boolean isServerStart() { // 判断服务器是否启动
		return isServerStart;
	}
	
	public Packet getReceivePakcet() { // 获取接受包
		if(worker != null) {
			return worker.getReceivePacket();
		}
		return null;
	}
	
	// Setter
	public void setServerIP(String serverIP) { // 设置服务器IP地址
		this.serverIP = serverIP;
	}
		
	public void setServerPort(int serverPort) { // 设置服务器端口号
		this.serverPort = serverPort;
	}
	
	public void setServerStart(boolean isServerStart) { // 设置服务器是否启动
		this.isServerStart = isServerStart;
	}
	
	public void setSendPacket(Packet p) { // 设置发送包
		if(worker != null) {
			worker.setSendPacket(p);
		}
	}
	
	// 启动客户端
	public void start() {
		
		while(!isServerStart()) { // 服务器需要先启动，才能启动客户端，否则阻塞
			
		}
		
		try {
			Socket socket = new Socket(InetAddress.getByName(serverIP), serverPort);
			clientIP = socket.getLocalAddress().getHostAddress();
			worker = new TCPWorker(socket);
			new Thread(worker).start(); // 启动工作线程
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
