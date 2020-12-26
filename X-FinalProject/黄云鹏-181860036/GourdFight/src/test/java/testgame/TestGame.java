package testgame;

import app.Game;
import testview.HomeView;
import testview.PlayView;

import framework.*;

public class TestGame extends Game { // Game测试类
	
	public static void main(String[] args) {
		launch(args); // Application的启动方法
	}

	@Override
	public void onLaunch() {
		// 游戏启动设置测试
		Framework.app.setTitle("Test Game"); // 设置窗口标题
		Framework.app.setWidth(800); // 设置窗口宽度
		Framework.app.setHeight(600); // 设置窗口高度
		
		// 注册页面
		Framework.app.regView("Home", new HomeView()); // 主页面
		Framework.app.regView("Play", new PlayView()); // 游戏界面
		Framework.app.gotoView("Home");
	}
	
	@Override
	public void onFinish() {
		// 游戏结束设置测试
		super.onFinish();
		System.out.println("onFinish Test");
	}
	
	@Override
	public boolean onExit() {
		// 游戏退出许可设置测试
		System.out.println("onExit Test");
		return super.onExit();
	}
	
}
