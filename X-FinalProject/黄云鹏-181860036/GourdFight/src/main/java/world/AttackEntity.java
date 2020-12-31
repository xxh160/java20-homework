package world;

public class AttackEntity extends Entity { // 攻击实体，用于在角色实体攻击时产生的临时实体

	private int endFrame; // 当攻击实体达到最大距离之后，若还没有因碰撞而消亡，则会驻留在最大距离处持续endFrame帧时
	private int endFrameCount; // endFrame的计数器
	
	public AttackEntity(String name,boolean l,double dx) {
		super(name);
		isLeft = l;
		deltaX = dx;
		setMobile(true);
		setAttackable(true); // 具有可攻击性
		endFrame = 10;
		endFrameCount = 0;
	}
	 
	public void countEndFrame() { // 计数endFrame
		endFrameCount++;
		if(endFrameCount >= endFrame) {
			setActive(false);
			endFrameCount = 0;
		}
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
