package world;

import java.awt.print.Printable;
import java.util.HashMap;

import app.ImageSet;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.image.Image;

public class Entity { // 游戏实体类，所有游戏角色、道具等的父类
	
	private String name; // 实体名称
	private EntityState state; // 实体状态(默认"朝右边站着")
	
	private boolean isMobile; // 是否可移动(默认不可移动)
	private boolean isActive; // 是否活跃(默认活跃)
	private boolean isAttackable; // 是否具有攻击性(用于碰撞检测, 默认不具有攻击性)
	private boolean isLeft; // 朝向左边(false 则朝向右边, 默认朝向左边)
	private int jumpTag; // 跳跃标记(0:没有起跳,1:正在上升,2:正在下落,3:落地)
	
	private double deltaX; // x轴位移
	private double deltaY; // y轴位移
	private int frameCount; // 状态帧计数器
	
	private HashMap<EntityState,Image> imageMap; // 实体图片字典
	private HashMap<EntityState,String> textMap; // 实体文本字典 
	private HashMap<EntityState, Integer> frameMap; // 每个状态持续的帧数
	
	private double lifeValue; // 生命值
	private double moveSpeed; // 移动速度
	private double runSpeed; // 冲刺速度
	private double jumpSpeed; // 跳跃速度
	private double jumpHeight; // 跳跃高度
	private double attackNearValue; // 近攻攻击值
	private double attackFarValue; // 远攻攻击值
	private double attackKillValue; // 必杀攻击值
	private double currentAttackValue; // 当前攻击值
	private double defendValue; // 防御值
	private double currentDefendValue; // 当前防御值
	
	// 初始化
	public Entity(String name) {
		imageMap = new HashMap<>();
		textMap = new HashMap<>();
		frameMap = new HashMap<>();
		
		deltaX = 0;
		deltaY = 0;
		frameCount = 0;
		
		lifeValue = 100;
		moveSpeed = 1;
		runSpeed = 2;
		jumpSpeed = 1;
		jumpHeight = 20;
		attackNearValue = 10;
		attackFarValue = 15;
		attackKillValue = 25;
		currentAttackValue = 0;
		defendValue = 5;
		currentDefendValue = 0;
		
		setName(name);
		setState(EntityState.STANDING_TORIGHT);
		setMobile(false);
		setActive(true);
		setAttackable(false);
		
		initFrame();
	}
	
	public void initFrame() { // 初始化帧计数
		for(EntityState state : EntityState.values()){
			switch (state) {
			case ATTACKING_NEAR_TOLEFT:
				addFrame(state, 30);
				break;
			case ATTACKING_NEAR_TORIGHT:
				addFrame(state, 30);
				break;
			case ATTACKING_FAR_TOLEFT:
				addFrame(state, 40);
				break;
			case ATTACKING_FAR_TORIGHT:
				addFrame(state, 40);
				break;
			case ATTACKING_KILL_TOLEFT:
				addFrame(state, 60);
				break;
			case ATTACKING_KILL_TORIGHT:
				addFrame(state, 60);
				break;
			case JUMPING_TOLEFT:
				addFrame(state, (int)(2*jumpHeight / jumpSpeed));
				break;
			case JUMPING_TORIGHT:
				addFrame(state, (int)(2*jumpHeight / jumpSpeed));
				break;
			default:
				addFrame(state, 20);
				break;
			}
		}
	}
	
	// Getter
	public String getName() { // 获取名称
		return name;
	}
	
	
	public EntityState getState() { // 获取当前状态
		return state;
	}
	
	
	public boolean isMobile() { // 判断实体是否可移动
		return this.isMobile;
	}
	
	
	public boolean isActive() { // 判断实体是否活跃
		return this.isActive;
	}
	
	
	public boolean isAttackable() { // 判断实体是否具有攻击性
		return isAttackable;
	}
	
	public boolean isStanding() { // 判断实体是否处于站着的状态
		// 只有处于站着的状态才能响应用户下一个操作
		return (state == EntityState.STANDING_TOLEFT || 
				state == EntityState.STANDING_TORIGHT || 
				state == EntityState.STANDING_FORWARD);
	}
	
