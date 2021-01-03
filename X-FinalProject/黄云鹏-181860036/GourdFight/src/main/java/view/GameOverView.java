package view;

import app.View;
import framework.Constants;
import framework.Framework;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import world.Entity;
import world.EntityState;

public class GameOverView extends View { // 游戏结束页面，用于显示游戏结果，以及进行下一步操作(重新开始游戏/记录游戏/退出游戏)

	private ImageView imgView; // 玩家胜负状态图片显示器
	
	private Button saveBtn; // 游戏存档按钮
	private Button backBtn; // 返回主页面按钮
	private Button exitBtn; // 退出游戏按钮
	
	// 初始化
	public GameOverView() {
		super(Constants.STACK_PANE);
		bgmFileStr = Constants.GAMEOVER_VIEW_BGM; // 设置背景音频文件
		autoPlay = true;
		autoStop = true;
	}

	// 页面生命周期管理
	@Override
	public void onLaunch() {
		String d = Constants.BGM;
		String f = "button";
		// 定义图片显示器
		imgView = new ImageView();
		imgView.setCache(true);
		imgView.setPreserveRatio(true);
		imgView.setSmooth(true);
		imgView.setFitWidth(Constants.GAMEOVER_IMAGEVIEW_W);
        imgView.setFitHeight(Constants.GAMEOVER_IMAGEVIEW_H);
        
		// 定义按钮以及点击事件
		saveBtn = new Button("Save Game");
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 保存游戏记录
				Framework.log.saveGame();
			}
		});
		
		backBtn = new Button("Back To Home");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 先清空PlayView
				PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
				playView.clear();
				// 回到主页面
				Framework.app.gotoView(Constants.HOME_VIEW_KEY);
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
	
		// 定义布局
		saveBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		backBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		exitBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		
		VBox vBox = new VBox(saveBtn,backBtn,exitBtn); // 按钮纵向布局
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(20); 
		
		
		HBox hBox = new HBox(imgView,vBox); // 图片和按钮水平布局
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(50);
		
		getChildren().add(hBox);
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
		
		// 获取玩家1的胜负状态的图片
		PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
		Entity player1 = playView.getEntity(Constants.PLAYER1);
		boolean isWinner = playView.isWinner();
		if(isWinner)
			player1.setState(EntityState.WINNER);
		else
			player1.setState(EntityState.LOSER);
		Image img = player1.getCurrentImage(); 
		imgView.setImage(img);
	}
	
	@Override
	public void onUpdate(double time) {
		super.onUpdate(time);
	}
	
	@Override
	public void onLeave() {
		super.onLeave();
	}

}
