package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TCPWorker implements Runnable { // TCP工作线程类，用于交互服务器和客户端之间的数据
	
	Socket socket; // 套接字
	
	private Packet sendPacket; // 发送包
	private Packet receivePacket; // 接收包
	
	private boolean isRunnable; // 线程是否可以工作
	
	// 初始化
	public TCPWorker(Socket socket) {
		this.socket = socket;
		isRunnable = true;
		
		sendPacket = null;
		receivePacket = null;
	}

	// Getter
	public boolean isRunnable() { // 获取线程是否可以工作
		return isRunnable;
	}
	
	public Packet getReceivePacket() { // 获取接收包
		return receivePacket;
	}
	
	// Setter
	public void setRunnable(boolean isRunnable) { // 设置线程是否可以工作
		this.isRunnable = isRunnable;
	}
	
	public void setSendPacket(Packet sendPacket) { // 设置发送包
		this.sendPacket = sendPacket;
		this.sendPacket.setSendReady(true); // 设置新包后即可以允许发送
	}
	
	// 工作线程方法
	@Override
	public void run() {
		try {
			InputStream ips = socket.getInputStream(); // 输入流
			OutputStream ops = socket.getOutputStream(); // 输出流
			
			BufferedReader br = new BufferedReader(new InputStreamReader(ips)); // 输入流缓冲区封装
			DataOutputStream dos = new DataOutputStream(ops); // 输出流数据化封装
			
			while(isRunnable()) {
				String line = br.readLine(); // 接收字符串
				if(line != null) {
					if(receivePacket == null) {
						receivePacket = new Packet();
					}
					receivePacket.receive(line); // 接受包
				}
				
				if(sendPacket.isSendReady()) {
					String echo = sendPacket.send(); // 发送字符串
					dos.writeBytes(echo); // 发送包
				}
			}
			
			br.close();
			dos.close();
			socket.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
