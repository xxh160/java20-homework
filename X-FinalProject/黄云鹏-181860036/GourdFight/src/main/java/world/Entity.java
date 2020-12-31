package world;

import java.util.HashMap;
import gourdfight.Constants;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.image.Image;

public class Entity { // 游戏实体类，所有游戏角色、道具等的父类
	
	private String name; // 实体名称
	private EntityState state; // 实体状态(默认"朝右边站着")
	
	private boolean isMobile; // 是否可移动(默认不可移动)
	private boolean isActive; // 是否活跃(默认活跃)
	private boolean isAttackable; // 是否具有攻击性(用于碰撞检测, 默认不具有攻击性)
	private boolean isDefendable; // 是否具有防御性(用于碰撞检测，默认不具有防御性)
	protected boolean isLeft; // 朝向左边(false 则朝向右边, 默认朝向左边)
	private int jumpTag; // 跳跃标记(0:没有起跳,1:正在上升,2:正在下落,3:落地)
	
	private double width; // 实体宽度
	private double height; // 实体高度
	protected double deltaX; // x轴位移
	protected double deltaY; // y轴位移
	private int frameCount; // 状态帧计数器
	
	private HashMap<EntityState,Image> imageMap; // 实体图片字典
	private HashMap<EntityState,String> textMap; // 实体文本字典 
	private HashMap<EntityState, Integer> frameMap; // 每个状态持续的帧数
	
	private double lifeValue; // 生命值
	
	private double moveSpeed; // 移动初速度
	private double moveAcceleration; // 移动加速度
	private double runSpeed; // 冲刺初速度
	private double runAcceleration; // 冲刺加速度
	
	private double jumpSpeed; // 跳跃初速度
	private double jumpHeight; // 跳跃高度
	private double jumpAcceleration; // 重力加速度
	
	private String attackNearName; // 近攻招式名称
	private Image attackNearLeftImg; // 近攻实体图片(朝左)
	private Image attackNearRightImg; // 近攻实体图片(朝右)
	private double attackNearValue; // 近攻攻击值
	private double attackNearDist; // 近攻距离
	private double attackNearSpeed; // 近攻实体移动速度
	private double attackNearWidth; // 近攻实体宽度
	private double attackNearHeight; // 近攻实体高度
	
	private String attackFarName; // 远攻招式名称
	private Image attackFarLeftImg; // 远攻实体图片(朝左)
	private Image attackFarRightImg; // 远攻实体图片(朝右)
	private double attackFarValue; // 远攻攻击值
	private double attackFarDist; // 远攻距离
	private double attackFarSpeed; // 远攻实体移动速度
	private double attackFarWidth; // 远攻实体宽度
	private double attackFarHeight; // 远攻实体高度
	
	private String attackKillName; // 必杀招式名称
	private Image attackKillLeftImg; // 必杀实体图片(朝左)
	private Image attackKillRightImg; // 必杀实体图片(朝右)
	private double attackKillValue; // 必杀攻击值
	private double attackKillDist; // 必杀距离
	private double attackKillSpeed; // 必杀实体移动速度
	private double attackKillWidth; // 必杀实体宽度
	private double attackKillHeight; // 必杀实体高度
	
	protected String currentAttackName; // 当前攻击招式名称
	protected Image currentAttackImg; // 当前攻击实体图片
	protected double currentAttackValue; // 当前攻击值
	protected double currentAttackDist; // 当前攻击距离
	protected double currentAttackSpeed; // 当前攻击实体移动速度
	protected double currentAttackWidth; // 当前攻击实体宽度
	protected double currentAttackHeight; // 当前攻击实体高度
	
	protected String defendName; // 防御招式名称
	protected Image defendLeftImg; // 防御实体图片(朝左)
	protected Image defendRightImg; // 防御实体图片(朝右)
	protected double defendValue; // 防御值
	protected double defendDist; // 防御距离
	protected double defendSpeed; // 防御实体移动速度
	protected double defendWidth; // 防御实体宽度
	protected double defendHeight; // 防御实体高度
	