	public boolean isLeft() { // 判断当前朝向是否是朝左
		return isLeft;
	}
	
	
	public Image getImage(EntityState state) { // 获取图片
		return imageMap.get(state);
	}
	
	
	public String getText(EntityState state) { // 获取文本
		return textMap.get(state);
	}
	
	public int getFrame(EntityState state) { // 获取帧计数
		return frameMap.get(state);
	}
	
	public double getDeltaX() { // 获取x轴位移
		return deltaX;
	}
	
	
	public double getDeltaY() { // 获取y轴位移
		return deltaY;
	}
	
	
	
	public Image getCurrentImage() { // 获取当前状态图片
	
		return imageMap.get(state);
	}
	
	public double getCurrentAttackvalue() { // 获取当前攻击值(用于碰撞回调)
		if(isAttackable()) {
			return currentAttackValue;
		}
		else {
			return 0;
		}
		
	}
	
	public int getJumpTag() {
		return jumpTag;
	}
	
	// Setter
	public void setName(String name) { // 设置名称
		this.name = name;
	}
	
	
	public void setState(EntityState state) { // 设置实体状态
		this.state = state;
		if(state == EntityState.MOVING_TOLEFT) { // 只有左向移动能切换朝向
			isLeft = true;
		}
		else if (state == EntityState.MOVING_TORIGHT) { // 只有右向移动能切换朝向
			isLeft = false;
		}
	}
	
	
	public void setMobile(boolean m) { // 设置实体是否可移动
		isMobile = m;
	}
	
	
	public void setActive(boolean a) { // 设置实体是否活跃
		isActive = a;
	}
	
	
	public void setAttackable(boolean a) { // 设置实体是否具有攻击性
		isAttackable = a;
	}
	
	
	public void addImage(EntityState state, Image img) { // 添加状态图片
		if(img != null) {
			imageMap.put(state, img);
		}
	}
	
	
	public void addText(EntityState state, String text) { // 添加状态文本
		if(text != null) {
			textMap.put(state, text);
		}
	}
		
	
	// 状态切换

	public void addFrame(EntityState state, int frm) { // 添加帧计数
		frameMap.put(state, frm);
	}
	
	public void setLifeValue(double val) { // 设置生命值
		lifeValue = val;
	}
	
	public void setMoveSpeed(double val) { // 设置移动速度
		moveSpeed = val;
	}
	
	public void setRunSpeed(double val) { // 设置冲刺速度
		runSpeed = val;
	}
	
	public void setJumpSpeed(double val) { // 设置跳跃速度
		jumpSpeed = val;
		addFrame(EntityState.JUMPING_TOLEFT, (int)(2*jumpHeight / jumpSpeed));
		addFrame(EntityState.JUMPING_TORIGHT, (int)(2*jumpHeight / jumpSpeed));
	}
	
	public void setJumpHeight(double val) { // 设置跳跃高度
		jumpHeight = val;
		addFrame(EntityState.JUMPING_TOLEFT, (int)(2*jumpHeight / jumpSpeed));
		addFrame(EntityState.JUMPING_TORIGHT, (int)(2*jumpHeight / jumpSpeed));
	}
	
	public void setAttackNearValue(double val) { // 设置近攻攻击值
		attackNearValue = val;
	}
	
	public void setAttackFarValue(double val) { // 设置远攻攻击值
		attackFarValue = val;
	}
	
	public void setAttackKillValue(Double val) { // 设置必杀攻击值
		attackKillValue = val;
	}
	
	public void setDefendValue(double val) { // 设置防御值
		defendValue = val;
	}
	
	public void countFrame(EntityState state) { // 帧计数，并自动回退到静止状态
		frameCount++;
		if(frameCount >= frameMap.get(state)) {
			frameCount = 0;
			resetToStand();
		}
	}
	
	// 状态切换
	public void resetToStand() { // 返回站立的静止状态(只有在这些状态下才能响应用户操作)
		currentAttackValue = 0;
		currentDefendValue = 0;
		jumpTag = 0;
		if(isLeft) {
			setState(EntityState.STANDING_TOLEFT);
		}
		else {
			setState(EntityState.STANDING_TORIGHT);
		}
	}
	
