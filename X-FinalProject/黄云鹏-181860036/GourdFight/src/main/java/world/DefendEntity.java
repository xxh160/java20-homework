package world;

public class DefendEntity extends Entity { // 防御实体，用于在角色实体防御产生的临时实体

	private int endFrame; // 当攻击实体达到最大距离之后，若还没有因碰撞而消亡，则会驻留在最大距离处持续endFrame帧时
	private int endFrameCount; // endFrame的计数器
	
	public DefendEntity(String name,boolean l,double dx) {
		super(name);
		isLeft = l;
		deltaX = dx;
		setMobile(true);
		setDefendable(true); // 具有防御性
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
	public void moveLeft() { // 防御实体左移
		if(isActive() && isMobile()) {
			deltaX -= defendSpeed;
		}
	}
	
	@Override
	public void moveRight() { // 防御实体右移
		if(isActive() && isMobile()) {
			deltaX += defendSpeed;
		}
	}
	
	@Override
	public void setDefendName(String name) {
		super.setDefendName(name);
		setName(name);
	}
	
	@Override
	public void setDefendValue(double val) {
		super.setDefendValue(val);
		setLifeValue(val); // 防御实体的生命值即是其防御值，因碰撞而被消耗后死亡
	}
	
	@Override
	public void setDefendWidth(double val) {
		super.setDefendWidth(val);
		setWidth(val);
	}
	
	@Override
	public void setDefendHeight(double val) {
		super.setDefendHeight(val);
		setHeight(val);
	}
	
}
