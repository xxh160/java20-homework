package gourdfight;

import app.Game;
import framework.Framework;

public class Main extends Game { // 主函数类，程序入口
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void onLaunch() {
		// 游戏启动设置
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
		// 游戏结束设置
		super.onFinish();
	}
	
	@Override
	public boolean onExit() {
		// 游戏退出许可设置
		return super.onExit();
	}
}
