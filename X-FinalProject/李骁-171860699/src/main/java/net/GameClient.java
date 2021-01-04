package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;

import creature.*;
import view.MainCanvas;

public class GameClient implements Runnable { // Socket客户端
	private String host; // IP地址
	private int port; // 端口号

	Socket clientSocket; // 声明客户端的Socket连接
	PrintWriter out;
	BufferedReader in;
	boolean done = false;
	BufferedReader inFromUser;

	private ExecutorService exec = Executors.newCachedThreadPool(); // 线程池

	public GameClient(String host, int port) {
		this.host = host;
		this.port = port;
		// connect();// 调用连接方法
	}

	public void start() {
		exec.submit(this);
	}

	public void sendMessage(String msg) {
		System.out.println("客户端发送：" + msg);
		if (out != null) {
			out.println(msg);
		}
		else {
			System.out.println("客户端发送信息失败，客户端未启动：" + msg);
		}
	}

	public void executeMessage(String msg) {
		System.out.println("客户端处理信息：" + msg);
		String[] commands = msg.split(",");
		if (commands.length == 2) {
			String cmd = commands[0];
			int n = Integer.parseInt(commands[1]);
			if (cmd.startsWith("add")) {
				String className = cmd.substring(3, cmd.length());
				System.out.println("添加敌人类型：" + className);
				Creature creature = null;
				if (className.equals(Chuanshanjia.class.getSimpleName())) {
					creature = new Chuanshanjia();
				}
				else if (className.equals(Dawa.class.getSimpleName())) {
					creature = new Dawa();
				}
				else if (className.equals(Huowa.class.getSimpleName())) {
					creature = new Huowa();
				}
				else if (className.equals(Shuiwa.class.getSimpleName())) {
					creature = new Shuiwa();
				}
				else if (className.equals(Xiezijing.class.getSimpleName())) {
					creature = new Xiezijing();
				}
				else if (className.equals(Shejing.class.getSimpleName())) {
					creature = new Shejing();
				}
				else if (className.equals(Wugongjing.class.getSimpleName())) {
					creature = new Wugongjing();
				}
				else if (className.equals(Qingwajing.class.getSimpleName())) {
					creature = new Qingwajing();
				}
				else {
					creature = null;
				}
				//TODO 增加更多类型
					
				if (creature != null) {
					MainCanvas.recorder.recordOperation("enemy", "add"+className, n); //记录
					MainCanvas.runwayField.getRunways().get(n).addToEnemyCreatures(creature);
				}
				else {
					System.out.println("添加失败：" + className);
				}
			}
			else if (cmd.equals("clearRunway")) {
				MainCanvas.recorder.recordOperation("enemy", cmd, n);
				MainCanvas.runwayField.getRunways().get(n).removeAllCreatures();
			}
			else if (cmd.equals("freezeRunway")) {
				MainCanvas.recorder.recordOperation("enemy", cmd, n);
				MainCanvas.runwayField.getRunways().get(n).freezeMyCreatures();
			}
			else if (cmd.equals("costAddN")) {
				//MainCanvas.recorder.recordOperation("enemy", cmd, n);//无
				MainCanvas.cardField.cardsCostPlusN(n);
			}
			else if (cmd.equals("killHead")) {
				MainCanvas.recorder.recordOperation("enemy", cmd, n);
				MainCanvas.runwayField.getRunways().get(n).killMyHead();
			}
			else {
				System.out.println("未识别的消息");
			}
		} else {
			System.out.println("未识别的消息");
			// 不识别
		}
	}

