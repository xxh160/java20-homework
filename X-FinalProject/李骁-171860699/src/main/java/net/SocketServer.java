package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
public class SocketServer {// Socket服务器端
	private int port; // 端口号
 
	public SocketServer(int port) {
		this.port = port;
		start();
	}
 
	private void start() {// 启动服务器端的方法
 
		try {
			
			ServerSocket serverSocket = new ServerSocket(port);// 根据端口创建服务器
			System.out.println("服务器本机ip：" + serverSocket.getInetAddress());
			System.out.println("服务器已启动，监听端口号为：" + port);
			System.out.println("正在等待客户端连接。。。。。");
			Socket socketAccept = serverSocket.accept();// 挂起等待客户的请求
			BufferedReader in = new BufferedReader(new InputStreamReader(socketAccept.getInputStream()));// 获得（读取客户端的数据流）
			PrintWriter out = new PrintWriter(socketAccept.getOutputStream(), true); // 获得写往客户端的(数据输出流),true表示自动刷新
			out.println("服务器端连接成功。。。。");
			out.println("输入exit断开与服务器的连接");
			boolean done = false;
			while (!done) {
				String line = in.readLine();// 读取客户端每行的内容
				//System.out.println("line : "+line);
				if (line == null)
					done = true;
				else {
					System.out.println("客户端传来的内容： " + line);
					String message = infoUpperCase(line);// 变成大写再传回客户端去
					out.println("从服务器端口发送的内容 "+message); //--该处的print要加ln，否则就会无法往客户端传递消息
					if (line.trim().equals("exit")) // 退出判断
						done = true;
				}
			}
			socketAccept.close();  //关闭通信资源
		} catch (IOException e) {
			System.out.println("启动服务器端出现错误："+e.getMessage());
		}
	}
 
	public String infoUpperCase(String line) {
		return line.toUpperCase(); // 将字符串大写
	}
}