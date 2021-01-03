package testview;

import app.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import framework.*;

public class PlayView extends View { // 游戏页面测试类

	private Button homeBtn; // 返回主页面按钮
	
	public PlayView() {
		super(Constants.STACK_PANE);
	}
	
	@Override
	public void onLaunch() {
		// 页面启动管理测试
		homeBtn = new Button("Home");
		homeBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// 返回到主页面
				Framework.app.gotoView("Home");
			}
		});
		
		getChildren().add(homeBtn);
	}

}
