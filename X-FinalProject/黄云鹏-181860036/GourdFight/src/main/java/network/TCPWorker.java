package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TCPWorker implements Runnable { // TCP工作线程类，用于交互服务器和客户端之间的数据
	
	Socket socket; // 套接字
	
	private boolean isRunnable; // 线程是否可以工作
	
	// 初始化
	public TCPWorker(Socket socket) {
		this.socket = socket;
		isRunnable = true;
	}

	// Getter
	public boolean isRunnable() { // 获取线程是否可以工作
		return isRunnable;
	}
	
	// Setter
	public void setRunnable(boolean isRunnable) { // 设置线程是否可以工作
		this.isRunnable = isRunnable;
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
				String line; // 客户端输入行
				while((line = br.readLine()) != null) {
					String echo = line + " echo" + System.lineSeparator();
					dos.writeBytes(echo);
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