	public void moveRight() { // 向右移动
		if(isActive() && isMobile()) {
			deltaX += moveSpeed;
			countFrame(EntityState.MOVING_TORIGHT);
		}
	}
	
	
	public void moveLeft() { // 向左移动
		if(isActive() && isMobile()) {
			deltaX -= moveSpeed;
			countFrame(EntityState.MOVING_TOLEFT);
		}
	}
	
	public void runLeft() { // 向左冲刺
		if(isActive() && isMobile()) {
			deltaX -= runSpeed;
			countFrame(EntityState.RUNNING_TOLEFT);
		}
	}
	
	public void runRight() { // 向右冲刺
		if(isActive() && isMobile()) {
			deltaX += runSpeed;
			countFrame(EntityState.RUNNING_TORIGHT);
		}
	}
	
	
	public void moveUp() { // 向上移动
		if(isActive() && isMobile()) {
			deltaY -= jumpSpeed;
			if(isLeft) {
				countFrame(EntityState.JUMPING_TOLEFT);
			}
			else {
				countFrame(EntityState.JUMPING_TORIGHT);
			}
			
		}
	}
	
	
	public void moveDown() { // 向下移动
		if(isActive() && isMobile()) {
			deltaY += jumpSpeed;
			if(isLeft) {
				countFrame(EntityState.JUMPING_TOLEFT);
			}
			else {
				countFrame(EntityState.JUMPING_TORIGHT);
			}
		}
	}
		

	
	public void defend() { // 防御
		currentDefendValue = defendValue;
		if(isLeft) {
			countFrame(EntityState.DEFENDING_TOLEFT);
		}
		else {
			countFrame(EntityState.DEFENDING_TORIGHT);
		}
	}
	
	
	public void attackNear() { // 近攻
		currentAttackValue = attackNearValue;
		if(isLeft) {
			countFrame(EntityState.ATTACKING_NEAR_TOLEFT);
		}
		else {
			countFrame(EntityState.ATTACKING_NEAR_TORIGHT);
		}
	}
	
	
	public void attackFar() { // 远攻
		currentAttackValue = attackFarValue;
		if(isLeft) {
			countFrame(EntityState.ATTACKING_FAR_TOLEFT);
		}
		else {
			countFrame(EntityState.ATTACKING_FAR_TORIGHT);
		}
	}
	
	
	public void attackKill() { // 必杀
		currentAttackValue = attackKillValue;
		if(isLeft) {
			countFrame(EntityState.ATTACKING_KILL_TOLEFT);
		}
		else {
			countFrame(EntityState.ATTACKING_KILL_TORIGHT);
		}
	}
	
	// 动作
	
	public void collided(double attackValue) { // 被其他实体碰撞
		boolean isHurt = getHurt(attackValue);
		if(isHurt) {
			
		}
	}
	
	public void jump() { // 跳跃
		
		if(jumpTag == 0) { // 尚未起跳
			moveUp();
			jumpTag = 1;
		}
		else if (jumpTag == 1) { // 正在上升
			if(deltaY <= -jumpHeight) { // 达到最大高度
				deltaY = -jumpHeight;
				jumpTag = 2;
				if(isLeft) {
					countFrame(EntityState.JUMPING_TOLEFT);
				}
				else {
					countFrame(EntityState.JUMPING_TORIGHT);
				}
			}
			else {
				moveUp();
			}
		}
		else if(jumpTag == 2) { // 正在下落
			if(deltaY >= 0) { // 落到地面
				deltaY = 0;
				jumpTag = 3;
				if(isLeft) {
					countFrame(EntityState.JUMPING_TOLEFT);
				}
				else {
					countFrame(EntityState.JUMPING_TORIGHT);
				}
			}
			else {
				moveDown();
			}
		}
		else if(jumpTag == 3) { // 已经落地，但帧数没有计数完(正常情况下应该不会发生)
			if(isLeft) {
				countFrame(EntityState.JUMPING_TOLEFT);
			}
			else {
				countFrame(EntityState.JUMPING_TORIGHT);
			}
		}
	}
	
	public boolean getHurt(double attackValue) { // 计算伤害，并返回是否受伤
		double hurt = attackValue - currentDefendValue;
		if(hurt > 0) {
			if(hurt > lifeValue) {
				lifeValue  = 0;
				setActive(false);
			}
			else {
				lifeValue -= hurt;
			}
			return true;
		}
		return false;
	}
	
	
}