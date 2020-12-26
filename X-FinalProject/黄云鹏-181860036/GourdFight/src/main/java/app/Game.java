package app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.22
 * @inherit Application
 * @functions 提供游戏应用主入口和声明周期管理的抽象类，子类为游戏实例对象类
 * @properties app
 * @methods 
 * 		main(): java程序入口函数
 * 		launch(String args): main函数中启动游戏的入口静态函数，继承自Application
 * 		onLaunch(): 游戏启动设置，例如初始化app窗口的标题、大小等，以及创建所有的页面，包括主页面
 * 		onFinish(): 游戏结束设置，例如游戏存档或者显示游戏结束页面等
 * 		onExit(): 游戏退出许可设置，例如在某种情况下，不允许用户退出游戏 	
 */

public abstract class Game extends Application { // 游戏抽象类，子类为游戏实例
	
	private App app;
	
	// 游戏生命周期管理
	public abstract void onLaunch(); // 游戏启动管理，子类必须实现
	
	public void onFinish() {
		// 游戏结束管理，子类选择实现
	}
	
	public boolean onExit() {
		// 游戏退出许可管理，子类选择实现
		return true;
	}

	// 游戏启动
	@Override
	public final void start(Stage primaryStage) throws Exception {
		// 创建应用对象
		app = new App(primaryStage);
		// 生命周期管理
		app.onLaunch = new App.OnLaunch() {
			
			@Override
			public void handle() {
				onLaunch();
			}
		};
		app.onFinish = new App.OnFinish() {
			
			@Override
			public void handle() {
				onFinish();
			}
		};
		app.onExit = new App.OnExit() {
			
			@Override
			public boolean handle() {
				return onExit();
			}
		};
		
		// 启动应用
		app.launch();
	}
	
	// 游戏暂停
	@Override
	public final void stop() throws Exception {
		// 退出游戏
		app.finish();
	}
	
}
