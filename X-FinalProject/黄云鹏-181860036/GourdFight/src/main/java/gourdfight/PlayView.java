package gourdfight;

import java.util.HashMap;
import java.util.LinkedHashMap;

import app.ImageLocate;
import app.ImagePane;
import app.ImageSet;
import app.TextLocate;
import app.View;

import output.URL;
import world.Entity;
import world.EntityState;
import framework.*;
import input.Key;

import javafx.scene.image.Image;

public class PlayView extends View { // 游戏页面类
	
	HashMap<String, Entity> entityMap; // 游戏实体字典 
	LinkedHashMap<String, ImageLocate> imgLocateMap; // 游戏实体图片定位字典
	LinkedHashMap<String, TextLocate> textLocateMap; // 游戏实体文本定位字典
	
	// 初始化
	public PlayView() {
		super(Constants.IMAGE_PANE);
		entityMap = new HashMap<>();
		imgLocateMap = new LinkedHashMap<>();
		textLocateMap = new LinkedHashMap<>();
	}
	
	private void initEntity() { // 初始创建一些必要实体
		// 背景
		setBackground();
		// 玩家1
		setPlayer1();
		// 玩家2
		setPlayer2();
	}
	
	private void setBackground() { // 初设背景
		Entity background = new Entity(Constants.BACKGROUND);
		
		String filePath = URL.toPngPath("test", "mario", "Mario_background");
		Image background_img = new Image(URL.toURL(filePath)); // test
		background.addImage(Constants.BACKGROUND_INIT_IMAGE, background_img);
		
		addEntity(Constants.BACKGROUND, background);
		
		ImageLocate background_imgLocate = new ImageLocate(
				background_img,
				Constants.BACKGROUND_X,
				Constants.BACKGROUND_Y,
				Constants.BACKGROUND_W,
				Constants.BACKGROUND_H);
		
		addImageLocate(Constants.BACKGROUND, background_imgLocate);
	}
	
	private void setPlayer1() { // 初设玩家1
		
		// 基本属性设置
		Entity player1 = new Entity(Constants.PLAYER1);
		player1.setMobile(true);
		player1.setAttackable(true);
		
		// 动画序列设置
		String nameStr = "redBaby"; // 大娃(测试角色)
		HashMap<EntityState, Integer> imgNumMap = new HashMap<>();
		
		// 朝左边
		imgNumMap.put(EntityState.STANDING_TOLEFT, 16);
		imgNumMap.put(EntityState.STANDING_TORIGHT, 16);
		imgNumMap.put(EntityState.STANDING_FORWARD, 16);
		imgNumMap.put(EntityState.RUNNING_TOLEFT, 16);
		imgNumMap.put(EntityState.RUNNING_TORIGHT, 16);
		imgNumMap.put(EntityState.JUMPING, 16);
		imgNumMap.put(EntityState.LYING, 16);
		imgNumMap.put(EntityState.WOUNDED, 16);
		imgNumMap.put(EntityState.DEFENDING, 16);
		imgNumMap.put(EntityState.ATTACKING_NEAR, 16);
		imgNumMap.put(EntityState.ATTACKING_FAR, 16);
		imgNumMap.put(EntityState.ATTACKING_KILL, 16);
	
		for(EntityState state: EntityState.values()){
			int num = imgNumMap.get(state);
			ImageSet imgSet = new ImageSet(num);
			for(int i=0; i<num; i++) {
				String numStr = i < 10 ? "0" + i : "" + i;
				String filePath = URL.toPngPath("main", nameStr, state + "0" + numStr); // "0"表示朝向左的图片
				Image img = new Image(URL.toURL(filePath));
				imgSet.setImage(i, img, true); // true表示朝向左的图片
			}
			player1.addImageSet(state, imgSet);
		}
		
		// 朝右边
		imgNumMap.clear();
		imgNumMap.put(EntityState.STANDING_TOLEFT, 16);
		imgNumMap.put(EntityState.STANDING_TORIGHT, 16);
		imgNumMap.put(EntityState.STANDING_FORWARD, 16);
		imgNumMap.put(EntityState.RUNNING_TOLEFT, 16);
		imgNumMap.put(EntityState.RUNNING_TORIGHT, 16);
		imgNumMap.put(EntityState.JUMPING, 16);
		imgNumMap.put(EntityState.LYING, 16);
		imgNumMap.put(EntityState.WOUNDED, 16);
		imgNumMap.put(EntityState.DEFENDING, 16);
		imgNumMap.put(EntityState.ATTACKING_NEAR, 16);
		imgNumMap.put(EntityState.ATTACKING_FAR, 16);
		imgNumMap.put(EntityState.ATTACKING_KILL, 16);
		
		for(EntityState state: EntityState.values()){
			int num = imgNumMap.get(state);
			ImageSet imgSet = new ImageSet(num);
			for(int i=0; i<num; i++) {
				String numStr = i < 10 ? "0" + i : "" + i;
				String filePath = URL.toPngPath("main", nameStr, state + "1" + numStr); // "1"表示朝向右的图片
				Image img = new Image(URL.toURL(filePath));
				imgSet.setImage(i, img, false); // false表示朝向右的图片
			}
			player1.addImageSet(state, imgSet);
		}
		

		// 添加实体
		addEntity(Constants.PLAYER1, player1);
				
		// 添加图片定位器
		ImageLocate player1_imgLocate = new ImageLocate(
				player1.getCurrentImage(),
				Constants.PLAYER1_INIT_X,
				Constants.PLAYER1_INIT_Y,
				Constants.PLAYER1_INIT_W,
				Constants.PLAYER1_INIT_H);
		
		addImageLocate(Constants.PLAYER1, player1_imgLocate);
	}
	
