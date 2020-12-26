package app;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.22
 * @inherit 
 * @functions 为游戏类Game提供所需的页面、管理根容器pane的抽象类，子类为页面实例对象类
 * @properties pane
 * @methods 
 * 		Setters: 设置页面相关属性
 * 		Getters: 获取页面相关属性
 * 		onLaunch(): 页面启动设置，例如添加页面上的子节点等
 * 		onFinish(): 页面关闭设置，例如保存文件等
 * 		onEnter(): 进入页面设置，例如进入页面前弹出提示对话框等
 * 		onLeave(): 退出页面设置，例如退出页面前弹出提示对话框等
 * 		onStart(): 游戏引擎启动页面设置，例如加载页面启动动画等
 * 		onStop(): 游戏引擎退出页面设置，例如加载页面退出动画等
 * 		onUpdate(double time): 游戏引擎更新页面帧更新设置，例如游戏角色更新动画等 	
 */

public abstract class View { // 游戏页面抽象类，子类为页面实例
	
	protected Pane pane; // 根容器
	
	// 初始化
	public View() {
//		pane = new Pane();
		pane = new StackPane(); // 栈布局
		pane.setBackground(Background.EMPTY); // 设置根容器的背景为空
	}
	
	// Getter
	public Pane getPane() { // 获取根容器
		return pane;
	}
	
	public ObservableList<Node> getChildren(){ // 获取子容器列表
		return pane.getChildren();
	}
	
	// Setter
	
	// 页面生命周期管理
	public abstract void onLaunch(); // 启动页面管理，子类必须实现
	
	public void onFinish() {
		// 结束页面管理，子类选择实现
	}
	
	public void onEnter() { 
		// 进入页面管理，子类选择实现
	}
	
	public void onStart() {
		// 游戏引擎启动页面管理，子类选择实现
	}
	
	public void onStop() {
		// 游戏引擎关闭页面管理，子类选择实现
	}
	
	public void onUpdate(double time) {
		// 页面帧更新，子类选择实现
	}
	
	public void onLeave() { 
		// 退出页面管理，子类选择实现
	}
	
}
