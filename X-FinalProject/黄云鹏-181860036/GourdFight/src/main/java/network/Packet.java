package network;

import world.EntityState;

public class Packet { // 数据包，定义服务器和客户端交互的数据结构

	int frame; // 帧数
	EntityState action; // 动作(对应于待切换的状态)
	
	private boolean sendReady; // 发送允许
	
	// 初始化
	public Packet() {
		sendReady = false;
	}
	
	public Packet(int frame, EntityState action) {
		this.frame = frame;
		this.action = action;
		sendReady = false;
	}
	
	// Getter
	public boolean isSendReady() { // 判断是否允许发送
		return sendReady;
	}
	
	public int getFrame() { // 获取帧数
		return frame;
	}
	
	public EntityState getAction() { // 获取动作
		return action;
	}
	
	// Setter
	public void setSendReady(boolean s) { // 设置发送允许
		sendReady = s;
	}
	
	// 发送和接收
	public String send() { // 将数据结构封装成一个字符串发送
		String frameString = "" + frame + " ";
		String actionString = action.getState() + System.lineSeparator();
		String sendString = frameString + actionString;
		
		sendReady = false; // 每发送一次后自动撤销发送允许
		
		return sendString;
	}
	
	public void receive(String line) { // 接收一个字符串并解封成数据结构
		String[] msg = line.split(" ");
		frame = Integer.valueOf(msg[0]);
		action = EntityState.find(msg[1]);
	}
	
}
