package view;

import app.View;
import framework.Constants;
import framework.Framework;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import output.URL;

public class ModeView extends View { // 模式选择页面

	private ImageView bgImgView; // 背景图片管理器
	private Button singleBtn; // 单机版按钮
	private Button netBtn; // 网络版按钮
	
	public ModeView() {
		super(Constants.STACK_PANE);
		bgmFileStr = Constants.MODE_VIEW_BGM; // 设置背景音频文件
	}

	@Override
	public void onLaunch() {
		String d = Constants.BGM;
		String f = "button";
		// 定义控件
		singleBtn = new Button("Single Mode");
		singleBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 设置单机版模式
				PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
				playView.setMode(false);
				Role1View roleView = (Role1View)Framework.app.getView(Constants.ROLE1_VIEW_KEY);
				roleView.setMode(false);
				// 切换到玩家1角色选择页面
				Framework.app.gotoView(Constants.ROLE1_VIEW_KEY);
			}
		});
		
		netBtn = new Button("Net Mode");
		netBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 设置网络版模式
				PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
				playView.setMode(true);
				Role1View roleView = (Role1View)Framework.app.getView(Constants.ROLE1_VIEW_KEY);
				roleView.setMode(true);
				// 切换到网络端选择页面
				Framework.app.gotoView(Constants.NET_VIEW_KEY);
			}
		});
		
		singleBtn.setMinWidth(Constants.HOME_VIEW_BUTTON_W);
		netBtn.setMinWidth(Constants.HOME_VIEW_BUTTON_W);
		
		VBox box = new VBox(singleBtn,netBtn);
		box.setAlignment(Pos.CENTER); 
		box.setSpacing(20); 
		
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
	
}
