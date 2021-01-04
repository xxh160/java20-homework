package input;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

/**
 * 
 * @author 王浩天
 * @version	2020.12.24
 * @inherit Mouse
 * @functions 框架鼠标输入模块类，提供鼠标输入事件的响应
 * @properties units
 * @methods 
 * 		isPressed(Mouse mouse): 判断按键mouse是否被按下
 * 		isReleased(Mouse mouse): 判断按键mouse是否被释放
 * 		isHeld(Mouse mouse): 判断按键mouse是否被按住保持
 * 		isClicked(Mouse mouse): 判断按键mouse是否被按击，即被按下又释放
 * 		isDragged(Mouse mouse): 判断按键mouse是否被拖拽
 * 		isScrolled(): 判断鼠标滚轮是否滚动
 * 		getPointX() / getPointY(): 获取鼠标当前X/Y轴坐标
 * 		getPressX(Mouse mouse) / getPressY(Mouse mouse): 获取按键mouse按下时的X/Y轴坐标
 * 		getReleaseX(Mouse mouse) / getReleaseY(Mouse mouse): 获取按键mouse释放时的X/Y轴坐标
 * 		getDragX(Mouse mouse) / getDragY(Mouse mouse): 获取按键mouse拖拽的X/Y轴距离
 * 		getScrollValue(): 获取鼠标滚轮滚动值
 * 		getClickCount(Mouse mouse): 获取按键mouse连击次数
 */

public class MouseInput {

	private double pointX; // 鼠标当前X轴坐标
	private double pointY; // 鼠标当前Y轴坐标
	private double scrollValue; // 鼠标滚轮距离(向上/下滚动，距离为正/负)
	
	private final class Unit{ // 鼠标按键单元内部类，负责记录每个按键的瞬时状态和计数状态
		
		private boolean pressed; // 被按下
		private boolean released; // 被释放
		private boolean held; // 被按住保持
		
		private double pressX; // 鼠标按下的X轴坐标
		private double pressY; // 鼠标按下的Y轴坐标
		
		private double releaseX; // 鼠标释放的X轴坐标
		private double releaseY; // 鼠标释放的Y轴坐标
		
		private double dragX; // 帧间鼠标X轴的拖拽距离(瞬时值，帧更新时复位)
		private double dragY; // 帧间鼠标Y轴的拖拽距离(瞬时值，帧更新时复位)
		private double dragStoreX; // 真实鼠标X轴的拖拽距离(状态值，帧更新时不复位)
		private double dragStoreY; // 真实鼠标Y轴的拖拽距离(状态值，帧更新时不复位)
		private double dragMarkX; // 鼠标拖拽时的X轴坐标
		private double dragMarkY; // 鼠标拖拽时的Y轴坐标
		
		private int clickCount; // 帧间连续点击次数(瞬时值，帧更新时复位)
		private int clickStore; // 真实连续点击次数(状态值，帧更新时不复位)
		private long clickStamp; // 连续点击时间戳(单位：毫秒)
		
		private final int CLICK_DURATION = 200; // 相邻两次点击的响应时间间隔(单位：毫秒)
		
		// 初始化
		public Unit() {
			
		}
		
		// 状态转换
		public void press(double x, double y) { // 鼠标按下
			if(!held) {
				pressed = true;
				held = true;
				
				pressX = x;
				pressY = y;
				
				dragMarkX = x;
				dragMarkY = y;
			}
		}
		
		public void release(double x, double y) { // 鼠标释放
			if(held) {
				released = true;
				held = false;
				
				releaseX = x;
				releaseY = y;
				
				dragMarkX = 0;
				dragMarkY = 0;
				
				dragStoreX = 0;
				dragStoreY = 0;
				
				clickCount = ++clickStore;
				clickStamp = System.currentTimeMillis();
			}
		}
		
		public void drag(double x, double y) { // 鼠标拖拽
			dragX = (dragStoreX += x - dragMarkX);
			dragY = (dragStoreY += y - dragMarkY);
			
			dragMarkX = x;
			dragMarkY = y;
		}
		
		public void refresh() { // 刷新
			pressed = released = false;
			dragX = dragY = pressX = pressY = releaseX = releaseY = 0;
			clickCount = 0;
			
			if(clickStamp > 0) {
				long now = System.currentTimeMillis();
				long stamp = clickStamp;
				
				if(now - stamp > CLICK_DURATION) {
					clickStore = 0;
					clickStamp = 0;
				}
			}
		}
		
		public void reset() { // 复位
			pressed = released = held = false;
			
			pressX = pressY = releaseX = releaseY = dragMarkX = dragMarkY = dragStoreX = dragStoreY = dragX = dragY = 0;
			
			clickCount = clickStore = 0;
			clickStamp = 0L;
		}
	}
	
	private final Unit[] units; // 鼠标按键单元数组，与每一个按键一一对应
	
	// 初始化
	public MouseInput() {
		units = new Unit[Mouse.values().length];
		for(int i=0; i<units.length; i++) {
			units[i] = new Unit();
		}
	}
	
	// Getter
	public double getPointX() { // 获取鼠标当前X轴坐标
		return pointX;
	}
	
	public double getPointY() { // 获取鼠标当前Y轴坐标
		return pointY;
	}
	
