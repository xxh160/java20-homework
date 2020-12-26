package input;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.23
 * @inherit Key
 * @functions 框架键盘输入模块类，提供键盘输入事件的响应
 * @properties units
 * @methods 
 * 		isPressed(Key key): 判断按键key是否被按下
 * 		isReleased(Key key): 判断按键key是否被释放
 * 		isHeld(Key key): 判断按键key是否被按住保持
 * 		isTyped(Key key): 判断按键key是否被按击，即被按下又释放
 * 		getTypeCount(Key key): 获取按键key连击次数
 */

public class KeyInput { // 键盘输入类，负责处理键盘输入
	
	private final class Unit{ // 键盘按键单元内部类，负责记录每个按键的瞬时状态和计数状态
		
		private boolean pressed; // 被按下(瞬时值，帧更新时复位)
		private boolean released; // 被释放(瞬时值，帧更新时复位)
		private boolean held; // 按住保持(状态值，帧更新时不复位)
		
		private int typeCount; // 帧间连续按键次数(瞬时值，帧更新时复位)
		private int typeStore; // 真实连续按键次数(状态值，帧更新时不复位)
		private long typeStamp; // 连续按键时间戳(单位：毫秒)
		
		private final int TYPE_DURATION = 200; // 相邻两次按键的响应时间间隔(单位：毫秒)
		
		// 初始化
		public Unit() {
			
		}
		
		// 状态转换
		public void press() { // 按下
			if(!held) {
				pressed = true;
				held = true;
			}
		}
		
		public void release() { // 释放
			if(held) {
				released = true;
				held = false;
				
				// 记录一次按键
				typeCount = ++typeStore;
				typeStamp = System.currentTimeMillis();
			}
		}

		public void refresh() { // 刷新
			pressed = false;
			released = false;
			
			typeCount = 0;
			if(typeStamp > 0) {
				long now = System.currentTimeMillis();
				long stamp = typeStamp;
				
				if(now - stamp > TYPE_DURATION) {
					typeStore = 0;
					typeStamp = 0;
				}
			}
		}
		
		public void reset() { // 复位
			pressed = released = held = false;
			typeCount = typeStore = 0;
			typeStamp = 0L;
		}
		
	}
	
	private final Unit[] units; // 键盘按键单元数组，与每一个按键一一对应
	
	// 初始化
	public KeyInput() {
		units = new Unit[Key.values().length];
		for(int i=0; i<units.length; i++) {
			units[i] = new Unit();
		}
	}
	
	// Getter
	public boolean isPressed(Key key) { // 判断按键key是否被按下
		return key != null ? units[key.ordinal()].pressed : false;
	}
	
	public boolean isReleased(Key key) { // 判断按键key是否被释放
		return key != null ? units[key.ordinal()].released : false;
	}
	
	public boolean isHeld(Key key) { // 判断按键key是否被按住保持
		return key != null ? units[key.ordinal()].held : false;
	}
	
	public boolean isTyped(Key key) { // 判断按键是否被按击
		return getTypeCount(key) > 0;
	}
	
	public int getTypeCount(Key key) { // 获取连续按键次数
		return key != null ? units[key.ordinal()].typeCount : 0;
	}
	
	// Setter
	public void press(Key key) { // 按下按键key
		if(key != null) {
			units[key.ordinal()].press();
		}
	}
	
	public void release(Key key) { // 释放按键key
		if(key != null) {
			units[key.ordinal()].release();
		}
	}
	
	public void refresh() { // 刷新按键单元数组
		for(int i=0; i<units.length; i++) {
			units[i].refresh();
		}
	}
	
	public void reset() { // 重置按键单元数组
		for(int i=0; i<units.length; i++) {
			units[i].reset();
		}
	}
	
	// 安装和卸载
	public void install(Stage stage) { // 将键盘输入模块安装到stage上
		stage.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed); // 窗口响应按键按下事件
		stage.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased); // 窗口响应按键释放事件
		
		reset();
	}
	
	public void uninstall(Stage stage) { // 将键盘输入模块从stage上卸载
		stage.removeEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed); // 窗口响应按键按下事件
		stage.removeEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased); // 窗口响应按键释放事件
		
		reset();
	}
	
	private final void handleKeyPressed(KeyEvent event) { // 按键按下事件处理
		press(Key.find(event.getCode()));
		press(Key.find(event.getText()));
	}
	
	private final void handleKeyReleased(KeyEvent event) { // 按键释放事件处理
		release(Key.find(event.getCode()));
		release(Key.find(event.getText()));
	}
	
}
