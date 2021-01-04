package world;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.27
 * @inherit Entity
 * @functions 所有攻击元素的父类，提供攻击元素如何移动、如何消失等方法
 * @properties endFrameCount
 * @methods  	
 * 		Setter: 设置攻击元素的各种参数
 * 		moveLetft/moveRight: 攻击元素移动方法
 * 		countEndFrame: 计数末尾帧时
 */

public class AttackEntity extends Entity { // 攻击实体，用于在角色实体攻击时产生的临时实体
	
	private int endFrame; // 当攻击实体达到最大距离之后，若还没有因碰撞而消亡，则会驻留在最大距离处持续endFrame帧时
	private int endFrameCount; // endFrame的计数器
	private boolean isCounting; // 判断是否正在倒计时
	
	// 初始化
	public AttackEntity(String name,boolean l,double dx) {
		super(name);
		isLeft = l;
		deltaX = dx;
		setMobile(true);
		setAttackable(true); // 具有可攻击性
	
		endFrame = 5;
		endFrameCount = 0;
		isCounting = false;
	}
	
	// 动作
	public boolean countEndFrame() { // 计数endFrame
		endFrameCount++;
		isCounting = true;
		if(endFrameCount >= endFrame) { // 死亡，并复位计数器
			setActive(false);
			endFrameCount = 0;
			isCounting = false;
			return true;
		}
		return false;
	}
	
	@Override
	public void moveLeft() { // 攻击实体左移
		if(isActive() && isMobile()) {
			deltaX -= currentAttackSpeed;
		}
	}
	
	@Override
	public void moveRight() { // 攻击实体右移
		if(isActive() && isMobile()) {
			deltaX += currentAttackSpeed;
		}
	}
	
	// Getter
	public boolean isCounting() { // 判断是否正在倒计时
		return isCounting;
	}
	
	// Setter
	@Override
	public void setCurrentAttackName(String name) {
		super.setCurrentAttackName(name);
		setName(name);
	}
	
	@Override
	public void setCurrentAttackValue(double val) {
		super.setCurrentAttackValue(val);
		setLifeValue(val); // 攻击实体的生命值即是其攻击值，因碰撞而被消耗后死亡
	}
	
	@Override
	public void setCurrentAttackWidth(double val) {
		super.setCurrentAttackWidth(val);
		setWidth(val);
	}
	
	@Override
	public void setCurrentAttackHeight(double val) {
		super.setCurrentAttackHeight(val);
		setHeight(val);
	}
	
}
