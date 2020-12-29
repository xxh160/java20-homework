package world;

import java.util.HashMap;

import app.ImageSet;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.image.Image;

public class Entity { // 游戏实体类，所有游戏角色、道具等的父类
	
	private String name; // 实体名称
	private EntityState state; // 实体状态(默认"朝左边站着")
	
	private boolean isMobile; // 是否可移动(默认不可移动)
	private boolean isActive; // 是否活跃(默认活跃)
	private boolean isAttackable; // 是否具有攻击性(用于碰撞检测, 默认不具有攻击性)
	private boolean isLeft; // 朝向左边(false 则朝向右边, 默认朝向左边)
	
	private double deltaX; // x轴位移
	private double deltaY; // y轴位移
	
	private HashMap<String,Image> imageMap; // 实体图片字典
	private HashMap<String,String> textMap; // 实体文本字典 
	private HashMap<EntityState,ImageSet> imgSetMap; // 实体动画序列字典
	
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
		imgSetMap = new HashMap<>();
		
		deltaX = 0;
		deltaY = 0;
		
		lifeValue = 100;
		moveSpeed = 5;
		runSpeed = 20;
		jumpSpeed = 5;
		jumpHeight = 20;
		attackNearValue = 10;
		attackFarValue = 15;
		attackKillValue = 25;
		currentAttackValue = 0;
		defendValue = 5;
		currentDefendValue = 0;
		
		setName(name);
		setState(EntityState.STANDING_TOLEFT);
		setMobile(false);
		setActive(true);
		setAttackable(false);
	}
	
	// Getter
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
	
	
	public Image getImage(String id) { // 获取图片
		return imageMap.get(id);
	}
	
	
	public String getText(String id) { // 获取文本
		return textMap.get(id);
	}
	
	
	public double getDeltaX() { // 获取x轴位移
		return deltaX;
	}
	
	
	public double getDeltaY() { // 获取y轴位移
		return deltaY;
	}
	
	
	public Image getCurrentImage() { // 获取当前帧图片
		if(imgSetMap.containsKey(state))
			return imgSetMap.get(state).getCurrentImage(isLeft);
		return null;
	}
	
	// Setter
	
	// Setter
	public void setName(String name) { // 设置名称
		this.name = name;
	}
	
	
	public void setState(EntityState state) { // 设置实体状态
		this.state = state;
		if(state == EntityState.MOVING_TOLEFT || state == EntityState.RUNNING_TOLEFT) {
			isLeft = true;
		}
		else if (state == EntityState.MOVING_TORIGHT || state == EntityState.RUNNING_TORIGHT) {
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
	
	
	public void addImage(String id, Image img) { // 添加图片
		if(img != null && id != null) {
			imageMap.put(id, img);
		}
	}
	
	
	public void addText(String id, String text) { // 添加文本
		if(text != null && id != null) {
			textMap.put(id, text);
		}
	}
	
	
	public void addImageSet(EntityState state, ImageSet imgSet) { // 添加状态state的动画序列
		imgSetMap.put(state, imgSet);
	}
	
	// 状态切换
	
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
	}
	
	public void setJumpHeight(double val) { // 设置跳跃高度
		jumpHeight = val;
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
	
	// 状态切换
	public void resetToStand() { // 返回站立的静止状态(只有在这些状态下才能响应用户操作)
		if(isLeft) {
			state = EntityState.STANDING_TOLEFT;
		}
		else {
			state = EntityState.STANDING_TORIGHT;
		}
	}
	
	public void moveRight() { // 向右移动
		if(isActive() && isMobile()) {
			deltaX += moveSpeed;
			setState(EntityState.MOVING_TORIGHT);
		}
	}
	
	
	public void moveLeft() { // 向左移动
		if(isActive() && isMobile()) {
			deltaX -= moveSpeed;
			setState(EntityState.MOVING_TOLEFT);
		}
	}
	
	
	public void moveUp() { // 向上移动
		if(isActive() && isMobile()) {
			deltaY -= jumpSpeed;
			setState(EntityState.JUMPING);
		}
	}
	
	
	public void moveDown() { // 向下移动
		if(isActive() && isMobile()) {
			deltaY += jumpSpeed;
			setState(EntityState.JUMPING);
		}
	}
		

	
	public void defend() { // 防御
		currentDefendValue = defendValue;
		setState(EntityState.DEFENDING);
	}
	
	
	public void attackNear() { // 近攻
		currentAttackValue = attackNearValue;
		setState(EntityState.ATTACKING_NEAR);
	}
	
	
	public void attackFar() { // 远攻
		currentAttackValue = attackFarValue;
		setState(EntityState.ATTACKING_FAR);
	}
	
	
	public void attackKill() { // 必杀
		currentAttackValue = attackKillValue;
		setState(EntityState.ATTACKING_KILL);
	}
	
	// 动作
	
	public void collide(double attackValue) { // 与实体other碰撞
		boolean isHurt = getHurt(attackValue);
		if(isHurt) {
			state = EntityState.WOUNDED;
		}
	}
	
	public void jump() { // 跳跃
		
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
