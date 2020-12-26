package app;

import javafx.animation.AnimationTimer;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.22
 * @inherit AnimationTimer(通过内部类Timer间接继承)
 * @functions 为应用类App提供游戏引擎，即每个页面的主循环/帧更新线程方法
 * @properties timer
 * @methods 
 * 		Setter: 设置帧率等属性
 * 		Getters: 获取各种帧时、帧率等属性
 * 		 	
 */

public class Engine { // 游戏引擎类

	private final Timer timer; // 游戏主循环处理类
	
	private double nowNanos; // 当前帧时刻(单位：纳秒)
	private double lastNanos; // 上一帧时刻(单位：纳秒)
	private double deltaNanos; // 上一帧耗时(单位：纳秒)
	private double fps; // 帧率，即每一秒的帧数(单位：帧)
	private double npf; // 每一帧所用纳秒(单位：纳秒)
	
	OnStart onStart; // 游戏引擎启动接口
	OnStop onStop; // 游戏引擎关闭接口
	OnUpdate onUpdate; // 帧更新接口
	
	// 初始化
	public Engine() {
		this(60); // 默认帧率
	}
	
	public Engine(double fps) {
		timer = new Timer();
		this.setFPS(fps);
	}
	
	// Getter
	public double getFPS() { // 获取帧率
		return fps;
	}
	
	public double getNowNanos() { // 获取当前帧时刻(单位：纳秒)
		return nowNanos;
	}
	
	public double getNowMillis() { // 获取当前帧时刻(单位：毫秒)
		return nowNanos * 1E-6;
	}
	
	public double getNowSecs() { // 获取当前帧时刻(单位：秒)
		return nowNanos * 1E-9;
	}
	
	public double getLastNanos() { // 获取上一帧时刻(单位：纳秒)
		return lastNanos;
	}
	
	public double getLastMillis() { // 获取上一帧时刻(单位：毫秒)
		return lastNanos * 1E-6;
	}
	
	public double getLastSecs() { // 获取上一帧时刻(单位：秒)
		return lastNanos * 1E-9;
	}
	
	public double getDeltaNanos() { // 获取上一帧耗时(单位：纳秒)
		return deltaNanos;
	}
	
	public double getDeltaMillis() { // 获取上一帧耗时(单位：毫秒)
		return deltaNanos * 1E-6;
	}
	
	public double getDeltaSecs() { // 获取上一帧耗时(单位：秒)
		return deltaNanos * 1E-9;
	}
	
	
	// Setter
	public void setFPS(double fps) { // 设置fps和npf
		this.fps = fps;
		this.npf = 1E9 / this.fps;
	}
	
	// Timer内部类，用于游戏主循环的处理
	private final class Timer extends AnimationTimer{
		// 游戏引擎启动
		@Override
		public void start() {
			this.reset();
			super.start();
			
			if (onStart != null) {
				onStart.handle();
			}
		}
		
		// 游戏引擎关闭
		@Override
		public void stop() {
			if(onStop != null) {
				onStop.handle();
			}
			
			super.stop();
			this.reset();
		}
		
		// 游戏主循环处理
		@Override
		public void handle(long now) {
			// 计算相邻帧时
			nowNanos = now;
			if (lastNanos > 0) {
				deltaNanos += nowNanos - lastNanos;
			}
			lastNanos = nowNanos;
			
			if(deltaNanos >= npf) { // 帧耗时不小于npf，则进行帧更新
				if (onUpdate != null) {
					onUpdate.handle(deltaNanos);
				}
				
				deltaNanos -= npf;
			}
		}
		
		// 重置帧计时器
		public void reset() { 
			nowNanos = lastNanos = deltaNanos = 0;
		}
	}
	
	// 游戏引擎启动
	void start() {
		timer.start();
	}
	
	// 游戏引擎关闭
	void stop() {
		timer.stop();
	}
	
	// 游戏引擎启动接口
	static interface OnStart {
		void handle();
	}
	
	// 游戏引擎关闭接口
	static interface OnStop{
		void handle();
	}
	
	// 帧更新接口
	static interface OnUpdate {
		void handle(double time);
	}

}
