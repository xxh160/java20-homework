package gourdfight;

import java.util.HashMap;

import app.ImageLocate;
import app.ImagePane;
import app.TextLocate;
import app.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import world.Entity;
import framework.*;
import input.Key;
import input.KeyInput;
import input.Mouse;

public class PlayView extends View { // 游戏页面类
	
	HashMap<String, Entity> entityMap; // 游戏实体字典 
	HashMap<String, ImageLocate> imgLocateMap; // 游戏实体图片定位字典
	HashMap<String, TextLocate> textLocateMap; // 游戏实体文本定位字典
	
	// 初始化
	public PlayView() {
		super(Constants.IMAGE_PANE);
		entityMap = new HashMap<>();
		imgLocateMap = new HashMap<>();
		textLocateMap = new HashMap<>();
	}
	
	private void initEntity() { // 初始创建一些必要实体
		// 玩家1
		setPlayer1();
	}
	
	// Setter
	private void reset() { // 复位实体字典
		entityMap.clear();
		imgLocateMap.clear();
		textLocateMap.clear();
	}
	
	private void setPlayer1() { // 初设玩家1
		
		Entity player1 = new Entity(Constants.PLAYER1);
		player1.setMobile(true);
		Image player1_img = new Image("gourdfight/swift.png"); // test
		player1.addImage(Constants.PLAYER1_INIT_IMAGE, player1_img);
		addEntity(Constants.PLAYER1, player1);
				
		ImageLocate player1_imgLocate = new ImageLocate(
				player1_img,
				Constants.PLAYER1_INIT_X,
				Constants.PLAYER1_INIT_Y,
				Constants.PLAYER1_INIT_W,
				Constants.PLAYER1_INIT_H);
		
		addImageLocate(Constants.PLAYER1, player1_imgLocate);
	}
	
	private void updatePlayer1() { // 更新玩家1
		if(Framework.keyInput.isPressed(Key.D)) { // 玩家1向右移动
			
			entityMap.get(Constants.PLAYER1).moveRight();
			double deltaX = entityMap.get(Constants.PLAYER1).getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(
					Constants.PLAYER1_INIT_X + deltaX
					);
		}
		if(Framework.keyInput.isPressed(Key.A)) { // 玩家1向左移动
			
			entityMap.get(Constants.PLAYER1).moveLeft();
			double deltaX = entityMap.get(Constants.PLAYER1).getDeltaX();
			imgLocateMap.get(Constants.PLAYER1).setX(
					Constants.PLAYER1_INIT_X + deltaX
					);
		}
	}
	
	public void addEntity(String id, Entity entity) { // 添加实体
		if(id != null && entity != null) {
			entityMap.put(id, entity);
		}
	}
	
	public void removeEntity(String id) { // 删除实体
		if(id != null) {
			entityMap.remove(id);
		}
	}
	
	public void addImageLocate(String id, ImageLocate imgLocate) { // 添加图片定位
		if(id != null && imgLocate != null) {
			imgLocateMap.put(id, imgLocate);
		}
	}
	
	public void removeImageLocate(String id) { // 删除图片定位
		if(id != null) {
			imgLocateMap.remove(id);
		}
	}
	
	public void addTextLocate(String id, TextLocate textLocate) { // 添加文本定位
		if(id != null && textLocate != null) {
			textLocateMap.put(id, textLocate);
		}
	}
	
	public void removeTextLocate(String id) { // 删除文本定位
		if(id != null) {
			textLocateMap.remove(id);
		}
	}
	
	
	// 生命周期管理
	public void onLaunch() { // 页面启动设置
		reset(); // 清空实体
		initEntity(); // 初始化游戏实体
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
		// 更新实体属性
		updatePlayer1(); // 更新玩家1
		
		// 更新实体图片/文本
		((ImagePane) pane).update(imgLocateMap.values(),textLocateMap.values());
		
		super.onUpdate(time);
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}

}
