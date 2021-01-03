package view;

import app.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import output.URL;
import framework.*;

public class HomeView extends View { // 主页面类

	private ImageView bgImgView; // 背景图片管理器
	private Button newGameBtn; // 新游戏按钮
	private Button playBackBtn; // 游戏回放按钮
	private Button exitBtn; // 退出游戏按钮
	
	public HomeView() {
		super(Constants.STACK_PANE);
		bgmFileStr = Constants.HOME_VIEW_BGM; // 设置背景音频文件
		autoPlay = true;
	}
	
	// 生命周期管理
	@Override
	public void onLaunch() {
		String d = Constants.BGM;
		String f = "button";
		// 页面启动设置
		newGameBtn = new Button("New Game"); 
		newGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 切换到模式选择页面
				Framework.app.gotoView(Constants.MODE_VIEW_KEY);
			}
		});
		
		playBackBtn = new Button("Play Back"); 
		playBackBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 打开游戏记录
				Framework.log.openGame();
				// 切换到游戏页面
				Framework.app.gotoView(Constants.PLAY_VIEW_KEY);
			}
		});
		
		
		exitBtn = new Button("Exit");
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 调出欢迎下次光临的提示对话框
				Alert welcomeBack = new Alert(AlertType.INFORMATION);
				welcomeBack.setHeaderText("Good Bye!");
				welcomeBack.setContentText("Welcome to you next Time!!!");
				welcomeBack.showAndWait();
				// 退出游戏
				Framework.app.exit();
			}
			
		});
		
		newGameBtn.setMinWidth(Constants.HOME_VIEW_BUTTON_W);
		playBackBtn.setMinWidth(Constants.HOME_VIEW_BUTTON_W);
		exitBtn.setMinWidth(Constants.HOME_VIEW_BUTTON_W);

		 // 纵向布局
		VBox box = new VBox(newGameBtn,playBackBtn,exitBtn);
		box.setAlignment(Pos.CENTER); 
		box.setSpacing(20); 
		
		// 设置背景图片
		bgImgView = new ImageView();
		bgImgView.setCache(true);
		bgImgView.setSmooth(true);
		bgImgView.setPreserveRatio(true);
		bgImgView.setFitWidth(Constants.BACKGROUND_W);
		bgImgView.setFitHeight(Constants.BACKGROUND_H);
		String filePath = URL.toPngPath(Constants.MAIN_DIRECTORY,
				Constants.BACKGROUND_DIRECTORY, Constants.HOME_VIEW_KEY);
		String url = URL.toURL(filePath);
		Image img = new Image(url);
		bgImgView.setImage(img);
		
		getChildren().add(bgImgView);
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

