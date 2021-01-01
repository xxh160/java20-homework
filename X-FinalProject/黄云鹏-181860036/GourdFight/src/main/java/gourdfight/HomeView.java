package gourdfight;

import app.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import framework.*;
import input.Key;
import input.KeyInput;
import input.Mouse;

public class HomeView extends View { // 主页面类

	private Button playBtn; // 开启游戏按钮
	private Button exitBtn; // 退出游戏按钮
	
	public HomeView() {
		super(Constants.STACK_PANE);
		bgmFileStr = Constants.HOME_VIEW_BGM; // 设置背景音频文件
	}
	
	// 生命周期管理
	@Override
	public void onLaunch() {
		// 页面启动管理测试
		playBtn = new Button("Play"); 
		playBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// 切换到游戏页面
				Framework.app.gotoView(Constants.PLAY_VIEW_KEY);
			}
		});
		
		exitBtn = new Button("Exit");
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// 退出游戏
				Framework.app.exit();
			}
			
		});
		
		VBox box = new VBox(playBtn,exitBtn); // 纵向布局
		box.setAlignment(Pos.CENTER); // 对齐设置
		box.setSpacing(20); // 设置间距
		
		getChildren().add(box);
	}
	
	@Override
	public void onFinish() {
		super.onFinish();
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
	}
	
	@Override
	public void onLeave() {
		super.onLeave();
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onUpdate(double time) {
		
		super.onUpdate(time);
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}

}

