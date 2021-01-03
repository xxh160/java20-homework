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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import output.URL;
import world.EntityName;

public class Role2View extends View { // 玩家2角色选择页面(单机版)
	
	private ImageView imgView; // 角色身份图片显示器
	
	private Button playBtn; // 开始游戏
	
	private Button redBabyBtn; // 大娃选择按钮
	private Button orangeBabyBtn; // 二娃选择按钮
	private Button yellowBabyBtn; // 三娃选择按钮
	private Button greenBabyBtn; // 四娃选择按钮
	private Button blueBabyBtn; // 五娃选择按钮
	private Button indigoBabyBtn; // 六娃选择按钮
	private Button purpleBabyBtn; // 七娃选择按钮
	
	private Button snakeBtn; // 蛇精选择按钮
	private Button scorpionBtn; // 蝎子精选择按钮
	private Button chilopodBtn; // 蜈蚣精选择按钮
	private Button crocodileBtn; // 鳄鱼精选择按钮

	// 初始化
	public Role2View() {
		super(Constants.STACK_PANE);
		bgmFileStr = Constants.ROLE_VIEW_BGM; // 设置背景音频文件
	}
	
	@Override
	public void onLaunch() {
		String d = Constants.BGM;
		String f = "button";
		// 定义图片显示器
		imgView = new ImageView();
		imgView.setCache(true);
		imgView.setPreserveRatio(true);
		imgView.setSmooth(true);
		imgView.setFitWidth(Constants.GAMEOVER_IMAGEVIEW_H);
		imgView.setFitHeight(Constants.GAMEOVER_IMAGEVIEW_W);
		String filePath = URL.toPngPath("main", Constants.REDBABY_NAME, "ID");
		String url = URL.toURL(filePath);
		Image img = new Image(url);
		imgView.setImage(img); // 默认初始为大娃的身份图片
		
		// 定义页面切换按钮
		playBtn = new Button("Play");
		playBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 切换到游戏页面
				Framework.app.gotoView(Constants.PLAY_VIEW_KEY);
			}
		});
		playBtn.setDisable(true);
		playBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		
		// 定义角色选择按钮
		String dirStr = "main";
		// 大娃
		redBabyBtn = new Button("大娃");
		redBabyBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.REDBABY_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.REDBABY);
					playBtn.setDisable(false);
				}
			}
		});
		redBabyBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 二娃
		orangeBabyBtn = new Button("二娃");
		orangeBabyBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.ORANGEBABY_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.ORANGEBABY);
					playBtn.setDisable(false);
				}
			}
		});
		orangeBabyBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 三娃
		yellowBabyBtn = new Button("三娃");
		yellowBabyBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.YELLOWBABY_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.YELLOWBABY);
					playBtn.setDisable(false);
				}
			}
		});
		yellowBabyBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 四娃
		greenBabyBtn = new Button("四娃");
		greenBabyBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.GREENBABY_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.GREENBABY);
					playBtn.setDisable(false);
				}
			}
		});
		greenBabyBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 五娃
		blueBabyBtn = new Button("五娃");
		blueBabyBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.BLUEBABY_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.BLUEBABY);
					playBtn.setDisable(false);
				}
			}
		});
		blueBabyBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 六娃
		indigoBabyBtn = new Button("六娃");
		indigoBabyBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.INDIGOBABY_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.INDIGOBABY);
					playBtn.setDisable(false);
				}
			}
		});
		indigoBabyBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 七娃
		purpleBabyBtn = new Button("七娃");
		purpleBabyBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.PURPLEBABY_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.PURPLEBABY);
					playBtn.setDisable(false);
				}
			}
		});
		purpleBabyBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 蛇精
		snakeBtn = new Button("蛇精");
		snakeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.SNAKE_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.SNAKE);
					playBtn.setDisable(false);
				}
			}
		});
		snakeBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 蝎子精
		scorpionBtn = new Button("蝎子精");
		scorpionBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.SCORPION_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.SCORPION);
					playBtn.setDisable(false);
				}
			}
		});
		scorpionBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 蜈蚣精
		chilopodBtn = new Button("蜈蚣精");
		chilopodBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.CHILOPOD_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.CHILOPOD);
					playBtn.setDisable(false);
				}
			}
		});
		chilopodBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		// 鳄鱼精
		crocodileBtn = new Button("鳄鱼精");
		crocodileBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// 单击，则显示葫芦娃的身份牌
				playBtn.setDisable(true);
				Framework.audio.playClip(d, f);
				String filePath = URL.toPngPath(dirStr, Constants.CROCODILE_NAME, "ID");
				String url = URL.toURL(filePath);
				Image img = new Image(url);
				imgView.setImage(img);
				
				// 双击，设置player2的名字，并将play/next按钮的设为available
				if(event.getClickCount() == 2) {
					Framework.audio.playClip(d, f);
					PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
					playView.setPlayer2Name(EntityName.CROCODILE);
					playBtn.setDisable(false);
				}
			}
		});
		crocodileBtn.setMinWidth(Constants.GAMEOVER_BUTTON_W);
		
		// 设置布局
		VBox vBox = new VBox(redBabyBtn,orangeBabyBtn,yellowBabyBtn,
				greenBabyBtn,blueBabyBtn,indigoBabyBtn,purpleBabyBtn
				,snakeBtn,scorpionBtn,chilopodBtn,crocodileBtn);
		
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(20); 
		
		HBox vBox2 = new HBox(playBtn);
		vBox2.setAlignment(Pos.CENTER);
		vBox2.setSpacing(20);
		
		VBox hBox = new VBox(imgView,vBox2); // 图片和按钮水平布局
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(30);
		
		HBox box = new HBox(vBox,hBox);
		box.setAlignment(Pos.CENTER);
		box.setSpacing(50);
		
		getChildren().add(box);
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Role Select");
		alert.setContentText("Please choose the role for player2.");
		alert.showAndWait();
	}
	
}