	private void setPlayer2() { // 初设玩家2
//		Entity player2 = new Entity(Constants.PLAYER2);
//		player2.setMobile(true);
//		
//		String filePath = URL.toPngPath("test", "mario", "Mario_standToRight");
//		Image player2_img = new Image(URL.toURL(filePath)); // test
//		player2.addImage(Constants.PLAYER2_INIT_IMAGE, player2_img);
//		addEntity(Constants.PLAYER2, player2);
//				
//		ImageLocate player2_imgLocate = new ImageLocate(
//				player2_img,
//				Constants.PLAYER2_INIT_X,
//				Constants.PLAYER2_INIT_Y,
//				Constants.PLAYER2_INIT_W,
//				Constants.PLAYER2_INIT_H);
//		
//		addImageLocate(Constants.PLAYER2, player2_imgLocate);
	}
	
	// Setter
	private void reset() { // 复位实体字典
		entityMap.clear();
		imgLocateMap.clear();
		textLocateMap.clear();
	}
	
	private void updatePlayer1() { // 更新玩家1
		if(Framework.keyInput.isTyped(Key.D)) { // 玩家1向右移动
			
			entityMap.get(Constants.PLAYER1).moveRight();
			double deltaX = entityMap.get(Constants.PLAYER1).getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(
					Constants.PLAYER1_INIT_X + deltaX
					);
			
			String filePath = URL.toPngPath("test", "mario", "Mario_runToRight");
			Image img = new Image(URL.toURL(filePath));
			imgLocateMap.get(Constants.PLAYER1).setImg(img);
		}
		else if(Framework.keyInput.isTyped(Key.A)) { // 玩家1向左移动
			
			entityMap.get(Constants.PLAYER1).moveLeft();
			double deltaX = entityMap.get(Constants.PLAYER1).getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(
					Constants.PLAYER1_INIT_X + deltaX
					);
			
			String filePath = URL.toPngPath("test", "mario", "Mario_runToLeft");
			Image img = new Image(URL.toURL(filePath));
			imgLocateMap.get(Constants.PLAYER1).setImg(img);
		}
		
		else {

		}
	}
	
	private void updatePlayer2() { // 更新玩家2
//		if(Framework.keyInput.isTyped(Key.L)) { // 玩家1向右移动
//			
//			entityMap.get(Constants.PLAYER2).moveRight();
//			double deltaX = entityMap.get(Constants.PLAYER2).getDeltaX();
//			
//			imgLocateMap.get(Constants.PLAYER2).setX(
//					Constants.PLAYER2_INIT_X + deltaX
//					);
//		}
//		if(Framework.keyInput.isTyped(Key.J)) { // 玩家1向左移动
//			
//			entityMap.get(Constants.PLAYER2).moveLeft();
//			double deltaX = entityMap.get(Constants.PLAYER2).getDeltaX();
//			imgLocateMap.get(Constants.PLAYER2).setX(
//					Constants.PLAYER2_INIT_X + deltaX
//					);
//		}
	}
	
	private void updateFrame() { // 更新帧

		((ImagePane) pane).update(
				imgLocateMap.values(),
				textLocateMap.values());
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
		reset(); // 先清空上一次启动时的字典
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
		
		// 更新实体
		updatePlayer1(); // 更新玩家1
		updatePlayer2(); // 更新玩家2
		
		// 更新帧
		updateFrame();
		
		super.onUpdate(time);
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}

}
