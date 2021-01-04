package view;

import java.util.Optional;

import app.View;
import framework.Constants;
import framework.Framework;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import output.URL;

public class NetView extends View { // 网络端选择页面

	private ImageView bgImgView; // 背景图片管理器
	private Button serverBtn; // 服务器端选择按钮
	private Button clientBtn; // 客户端选择按钮
	public Button nextBtn; // 下一步按钮


	public NetView() {
		super(Constants.STACK_PANE);
		bgmFileStr = Constants.NET_VIEW_BGM; // 设置背景音频文件
	}

	@Override
	public void onLaunch() {
		String d = Constants.BGM;
		String f = "button";
		// 定义控件
		nextBtn = new Button("Next");
		nextBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 切换到玩家1角色选择页面
				Framework.app.gotoView(Constants.ROLE1_VIEW_KEY);
			}
		});
		nextBtn.setDisable(true);
		nextBtn.setMinWidth(Constants.HOME_VIEW_BUTTON_W);
		
		serverBtn = new Button("As Server");
		serverBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 设置玩家1为服务器端
				PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
				playView.setServer(true);
				// 开启服务器端
				Framework.server.start();
				// 开启成功，即驻留在此处，作为服务器
				
			}
		});
		serverBtn.setMinWidth(Constants.HOME_VIEW_BUTTON_W);
		
		clientBtn = new Button("As Client");
		clientBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 设置玩家1为客户端
				PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
				playView.setServer(false);
				// 调出包含textfield的输入对话框，要求用户输入服务器IP
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Client");
				dialog.setContentText("Please enter the server's IP to connect");
				Optional<String> result = dialog.showAndWait();
				if(result.isPresent()) {
					System.out.println(result.get());
					playView.setServerIP(result.get());
				}
				// 调出等待连接服务器的模态对话框
				Alert wait = new Alert(AlertType.INFORMATION);
				wait.setHeaderText("Server");
				wait.setContentText("Waiting for connecting the server...");
				wait.showAndWait();
				// 开启客户端
				Framework.client.start();
//				// 连接成功，允许用户进入角色选择页面
//				Role1View roleView = (Role1View)Framework.app.getView(Constants.ROLE1_VIEW_KEY);
//				roleView.setMode(true);
//				nextBtn.setDisable(false);
			}
		});
		clientBtn.setMinWidth(Constants.HOME_VIEW_BUTTON_W);
		
		// 设置布局
		VBox box = new VBox(serverBtn,clientBtn,nextBtn);
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
