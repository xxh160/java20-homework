package app;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import framework.*;
import input.KeyInput;
import input.MouseInput;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.22
 * @inherit 
 * @functions 为游戏类Game管理窗口、场景、根容器、游戏引擎以及所有页面
 * @properties stage、scene、root、viewMap、currentView、engine、keyInput、mouseInput...
 * @methods 
 * 		Setters: 设置窗口、场景、根容器等的属性
 * 		Getters: 获取窗口、场景、根容器等的属性
 * 		getView(String name): 获取指定名称的页面
 * 		getCurrentView(): 获取当前页面
 * 		regView(String name, View view): 注册页面
 * 		unregView(String name): 注销页面
 * 		gotoView(String name): 切换页面
 * 		exit(): 窗口/程序退出 	
 */

public class App {
	
	private final Stage stage; // 窗口
	private final Scene scene; // 场景
	private final Pane root; // 根容器
	
	private final HashMap<String, View> viewMap; // 页面字典
	private final ObjectProperty<View> currentView; // 当前页面
	
	private final Engine engine; // 游戏引擎
	
	private final KeyInput keyInput; // 键盘输入
	private final MouseInput mouseInput; // 鼠标输入
	
	OnLaunch onLaunch; // 窗口启动管理接口
	OnFinish onFinish; // 窗口关闭管理接口
	OnExit onExit; // 窗口退出许可管理接口
	
	// 初始化
	private final void initFramework() { // 初始化全局框架
		Framework.app = this;
		Framework.engine = this.engine;
		Framework.keyInput = this.keyInput;
		Framework.mouseInput = this.mouseInput;
	}
	
	private final void initApp() { // 初始化应用
		
		scene.setFill(Color.WHITE); // 场景背景色为白色
		root.setBackground(Background.EMPTY); // 根容器的背景设置为空
		
		stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// 窗口关闭请求事件处理
				if (onExit != null && !onExit.handle()) {
					event.consume(); // 停止向上委托，销毁应用
				}
			}
			
		});
		
		currentView.addListener(new  ChangeListener<View>(){

			@Override
			public void changed(ObservableValue<? extends View> observable, View oldValue, View newValue) {
				// 当前页面切换事件处理
				if (oldValue != null) { // 如果旧页面存在
					oldValue.onLeave(); // 退出旧页面
					root.getChildren().remove(oldValue.getPane()); // 从根容器中删除旧页面
				}
				
				if (newValue != null) { // 如果新页面存在
					root.getChildren().add(newValue.getPane()); // 往根容器中添加新页面
					newValue.onEnter(); // 进入新页面
				}
			}
			
		});
	}
	
	private final void initEngine() { // 初始化游戏引擎
		
		engine.onStart = new Engine.OnStart(){

			@Override
			public void handle() {
				for(View view:viewMap.values()) { // 启动所有页面
					view.onStart();
				}
				keyInput.install(stage); // 安装键盘输入模块
				mouseInput.install(stage); // 安装鼠标输入模块
			}
		};
		
		engine.onUpdate = new Engine.OnUpdate() {
			
			@Override
			public void handle(double time) {
				View view = getCurrentView();
				
				if (view != null) {
					view.onUpdate(time); // 当前页面更新
				}
				
				keyInput.refresh(); // 键盘输入模块刷新
				mouseInput.refresh(); // 鼠标输入模块刷新
			}
		};
		
		engine.onStop = new Engine.OnStop() {
			
			@Override
			public void handle() {
				keyInput.uninstall(stage); // 卸载键盘输入模块
				mouseInput.uninstall(stage); // 卸载鼠标输入模块
				
				for(View view:viewMap.values()) { // 关闭所有页面
					view.onStop();
				}
			}
		};
		
		stage.focusedProperty().addListener( new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				if(newValue) { // 窗口焦点更新时
					engine.start(); // 启动引擎
				}
				else {
					engine.stop(); // 否则暂停引擎，如程序在后台运行时
				}
			}
			
		});
	}
	
	public App(Stage stage) {
		this.stage = stage;
//		root = new Pane();
		root = new StackPane();
		scene = new Scene(root);
		stage.setScene(scene);
		
		viewMap = new HashMap<>();
		currentView = new SimpleObjectProperty<>();
		
		engine = new Engine();
		
		keyInput = new KeyInput();
		mouseInput = new MouseInput();
		
		initFramework();
		initApp();
		initEngine();
		
	}
	
	// Getter
	public Stage getStage() { // 获取窗口
		return stage;
	}
	
	public Scene getScene() { // 获取场景
		return scene;
	}
	
	public String getTitle() { // 获取标题
		return stage.getTitle();
	}
	
	public StringProperty titleProperty() { // 获取标题属性
		return stage.titleProperty();
	}
	
	public double getWidth() { // 获取根容器宽度
		return root.getMinWidth();
	}
	
	public DoubleProperty widthProperty() { // 获取根容器宽度属性
		return root.minWidthProperty();
	}
	
	public double getHeight() { // 获取根容器高度
		return root.getMinHeight();
	}
	
	public DoubleProperty heightProperty() { // 获取根容器高度属性
		return root.minHeightProperty();
	}
	
	public View getView(String name) { // 获取指定名称的页面对象
		return viewMap.get(name);
	}
	
	public View getCurrentView() { // 获取当前页面对象
		return currentView.get();
	}
	
	public ReadOnlyObjectProperty<View> currentViewProperty(){ // 获取当前页面属性(只读形式)
		return currentView;
	}
	
	// Setter
	public void setTitle(String title) { // 设置标题
		stage.setTitle(title);
	}
	
	public void setWidth(double width) { // 设置根容器宽度
		root.setMinWidth(width);
	}
	
	public void setHeight(double height) { // 设置根容器高度
		root.setMinHeight(height);
	}
	
	public void setResizeable(boolean value) { // 设置是否可以调节窗口大小
		stage.setResizable(value);
	}
	
	public void regView(String name,View view) { // 注册页面
		viewMap.put(name, view);
	}
	
	public void unregView(String name) { // 注销页面
		View view = viewMap.remove(name); // 从页面字典中删除
		
		if (view != null && view == getCurrentView()) { // 如果注销的页面是当前页面
			currentView.set(null); // 则还需要从当前页面中删除
		}
	}
	
	public void gotoView(String name) { // 切换页面，即设置当前页面
		View view = viewMap.get(name);
		if (view != null) {
			currentView.set(view);
		}
	}
	
	// 窗口启动(内部框架调用)
	void launch() {
		if (onLaunch != null) {
			onLaunch.handle();
		}
		
		for(View view : viewMap.values()) { // 启动所有页面
			view.onLaunch();
		}
		
		stage.requestFocus(); // 申请窗口焦点,以便接受鼠标/键盘事件
		stage.show();
	}
	
	// 窗口关闭(内部框架调用)
	void finish() {
		for(View view : viewMap.values()) { // 关闭所有页面
			view.onFinish();
		}
		
		if (onFinish != null) {
			onFinish.handle();
		}
	}
	
	// 窗口关闭(外部调用)
	public void exit() {
//		finish();
		Platform.exit();
	}
	
	// 窗口生命周期管理接口
	static interface OnLaunch{
		void handle(); // 窗口启动前的初始化
	}
	
	static interface OnFinish{
		void handle(); // 窗口关闭之前的处理
	}
	
	static interface OnExit{
		boolean handle(); // 是否允许窗口退出
	}
	
}

