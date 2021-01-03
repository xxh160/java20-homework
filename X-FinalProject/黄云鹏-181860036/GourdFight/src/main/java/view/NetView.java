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

public class NetView extends View { // 网络端选择页面

	private ImageView bgImgView; // 背景图片管理器
	private Button serverBtn; // 服务器端选择按钮
	private Button clientBtn; // 客户端选择按钮
	
	public NetView() {
		super(Constants.STACK_PANE);
		bgmFileStr = Constants.NET_VIEW_BGM; // 设置背景音频文件
	}

	@Override
	public void onLaunch() {
		String d = Constants.BGM;
		String f = "button";
		// 定义控件
		serverBtn = new Button("As Server");
		serverBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 设置玩家1为服务器端
				PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
				playView.setServer(true);
				// 开启服务器端
				
				// 调出提示对话框，并在内容中提供给用户服务器IP
				
				// 调出等待客户端响应的模态对话框
				
				// 连接成功后，切换到玩家1角色选择页面
				Framework.app.gotoView(Constants.ROLE1_VIEW_KEY);
			}
		});
		
		clientBtn = new Button("As Client");
		clientBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Framework.audio.playClip(d, f);
				// 设置玩家1为客户端
				PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
				playView.setServer(false);
				// 调出包含textfield的输入对话框，要求用户输入服务器IP
				
				// 开启客户端
			
				// 调出等待连接服务器的模态对话框
				
				// 连接成功后，切换到玩家1角色选择页面
				Framework.app.gotoView(Constants.ROLE1_VIEW_KEY);
			}
		});
		
		VBox box = new VBox(serverBtn,clientBtn);
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
