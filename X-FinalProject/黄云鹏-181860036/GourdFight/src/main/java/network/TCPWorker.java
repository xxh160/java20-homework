package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class TCPWorker implements Runnable { // TCP工作线程类，用于交互服务器和客户端之间的数据
	
	Socket socket; // 套接字
	
	int client; // 客户端编号

	public static ArrayList<Packet> client1Pkts = new ArrayList<>(); // 玩家1的数据
	public static int client1Count = 0; // 当前带取走的玩家1的数据包指针
	public static ArrayList<Packet> client2Pkts = new ArrayList<>(); // 玩家2的数据
	public static int client2Count = 0; // 当前带取走的玩家2的数据包指针
	
	private boolean isRunnable; // 线程是否可以工作
	
	// 初始化
	public TCPWorker(Socket socket, int num) {
		this.socket = socket;
		client = num;
		isRunnable = true;
	}

	// Getter
	public boolean isRunnable() { // 获取线程是否可以工作
		return isRunnable;
	}
	
	synchronized public Packet getReceivePacket() { // 获取接收包
//		if(!receivePackets.isEmpty() && receiveCount < receivePackets.size())
//		{
//			Packet pkt = receivePackets.get(receiveCount);
//			receiveCount++;
//			return pkt;
//		}
		return null;
		
	}
	
	// Setter
	public void setRunnable(boolean isRunnable) { // 设置线程是否可以工作
		this.isRunnable = isRunnable;
	}
	
	synchronized public void setSendPacket(Packet sendPacket) { // 设置发送包
//		sendPackets.add(sendPacket);
	}
	
	synchronized private void sendPacket(DataOutputStream dos) { // 发送数据包
		if(client == 0) { // 向玩家1发送玩家2的数据包
			if(!client2Pkts.isEmpty() && client2Count < client2Pkts.size()) {
				Packet pkt = client2Pkts.get(client2Count);
				String echo = pkt.send();
				System.out.println("send to client"+client+": "+ echo);
				try {
					dos.writeBytes(echo);
					client2Count++;
				} catch (IOException e) {
					e.printStackTrace();
				} // 发送包
			}
		}
		
		else if(client == 1) { // 向玩家2发送玩家1的数据包
			if(!client1Pkts.isEmpty() && client1Count < client1Pkts.size()) {
				Packet pkt = client1Pkts.get(client1Count);
				String echo = pkt.send();
				System.out.println("send to client"+client+": "+ echo);
				try {
					dos.writeBytes(echo);
					client1Count++;
				} catch (IOException e) {
					e.printStackTrace();
				} // 发送包
			}
		}
	}
	
	synchronized private void receivePacket(BufferedReader br) { // 接收数据包
		String line;
		try {
			line = br.readLine();
			if(line != null) {
				System.out.println("received from client"+client+": " + line);
				Packet pkt = new Packet();
				pkt.receive(line);
				if(client == 0) { // 接收玩家1的数据包
					client1Pkts.add(pkt);
				}
				else if (client == 1) { // 接收玩家2的数据包
					client2Pkts.add(pkt);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} // 接收字符串
	}
	
	// 工作线程方法
	@Override
	public void run() {
		try {
			InputStream ips = socket.getInputStream(); // 输入流
			OutputStream ops = socket.getOutputStream(); // 输出流
			
			BufferedReader br = new BufferedReader(new InputStreamReader(ips)); // 输入流缓冲区封装
			DataOutputStream dos = new DataOutputStream(ops); // 输出流数据化封装
			
			while(true) {
				
				if(!isRunnable) {
					System.out.println("! isRunnable");
					break;
				}
				
//				if(sendPackets.isEmpty() && receivePackets.isEmpty()) {
//					System.out.println("waiting");
//					continue;
//				}
				
//				String line = br.readLine(); // 接收字符串
//				if(line != null) {
//					System.out.println(line);
//					Packet pkt = new Packet();
//					pkt.receive(line);
//					receivePackets.add(pkt);
//				}
				receivePacket(br);
				
//				if(!sendPackets.isEmpty() && sendCount < sendPackets.size()) {
//					Packet pkt = sendPackets.get();
//					String echo = pkt.send(); // 发送字符串
//					System.out.println(echo);
//					dos.writeBytes(echo); // 发送包
//				}
				sendPacket(dos);
			
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