	// 初始化
	public Entity(String name) {
		imageMap = new HashMap<>();
		textMap = new HashMap<>();
		frameMap = new HashMap<>();
		initFrame();
		
		setWidth(Constants.PLAYER1_INIT_W);
		setHeight(Constants.PLAYER1_INIT_H);
		
		deltaX = 0;
		deltaY = 0;
		frameCount = 0;
		
		setLifeValue(100);
		setMoveSpeed(5);
		setRunSpeed(12);
		setJumpSpeed(5);
		setJumpHeight(200);
		
		setAttackNearValue(10);
		setAttackNearDist(35);
		setAttackNearSpeed(2);
		setAttackNearWidth(Constants.PLAYER1_ATTACK_W);
		setAttackNearHeight(Constants.PLAYER1_ATTACK_H);
		
		setAttackFarValue(15);
		setAttackFarDist(250);
		setAttackFarSpeed(3);
		setAttackFarWidth(Constants.PLAYER1_ATTACK_W);
		setAttackFarHeight(Constants.PLAYER1_ATTACK_H);
		
		setAttackKillValue(25);
		setAttackKillDist(100);
		setAttackKillSpeed(2.5);
		setAttackKillWidth(Constants.PLAYER1_ATTACK_W);
		setAttackKillHeight(Constants.PLAYER1_ATTACK_H);
		
		setCurrentAttackValue(0);
		setCurrentAttackDist(0);
		setCurrentAttackSpeed(0);
		setCurrentAttackWidth(0);
		setCurrentAttackHeight(0);
		
		setDefendValue(5);
		setDefendDist(20);
		setDefendSpeed(3);
		setDefendWidth(Constants.PLAYER1_DEFEND_W);
		setDefendHeight(Constants.PLAYER1_DEFEND_H);		
		
		setName(name);
		setState(EntityState.STANDING_TORIGHT);
		setMobile(false);
		setActive(true);
		setAttackable(false);
		setDefendable(false);
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
	
	public String getAttackNearName() { // 获取近攻招式名称
		return attackNearName;
	}
	
	public String getAttackFarName() { // 获取远攻招式名称
		return attackFarName;
	}
	
	public String getAttackKillName() { // 获取必杀招式名称
		return attackKillName;
	}
	
	public String getDefendName() { // 获取防御招式名称
		return defendName;
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
	
	public boolean isDefendable() { // 判断实体是否具有防御性
		return isDefendable;
	}
	
	public boolean isStanding() { // 判断实体是否处于站着的状态
		// 只有处于站着的状态才能响应用户下一个操作
		return (state == EntityState.STANDING_TOLEFT || 
				state == EntityState.STANDING_TORIGHT || 
				state == EntityState.STANDING_FORWARD);
	}
	
	public boolean isAttacking() { // 判断实体是否处于攻击状态
		return 	(state == EntityState.ATTACKING_NEAR_TOLEFT) ||
				(state == EntityState.ATTACKING_NEAR_TORIGHT) ||
				(state == EntityState.ATTACKING_FAR_TOLEFT) ||
				(state == EntityState.ATTACKING_FAR_TORIGHT) ||
				(state == EntityState.ATTACKING_KILL_TOLEFT) ||
				(state == EntityState.ATTACKING_KILL_TORIGHT);
	}
	
	public boolean isDefending() { // 判断实体是否处于防御状态
		return 	(state == EntityState.DEFENDING_TOLEFT) ||
				(state == EntityState.DEFENDING_TORIGHT);
	}
	
	public boolean isLeft() { // 判断当前朝向是否是朝左
		return isLeft;
	}
	
	public double getLifeValue() { // 获取当前生命值
		return lifeValue;
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
	
	
	
	public double getWidth() { // 获取实体宽度
		return width;
	}
	
	public double getHeight() { // 获取实体高度
		return height;
	}
	
	public Image getCurrentImage() { // 获取当前状态图片
	
		return imageMap.get(state);
	}
	
	public String getCurrentAttackName() { // 获取当前攻击招式名称
		return currentAttackName;
	}
	
	public Image getCurrentAttackImg() { // 获取当前攻击实体图片
		return currentAttackImg;
	}
	
	public double getCurrentAttackValue() { // 获取当前攻击值(用于碰撞回调)
		return currentAttackValue;
	}
	
	public double getCurrentAttackDist() { // 获取当前攻击距离
		return currentAttackDist;
	}
	
	public double getCurrentAttackSpeed() { // 获取当前攻击实体移动速度
		return currentAttackSpeed;
	}
	
	public double getCurrentAttackWidth() { // 获取当前攻击实体宽度
		return currentAttackWidth;
	}
	
	public double getCurrentAttackHeight() { // 获取当前攻击实体高度
		return currentAttackHeight;
	}
	
	public Image getCurrentDefendImg() { // 获取当前防御实体图片
		if(isLeft) {
			return defendLeftImg;
		}else {
			return defendRightImg;
		}
	}
	
	public double getDefendValue() { // 获取当前防御值(用于碰撞回调)
		return defendValue;
	}
	
	public double getDefendDist() { // 获取当前防御距离
		return defendDist;
	}
	
	public double getDefendSpeed() { // 获取当前防御实体移动速度
		return defendSpeed;
	}
	
	public Image getDefendLeftImage() { // 获取防御实体图片(朝左)
		return defendLeftImg;
	}
	
	public Image getDefendRightImage() { // 获取防御实体图片(朝右)
		return defendRightImg;
	}
	
	public double getDefendWidth() { // 获取当前防御实体宽度
		return defendWidth;
	}
	
	public double getDefendHeight() { // 获取当前防御实体高度
		return defendHeight;
	}
	
	// Setter
	public void setName(String name) { // 设置名称
		this.name = name;
	}

	public void setWidth(double val) { // 设置实体宽度
		width = val;
	}
	
	public void setHeight(double val) { // 设置实体高度
		height = val;
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
	
	public void setDirection(boolean d) { // 设置朝向
		isLeft = d;
	}
	
	
	public void setAttackable(boolean a) { // 设置实体是否具有攻击性
		isAttackable = a;
	}
	
	public void setDefendable(boolean d) { // 设置实体是否具有防御性
		isDefendable = d;
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
	
	public void setMoveSpeed(double val) { // 设置移动初速度
		moveSpeed = val; // 设置初速度
		moveAcceleration = moveSpeed / frameMap.get(EntityState.MOVING_TOLEFT); // 设置移动加速度
	}
	
	public void setRunSpeed(double val) { // 设置冲刺初速度
		runSpeed = val;
		runAcceleration = runSpeed / frameMap.get(EntityState.RUNNING_TOLEFT); // 设置冲刺加速度
	}
	
	public void setJumpSpeed(double val) { // 设置跳跃初速度
		jumpSpeed = val; // 设置初速度
		addFrame(EntityState.JUMPING_TOLEFT, (int)(2*jumpHeight / jumpSpeed)); // 设置帧时
		addFrame(EntityState.JUMPING_TORIGHT, (int)(2*jumpHeight / jumpSpeed)); // 设置帧时
		jumpAcceleration = 2*jumpSpeed / frameMap.get(EntityState.JUMPING_TOLEFT); // 设置重力加速度
	}
	
	public void setJumpHeight(double val) { // 设置跳跃高度
		jumpHeight = val; // 设置跳跃高度
		addFrame(EntityState.JUMPING_TOLEFT, (int)(2*jumpHeight / jumpSpeed)); // 设置帧时
		addFrame(EntityState.JUMPING_TORIGHT, (int)(2*jumpHeight / jumpSpeed)); // 设置帧时
		jumpAcceleration = 2*jumpSpeed / frameMap.get(EntityState.JUMPING_TOLEFT); // 设置重力加速度
	}
	
	public void setAttackNearName(String name) { // 设置近攻招式名称
		attackNearName = name;
	}
	
	public void setAttackNearValue(double val) { // 设置近攻攻击值
		attackNearValue = val;
	}
	
	public void setAttackNearDist(double val) { // 设置近攻距离
		attackNearDist = val;
	}
	
	public void setAttackNearWidth(double val) { // 设置近攻实体宽度
		attackNearWidth = val;
	}
	
	public void setAttackNearHeight(double val) { // 设置近攻实体高度
		attackNearHeight = val;
	}
	
	public void setAttackNearSpeed(double val) { // 设置近攻实体移动速度
		attackNearSpeed = val;
	}

	public void setAttackNearImage(Image lImg,Image rImg) { // 设置近攻实体图片
		attackNearLeftImg = lImg;
		attackNearRightImg = rImg;
	}
	
	public void setAttackFarName(String name) { // 设置远攻招式名称
		attackFarName = name;
	}
	
	public void setAttackFarValue(double val) { // 设置远攻攻击值
		attackFarValue = val;
	}
	
	public void setAttackFarDist(double val) { // 设置远攻距离
		attackFarDist = val;
	}
	
	public void setAttackFarWidth(double val) { // 设置远攻实体宽度
		attackFarWidth = val;
	}
	
	public void setAttackFarHeight(double val) { // 设置远攻实体高度
		attackFarHeight = val;
	}
	
	public void setAttackFarSpeed(double val) { // 设置远攻实体移动速度
		attackFarSpeed = val;
	}
	
	public void setAttackFarImage(Image lImg, Image rImg) { // 设置远攻实体图片
		attackFarLeftImg = lImg;
		attackFarRightImg = rImg;
	}
	
	public void setAttackKillName(String name) { // 设置必杀招式名称
		attackKillName = name;
	}
	
	public void setAttackKillValue(double val) { // 设置必杀攻击值
		attackKillValue = val;
	}
	
	public void setAttackKillDist(double val) { // 设置必杀距离
		attackKillDist = val;
	}
	
	public void setAttackKillWidth(double val) { // 设置必杀实体宽度
		attackKillWidth = val;
	}
	
	public void setAttackKillHeight(double val) { // 设置必杀实体高度
		attackKillHeight = val;
	}
	
	public void setAttackKillSpeed(double val) { // 设置必杀实体移动速度
		attackKillSpeed = val;
	}
	
	public void setAttackKillImage(Image lImg, Image rImg) { // 设置必杀实体图片
		attackKillLeftImg = lImg;
		attackKillRightImg = rImg;
	}
	
	public void setCurrentAttackName(String name) { // 设置当前攻击招式名称
		currentAttackName = name;
	}
	
	public void setCurrentAttackImg(Image img) { // 设置当前攻击实体图片
		currentAttackImg = img;
	}
	
	public void setCurrentAttackValue(double val) { // 设置当前攻击值
		currentAttackValue = val;
	}
	
	public void setCurrentAttackDist(double val) { // 设置当前攻击距离
		currentAttackDist = val;
	}
	
	public void setCurrentAttackWidth(double val) { // 设置当前攻击实体宽度
		currentAttackWidth = val;
	}
	
	public void setCurrentAttackHeight(double val) { // 设置当前攻击实体高度
		currentAttackHeight = val;
	}
	
	public void setCurrentAttackSpeed(double val) { // 设置当前攻击实体移动速度
		currentAttackSpeed = val;
	}
	
	public void setDefendName(String name) { // 设置防御招式名称
		defendName = name;
	}
	
	public void setDefendValue(double val) { // 设置防御值
		defendValue = val;
	}
	
	public void setDefendDist(double val) { // 设置防御距离
		defendDist = val;
	}
	
	public void setDefendSpeed(double val) { // 设置防御实体移动速度
		defendSpeed = val;
	}
	
	public void setDefendWidth(double val) { // 设置防御实体宽度
		defendWidth = val;
	}
	
	public void setDefendHeight(double val) { // 设置防御实体高度
		defendHeight = val;
	}
	
	public void setDefendImg(Image lImg, Image rImg) { // 设置防御实体图片
		defendLeftImg = lImg;
		defendRightImg = rImg;
	}
	
	public void countFrame(EntityState state) { // 帧计数，并自动回退到静止状态
		frameCount++;
		if(frameCount >= frameMap.get(state)) {
			resetToStand();
		}
	}
	
	// 状态切换
	public void resetToStand() { // 返回站立的静止状态(只有在这些状态下才能响应用户操作)
		currentAttackValue = 0;
		jumpTag = 0;
		frameCount = 0;
		if(isLeft) {
			setState(EntityState.STANDING_TOLEFT);
		}
		else {
			setState(EntityState.STANDING_TORIGHT);
		}
	}
	
	public void moveRight() { // 向右移动(初速度方向为正方向)
		if(isActive() && isMobile()) {
			double v0 = moveSpeed - moveAcceleration * (frameCount-1); // 初速度
			double v = moveSpeed - moveAcceleration * frameCount; // 末速度
			double x = (v*v - v0*v0) / (2*-moveAcceleration); // 位移
			deltaX += x; // 真实移动距离
			countFrame(EntityState.MOVING_TORIGHT);
		}
	}
	
	
	public void moveLeft() { // 向左移动(初速度方向为正方向)
		if(isActive() && isMobile()) {
			double v0 = moveSpeed - moveAcceleration * (frameCount-1); // 初速度
			double v = moveSpeed - moveAcceleration * frameCount; // 末速度
			double x = (v*v - v0*v0) / (2*-moveAcceleration); // 位移
			deltaX -= x; // 真实移动距离
			countFrame(EntityState.MOVING_TOLEFT);
		}
	}
	
	public void runLeft() { // 向左冲刺
		if(isActive() && isMobile()) {
			double v0 = runSpeed - runAcceleration * (frameCount-1); // 初速度
			double v = runSpeed - runAcceleration * frameCount; // 末速度
			double x = (v*v - v0*v0) / (2*-runAcceleration); // 位移
			deltaX -= x; // 真实移动距离
			countFrame(EntityState.RUNNING_TOLEFT);
		}
	}
	
	public void runRight() { // 向右冲刺
		if(isActive() && isMobile()) {
			double v0 = runSpeed - runAcceleration * (frameCount-1); // 初速度
			double v = runSpeed - runAcceleration * frameCount; // 末速度
			double x = (v*v - v0*v0) / (2*-runAcceleration); // 位移
			deltaX += x; // 真实移动距离
			countFrame(EntityState.RUNNING_TORIGHT);
		}
	}
	
	
	public void moveUp() { // 向上移动(竖直向下为正方向)
		if(isActive() && isMobile()) {
			double v0 = -jumpSpeed; // 初速度
			double v = v0 + jumpAcceleration * frameCount; // 末速度
			
			deltaY = (v*v - v0*v0) / (2*jumpAcceleration); // 位移
			if(isLeft) {
				countFrame(EntityState.JUMPING_TOLEFT);
			}
			else {
				countFrame(EntityState.JUMPING_TORIGHT);
			}
			
		}
	}
	
	
	public void moveDown() { // 向下移动(竖直向下为正方向)
		if(isActive() && isMobile()) {
			double v0 = -jumpSpeed; // 初速度
			double v = v0 + jumpAcceleration * frameCount; // 末速度
			
			deltaY = (v*v - v0*v0) / (2*jumpAcceleration); // 位移
			if(isLeft) {
				countFrame(EntityState.JUMPING_TOLEFT);
			}
			else {
				countFrame(EntityState.JUMPING_TORIGHT);
			}
		}
	}
		

	
	public void defend() { // 防御
		if(isLeft) {
			countFrame(EntityState.DEFENDING_TOLEFT);
		}
		else {
			countFrame(EntityState.DEFENDING_TORIGHT);
		}
	}
	
	
	public void attackNear() { // 近攻
		setCurrentAttackName(attackNearName);
		if(isLeft) {
			setCurrentAttackImg(attackNearLeftImg);
		}else {
			setCurrentAttackImg(attackNearRightImg);
		}
		setCurrentAttackValue(attackNearValue);
		setCurrentAttackDist(attackNearDist);
		setCurrentAttackSpeed(attackNearSpeed);
		setCurrentAttackWidth(attackNearWidth);
		setCurrentAttackHeight(attackNearHeight);
		
		if(isLeft) {
			countFrame(EntityState.ATTACKING_NEAR_TOLEFT);
		}
		else {
			countFrame(EntityState.ATTACKING_NEAR_TORIGHT);
		}
	}
	
	
	public void attackFar() { // 远攻
		setCurrentAttackName(attackFarName);
		if(isLeft) {
			setCurrentAttackImg(attackFarLeftImg);
		}else {
			setCurrentAttackImg(attackFarRightImg);
		}
		setCurrentAttackValue(attackFarValue);
		setCurrentAttackDist(attackFarDist);
		setCurrentAttackSpeed(attackFarSpeed);
		setCurrentAttackWidth(attackFarWidth);
		setCurrentAttackHeight(attackFarHeight);
		
		if(isLeft) {
			countFrame(EntityState.ATTACKING_FAR_TOLEFT);
		}
		else {
			countFrame(EntityState.ATTACKING_FAR_TORIGHT);
		}
	}
	
	
	public void attackKill() { // 必杀
		setCurrentAttackName(attackKillName);
		if(isLeft) {
			setCurrentAttackImg(attackKillLeftImg);
		}else {
			setCurrentAttackImg(attackKillRightImg);
		}
		setCurrentAttackValue(attackKillValue);
		setCurrentAttackDist(attackKillDist);
		setCurrentAttackSpeed(attackKillSpeed);
		setCurrentAttackWidth(attackKillWidth);
		setCurrentAttackHeight(attackKillHeight);
		
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
		
		double hurt = 0;
		if (isDefendable()) { // 可防御
			hurt = attackValue - defendValue;
		}else { // 不可防御
			hurt = attackValue;
		}
		if(hurt > 0) { // 造成伤害
			if(hurt > lifeValue) {
				lifeValue  = 0;
				setActive(false); // 生命值减为零，要么处于倒地状态，要么直接消失
			}
			else {
				lifeValue -= hurt;
			}
			return true;
		}
		return false;
	}
	
	
}