	public double getScrollValue() { // 获取鼠标滚轮滚动值
		return scrollValue;
	}
	
	public boolean isPressed(Mouse mouse) { // 判断按键mouse是否被按下
		return mouse != null ? units[mouse.ordinal()].pressed : false;
	}
	
	public boolean isReleased(Mouse mouse) { // 判断按键mouse是否被释放
		return mouse != null ? units[mouse.ordinal()].released : false;
	}
	
	public boolean isHeld(Mouse mouse) { // 判断按键mouse是否被按住保持
		return mouse != null ? units[mouse.ordinal()].held : false;
	}
	
	public boolean isDragged(Mouse mouse) { // 判断按键是否被拖拽
		return (getDragX(mouse) > 0) || (getDragY(mouse) > 0);
	}
	
	public boolean isClicked(Mouse mouse) { // 判断按键是否被点击
		return getClickCount(mouse) > 0;
 	}
	
	public boolean isScrolled() { // 判断鼠标滚轮是否滚动
		return scrollValue != 0;
	}
	
	public double getPressX(Mouse mouse) { // 获取按键按下X轴坐标
		return mouse != null ? units[mouse.ordinal()].pressX : 0;
	}
	
	public double getPressY(Mouse mouse) { // 获取按键按下Y轴坐标
		return mouse != null ? units[mouse.ordinal()].pressY : 0;
	}
	
	public double getReleaseX(Mouse mouse) { // 获取按键释放X轴坐标
		return mouse != null ? units[mouse.ordinal()].releaseX : 0;
	}
	
	public double getReleaseY(Mouse mouse) { // 获取按键释放Y轴坐标
		return mouse != null ? units[mouse.ordinal()].releaseY : 0;
	}
	
	public double getDragX(Mouse mouse) { // 获取按键X轴拖拽距离
		return mouse != null ? units[mouse.ordinal()].dragX : 0;
	}
	
	public double getDragY(Mouse mouse) { // 获取按键Y轴拖拽距离
		return mouse != null ? units[mouse.ordinal()].dragY : 0;
	}
	
	public int getClickCount(Mouse mouse) { // 获取按键点击次数
		return mouse != null ? units[mouse.ordinal()].clickCount : 0;
	}
	
	// Setter
	public void point(double x, double y) { // 设置鼠标当前坐标
		pointX = x;
		pointY = y;
	}
	
	public void scroll(double value) { // 鼠标滚动
		scrollValue += value;
	}
	
	public void press(Mouse mouse, double x, double y) { // 按下按键
		if(mouse != null) {
			units[mouse.ordinal()].press(x, y);
		}
	}
	
	public void release(Mouse mouse, double x, double y) { // 释放按键
		if(mouse != null) {
			units[mouse.ordinal()].release(x, y);
		}
	}
	
	public void drag(Mouse mouse, double x, double y) { // 拖拽按键
		if(mouse != null) {
			units[mouse.ordinal()].drag(x, y);
		}
	}
	
	public void refresh() { // 刷新
		for(int i=0; i<units.length; i++) {
			units[i].refresh();
		}
		
		scrollValue = 0;
	}
	
	public void reset() { // 复位
		pointX = 0;
		pointY = 0;
		
		for(int i=0; i<units.length; i++) {
			units[i].reset();
		}
		
		scrollValue = 0;
	}
	
	// 安装和卸载
	public void install(Stage stage) { // 将鼠标输入模块安装到stage上
		stage.addEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved); // 窗口响应鼠标移动事件
		stage.addEventHandler(ScrollEvent.SCROLL, this::handleMouseScrolled); // 窗口响应鼠标滚轮滚动事件
		stage.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed); // 窗口响应按键按下事件
		stage.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased); // 窗口响应按键释放事件
		stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged); // 窗口响应按键拖拽事件
		
		reset();
	}
	
	public void uninstall(Stage stage) { // 将键盘输入模块从stage上卸载
		stage.removeEventHandler(MouseEvent.MOUSE_MOVED, this::handleMouseMoved); // 窗口响应鼠标移动事件
		stage.removeEventHandler(ScrollEvent.SCROLL, this::handleMouseScrolled); // 窗口响应鼠标滚轮滚动事件
		stage.removeEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed); // 窗口响应按键按下事件
		stage.removeEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased); // 窗口响应按键释放事件
		stage.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged); // 窗口响应按键拖拽事件
		
		reset();
	}
	
	private final void handleMouseMoved(MouseEvent event) { // 鼠标移动事件处理
		point(event.getX(), event.getY());
	}
	
	private final void handleMouseScrolled(ScrollEvent event) { // 鼠标滚轮滚动事件处理
		scroll(event.getDeltaY());
	}
	
	private final void handleMousePressed(MouseEvent event) { // 按键按下事件处理
		press(Mouse.find(event.getButton()), event.getX(), event.getY());
	}
	
	private final void handleMouseReleased(MouseEvent event) { // 按键释放事件处理
		release(Mouse.find(event.getButton()), event.getX(), event.getY());
	}
	
	private final void handleMouseDragged(MouseEvent event) { // 按键拖拽事件处理
		point(event.getX(), event.getY());
		drag(Mouse.find(event.getButton()), event.getX(), event.getY());
	}
	
}