	@Override
	public void run() {
		try {
			if (host.equals("localhost") || host.equals("127.0.0.1")) {// 判断IP地址(域名)如果是本机localhost
				clientSocket = new Socket(InetAddress.getLocalHost(), port);// 创建本地Socket连接
				// 如果该方法InetAddress.getLocalHost()报错，则要在sudo vi /private/etc/hosts
				// 中添加本机地址与你主机名的映射，类似 127.0.0.1 主机名
				// 然后终端执行命令 dscacheutil -flushcache，之后主机地址便可正常解析
			} else {
				clientSocket = new Socket(InetAddress.getByName(host), port);// 创建远程socket连接
			}

			out = new PrintWriter(clientSocket.getOutputStream(), true);// 往服务器写内容的数据流
			// 从服务器获得信息
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));// 接收服务器发送内容的输入流
			System.out.println("服务器信息：" + in.readLine());
			System.out.println("服务器信息：" + in.readLine());
			System.out.println("请输入>");
			MainCanvas.recorder.start(); //记录器启动
			inFromUser = new BufferedReader(new InputStreamReader(System.in)); // 用户输入
		} catch (SecurityException e) {
			System.out.println("连接服务器出现安全问题！" + e.getMessage());
		} catch (IOException e) {
			System.out.println("连接服务器出现I/O问题！" + e.getMessage());
		}

		while (!Thread.interrupted() && !done) {
			//这种写法会由于update要执行的东西多而卡死
			/*Platform.runLater(new Runnable() {
			 
				@Override public void run() {
					System.out.println("update");
					update(); 
				} 
			});*/
			
			System.out.println("client update");
			update();
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		}
		close();
	}

	public void update() {
		try {
			System.out.println("等待服务器数据...");
			String line = in.readLine(); // 从服务器读取字符串
			System.out.println("line : "+line);
			if (line == null) {
				done = true;
			}
			else {
				System.out.println("服务器发来信息：[" + line + "]");// 显示从服务器发送来的数据
				executeMessage(line); //由于和javafx application不在一个线程，所以不能直接操作scene里的元素，否则卡住
			}
		} catch (SecurityException e) {
			System.out.println("连接服务器出现安全问题！" + e.getMessage());
		} catch (IOException e) {
			System.out.println("连接服务器出现I/O问题！" + e.getMessage());
		}
	}

	public void close() {
		System.out.println("客户端关闭");
		done = true;
		try {
			if (clientSocket != null) {
				clientSocket.close();
			}
		} catch (Exception e) {
			System.out.println("关闭客户端出现问题" + e.getMessage());
		}
		exec.shutdownNow();
	}

	/*
	 * public void connect() {
	 * 
	 * try { if (host.equals("localhost") || host.equals("127.0.0.1")) {//
	 * 判断IP地址(域名)如果是本机localhost clientSocket = new
	 * Socket(InetAddress.getLocalHost(), port);// 创建本地Socket连接 //
	 * 如果该方法InetAddress.getLocalHost()报错，则要在sudo vi /private/etc/hosts //
	 * 中添加本机地址与你主机名的映射，类似 127.0.0.1 主机名 // 然后终端执行命令 dscacheutil
	 * -flushcache，之后主机地址便可正常解析 } else { clientSocket = new
	 * Socket(InetAddress.getByName(host), port);// 创建远程socket连接 }
	 * 
	 * out = new PrintWriter(clientSocket.getOutputStream(), true);// 往服务器写内容的数据流 //
	 * 从服务器获得信息 in = new BufferedReader(new
	 * InputStreamReader(clientSocket.getInputStream()));// 接收服务器发送内容的输入流
	 * System.out.println("服务器信息：" + in.readLine()); System.out.println("服务器信息：" +
	 * in.readLine()); System.out.println("请输入>"); inFromUser = new
	 * BufferedReader(new InputStreamReader(System.in)); // 用户输入 while (!done) {
	 * System.out.println("等待输入..."); // String line = stdin.readLine();//
	 * 获得从键盘输入的每行字符 // String line = "exit"; String line = inFromUser.readLine();
	 * System.out.println("\n输入完毕"); out.println(line);// 发送到服务器端
	 * --该处的print要加ln，否则就会无法往服务器端传递消息 if (line.equalsIgnoreCase("exit")) //
	 * 读到exit则结束循环 done = true; String info = in.readLine(); // 从服务器读取字符串
	 * System.out.println("服务器信息：" + info);// 显示从服务器发送来的数据 if (!done)
	 * System.out.println("请输入>"); } clientSocket.close(); // 关闭资源 } catch
	 * (SecurityException e) { System.out.println("连接服务器出现安全问题！" + e.getMessage());
	 * } catch (IOException e) { System.out.println("连接服务器出现I/O问题！" +
	 * e.getMessage()); } }
	 */
}