package framework;

import app.App;
import app.Engine;
import input.KeyInput;
import input.MouseInput;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.22
 * @inherit 
 * @functions 为游戏框架提供一些重要的全局静态对象，方便随时调用
 * @properties app、engine、keyInput、mouseInput
 * @methods  	
 */

public class Framework { // 全局框架类，存放全局静态模块对象
	
	public static App app; // 应用窗口模块
	
	public static Engine engine; // 游戏引擎模块
	
	public static KeyInput keyInput; // 键盘输入模块
	
	public static MouseInput mouseInput; // 鼠标输入模块
}
