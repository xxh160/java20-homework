package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
 
public class SocketClient { // Socket客户端
	private String host; // IP地址
	private int port; // 端口号
 
	public SocketClient(String host, int port) {
		this.host = host;
		this.port = port;
		connectSocket();// 调用连接方法
	}
 
	public void connectSocket() {
		Socket socketConn; // 声明客户端的Socket连接
		try {
			if (host.equals("localhost") || host.equals("127.0.0.1")) {// 判断IP地址(域名)如果是本机localhost
				socketConn = new Socket(InetAddress.getLocalHost(), port);// 创建本地Socket连接
				//如果该方法InetAddress.getLocalHost()报错，则要在sudo vi /private/etc/hosts 中添加本机地址与你主机名的映射，类似  127.0.0.1 主机名
				//然后终端执行命令 dscacheutil -flushcache，之后主机地址便可正常解析
				
 
			} else {
				socketConn = new Socket(InetAddress.getByName(host), port);// 创建远程socket连接
			}
			
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));// 获得从键盘输入的流
			PrintWriter out = new PrintWriter(socketConn.getOutputStream(), true);// 往服务器写内容的数据流
			// 从服务器获得信息
			BufferedReader in = new BufferedReader(new InputStreamReader(socketConn.getInputStream()));// 接收服务器发送内容的输入流
			System.out.println("服务器信息：" + in.readLine());
			System.out.println("服务器信息：" + in.readLine());
			System.out.println("请输入>");
 
            boolean done = false;
            BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
 
			while (!done) {
                System.out.println("等待输入...");
                //String line = stdin.readLine();// 获得从键盘输入的每行字符
                //String line = "exit";
                String line = inFromUser.readLine();
                System.out.println("\n输入完毕");
				out.println(line);// 发送到服务器端  --该处的print要加ln，否则就会无法往服务器端传递消息
				if (line.equalsIgnoreCase("exit")) // 读到exit则结束循环
					done = true;
				String info = in.readLine(); // 从服务器读取字符串
				System.out.println("服务器信息：" + info);// 显示从服务器发送来的数据
				if (!done)
					System.out.println("请输入>");
			}
			socketConn.close(); // 关闭资源
		} catch (SecurityException e) {
			System.out.println("连接服务器出现安全问题！"+e.getMessage());
		} catch (IOException e) {
			System.out.println("连接服务器出现I/O问题！"+e.getMessage());
		}
	}
}