package main;

import app.Game;
import framework.Constants;
import framework.Framework;
import view.GameOverView;
import view.HomeView;
import view.ModeView;
import view.NetView;
import view.PlayView;
import view.Role1View;
import view.Role2View;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.22
 * @inherit Game
 * @functions 提供主函数作为游戏的入口，以及创建所有的游戏界面
 * @properties
 * @methods  	
 * 		main(String[] args):主函数
 * 		onLaunch(): 创建所有的游戏界面
 */

public class Main extends Game { // 主函数类，程序入口
	public static void main(String[] args) {
		launch(args);
	}

	// 游戏生命周期管理
	@Override
	public void onLaunch() {
		// 游戏启动设置
		Framework.app.setTitle(Constants.WINDOW_TITLE); // 设置窗口标题
		Framework.app.setWidth(Constants.WINDOW_WIDTH); // 设置窗口宽度
		Framework.app.setHeight(Constants.WINDOW_HEIGHT); // 设置窗口高度
		Framework.app.setResizeable(false); // 设置窗口不可改变大小
				
		// 注册页面
		Framework.app.regView(Constants.HOME_VIEW_KEY, new HomeView()); // 主页面
		Framework.app.regView(Constants.PLAY_VIEW_KEY, new PlayView()); // 游戏页面
		Framework.app.regView(Constants.MODE_VIEW_KEY, new ModeView()); // 模式选择页面
		Framework.app.regView(Constants.NET_VIEW_KEY, new NetView()); // 网络端选择页面
		Framework.app.regView(Constants.ROLE1_VIEW_KEY, new Role1View()); // 玩家1角色选择页面
		Framework.app.regView(Constants.ROLE2_VIEW_KEY, new Role2View()); // 玩家2角色选择页面
		Framework.app.regView(Constants.GAMEOVER_VIEW_KEY, new GameOverView()); // 游戏结束页面
		
		// 首先跳转到主页面
		Framework.app.gotoView(Constants.HOME_VIEW_KEY);
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
