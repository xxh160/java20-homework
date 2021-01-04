package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import framework.Constants;
import framework.Framework;
import view.NetView;
import view.PlayView;
import view.Role1View;

/**
 * 
 * @author 黄云鹏
 * @version	2021.1.1
 * @inherit 
 * @functions 用于建立客户端
 * @properties socket
 * @methods  
 * 		start: 开启客户端
 */

public class TCPClient { // TCP客户端类
	
	private Socket socket;
	
	BufferedReader br;
	DataOutputStream dos;
	
	
	private String serverIP; // 服务器IP地址
	private String clientIP; // 客户端IP地址
	
	private int serverPort; // 服务器端口号
	private int clientPort; // 客户端端口号
	
	private boolean isServerStart = false; // 服务器是否启动
	private boolean isConnected = false; // 是否连上服务器
	
	// 初始化
	public TCPClient(String clientIP, int clientPort) {
		this.clientIP = clientIP;
		this.clientPort = clientPort;
//		worker = null;
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
	
	public boolean isConnected() { // 判断是否连接上服务器
		return isConnected;
	}
	
	public Packet getReceivePakcet() { // 获取接受包
//		if(worker != null) {
//			return worker.getReceivePacket();
//		}
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
//		if(worker != null) {
//			worker.setSendPacket(p);
//		}
	}
	
	synchronized public void sendPkt() { // 向服务器发送自己的数据包
		try {
			PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
			if(!playView.sendPktQueue.isEmpty() && playView.sendIdx < playView.sendPktQueue.size()) {
				
				Packet pkt = playView.sendPktQueue.get(playView.sendPktQueueIdx);
				String echo = pkt.send();
				dos.writeBytes(echo);
				playView.sendIdx++;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	synchronized public void receivePkt() { // 从服务器端接收对手的数据包
		try {
			PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
			String line = br.readLine();
			Packet pkt = new Packet();
			pkt.receive(line);
			playView.receivePktQueue.add(pkt);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 启动客户端
	public void start() {
		
		try {
			Socket socket = new Socket(InetAddress.getByName(serverIP), Constants.PORT);
			this.socket = socket;
			System.out.println("Connected To the Server");
			isConnected = true; // 成功连接上
			
			InputStream ips = socket.getInputStream(); // 输入流
			OutputStream ops = socket.getOutputStream(); // 输出流
			
			BufferedReader br = new BufferedReader(new InputStreamReader(ips)); // 输入流缓冲区封装
			this.br = br;
			DataOutputStream dos = new DataOutputStream(ops); // 输出流数据化封装
			this.dos = dos;
			
//			// 与服务器进行数据包的交互
//			ClientWorker worker = new ClientWorker(br, dos);
//			new Thread(worker).start();
			
			// 连接成功，允许用户进入角色选择页面
			Role1View roleView = (Role1View)Framework.app.getView(Constants.ROLE1_VIEW_KEY);
			roleView.setMode(true);
			NetView netView = (NetView)Framework.app.getView(Constants.NET_VIEW_KEY);
			netView.nextBtn.setDisable(false);
			
			Framework.app.gotoView(Constants.ROLE1_VIEW_KEY);
			
//			while(true) {
//				if(!isConnected)
//					break;
//				receivePkt(br);
//				sendPkt(dos);
//			}
//			dos.close();
//			br.close();
//			socket.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
