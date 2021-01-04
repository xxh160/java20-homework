package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import record.Recorder;
import creature.*;
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
			System.out.println("客户端已连上");
			MainCanvas.recorder.start(); //启动记录器

			
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
		if (out != null) {
			out.println(msg);
		}
		else {
			System.out.println("服务器发送信息失败，服务器未启动：" + msg);
		}
	}

	public void executeMessage(String msg) {
		System.out.println("服务器处理信息：" + msg);
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

	public void update() {
		try {
			String line = in.readLine();// 读取客户端每行的内容
			System.out.println("line : "+line);
			if (line == null) {
				done = true;
			}
			else {
				System.out.println("客户端发来消息：" + line);
				executeMessage(line);
			}
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