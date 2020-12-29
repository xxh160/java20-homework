package world;

import gourdfight.Constants;

public enum EntityState { // 实体状态枚举类，用于判断实体当前的状态，以便调用相应的图片(序列)进行渲染
	
	STANDING_FORWARD(Constants.STANDING_FORWARD), // 朝正面站着
	STANDING_TOLEFT(Constants.STANDING_TOLEFT),// 朝左边站着
	STANDING_TORIGHT(Constants.STANDING_TORIGHT), // 朝右边站着
	
	MOVING_TOLEFT(Constants.MOVING_TOLEFT),// 朝左边移动
	MOVING_TORIGHT(Constants.MOVING_TORIGHT), // 朝右边移动
	
	RUNNING_TOLEFT(Constants.RUNNING_TOLEFT),// 朝左边冲刺
	RUNNING_TORIGHT(Constants.RUNNING_TORIGHT), // 朝右边冲刺
	
	JUMPING(Constants.JUMPING), // 跳跃
	LYING(Constants.LYING), // 倒地
	WOUNDED(Constants.WOUNDED), // 受伤
	DEFENDING(Constants.DEFENDING), // 防御
	ATTACKING_NEAR(Constants.ATTACKING_NEAR), // 近攻
	ATTACKING_FAR(Constants.ATTACKING_FAR), // 远攻
	ATTACKING_KILL(Constants.ATTACKING_KILL), // 必杀
	;
	
	private String state;
	
	// 初始化
	private EntityState(String state) {
		this.state = state;
	}
	
	// Getter
	public String getState() { // 获取状态原始值
		return state;
	}
}
