package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;

import creature.Creature;
import view.MainCanvas;

public class GameServer implements Runnable {

	private int port = 28080; // 端口号

	ServerSocket serverSocket = null; // 服务器套接字
	Socket socketAccept = null; // 读写套接字
	BufferedReader in; // 读客户端发来的数据
	PrintWriter out; // 写发去客户端的数据

	boolean done = false;

	private ExecutorService exec = Executors.newCachedThreadPool(); // 线程池

	public GameServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port); // 根据端口创建服务器
			System.out.println("服务器本机ip：" + serverSocket.getInetAddress());
			System.out.println("服务器已启动，监听端口号为：" + port);
			System.out.println("正在等待客户端连接。。。。。");
			socketAccept = serverSocket.accept();// 挂起等待客户的请求
			in = new BufferedReader(new InputStreamReader(socketAccept.getInputStream()));// 获得（读取客户端的数据流）
			out = new PrintWriter(socketAccept.getOutputStream(), true); // 获得写往客户端的(数据输出流),true表示自动刷新
			out.println("服务器端连接成功。。。。");
			out.println("输入exit断开与服务器的连接");

			
		} catch (IOException e) {
			System.out.println("启动服务器端出现错误：" + e.getMessage());
		}
		
		while (!Thread.interrupted() && !done) {
			/*Platform.runLater(new Runnable() {
				@Override
				public void run() {
					System.out.println("update");
					update();
				}
			});*/
			System.out.println("update");
			update();
			try {
                Thread.sleep(20);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
		}
		close();
	}

	public void start() {// 启动服务器端的方法
		exec.submit(this);
	}

	public String infoUpperCase(String line) {
		return line.toUpperCase(); // 将字符串大写
	}

	public void sendMessage(String msg) {
		System.out.println("服务器发送：" + msg);
		out.println(msg);
	}

	public void executeMessage(String msg) {
		System.out.println("服务器处理信息" + msg);
		if (msg == "add1") {
			// TODO 改掉
			MainCanvas.runwayField.getRunways().get(0).addToEnemyCreatures(new Creature());
		} else {
			// 不识别
		}
	}

	public void update() {
		try {
			String line = in.readLine();// 读取客户端每行的内容
			// System.out.println("line : "+line);
			if (line == null)
				done = true;
			//else {
				System.out.println("客户端传来的内容： " + line);
				executeMessage(line);
				//String message = infoUpperCase(line);// 变成大写再传回客户端去
				//out.println("从服务器端口发送的内容 " + message); // --该处的print要加ln，否则就会无法往客户端传递消息
				//if (line.trim().equals("exit")) // 退出判断
				//	done = true;
			//}
		} catch (IOException e) {
			System.out.println("服务器接收消息错误：" + e.getMessage());
		}
	}

	public void close() {
		// 关闭套接字
		// 关闭线程
		System.out.println("服务器关闭");
		done = true;
		try {
			if (socketAccept != null) {
				socketAccept.close();
			}
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			System.out.println("关闭服务器端出现错误：" + e.getMessage());
		}
		exec.shutdownNow();
	}
}