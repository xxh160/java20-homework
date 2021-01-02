package gourdfight;

import app.Game;
import framework.Framework;
import world.EntityName;

public class Main extends Game { // 主函数类，程序入口
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void onLaunch() {
		// 游戏启动设置
		Framework.app.setTitle(Constants.WINDOW_TITLE); // 设置窗口标题
		Framework.app.setWidth(Constants.WINDOW_WIDTH); // 设置窗口宽度
		Framework.app.setHeight(Constants.WINDOW_HEIGHT); // 设置窗口高度
		Framework.app.setResizeable(false); // 设置窗口不可改变大小
				
		// 注册页面
		Framework.app.regView(Constants.HOME_VIEW_KEY, new HomeView()); // 主页面
		Framework.app.regView(Constants.PLAY_VIEW_KEY, new PlayView()); // 游戏界面
		Framework.app.gotoView(Constants.HOME_VIEW_KEY);
		
		// test!!!!!!!!!!!!!!!!!!!!!!
//		((PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY)).setMode(true); // 网络版测试
//		((PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY)).setServer(true); // 服务器端测试
//		((PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY)).launchNetwork(); // 开启网络
		((PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY)).setMode(false); // 单机版测试
		((PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY)).setPlayer1Name(EntityName.ORANGEBABY); // 二娃测试
		((PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY)).setPlayer2Name(EntityName.GREENBABY); // 四娃测试
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
