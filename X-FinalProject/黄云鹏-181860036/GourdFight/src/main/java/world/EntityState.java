package world;

import framework.Constants;

public enum EntityState { // 实体状态枚举类，用于判断实体当前的状态，以便调用相应的图片(序列)进行渲染
	
	STANDING_FORWARD(Constants.STANDING_FORWARD), // 朝正面站着
	STANDING_TOLEFT(Constants.STANDING_TOLEFT),// 朝左边站着
	STANDING_TORIGHT(Constants.STANDING_TORIGHT), // 朝右边站着
	
	MOVING_TOLEFT(Constants.MOVING_TOLEFT),// 朝左边移动
	MOVING_TORIGHT(Constants.MOVING_TORIGHT), // 朝右边移动
	
	RUNNING_TOLEFT(Constants.RUNNING_TOLEFT),// 朝左边冲刺
	RUNNING_TORIGHT(Constants.RUNNING_TORIGHT), // 朝右边冲刺
	
	JUMPING_TOLEFT(Constants.JUMPING_TOLEFT), // 朝左边跳跃
	JUMPING_TORIGHT(Constants.JUMPING_TORIGHT), // 朝右边跳跃
	LYING_TOLEFT(Constants.LYING_TOLEFT), // 朝左边倒地
	LYING_TORIGHT(Constants.LYING_TORIGHT), // 朝右边倒地
	DEFENDING_TOLEFT(Constants.DEFENDING_TOLEFT), // 朝左边防御
	DEFENDING_TORIGHT(Constants.DEFENDING_TORIGHT), // 朝右边防御
	ATTACKING_NEAR_TOLEFT(Constants.ATTACKING_NEAR_TOLEFT), // 朝左边近攻
	ATTACKING_NEAR_TORIGHT(Constants.ATTACKING_NEAR_TORIGHT), // 朝右边近攻
	ATTACKING_FAR_TOLEFT(Constants.ATTACKING_FAR_TOLEFT), // 朝左边远攻
	ATTACKING_FAR_TORIGHT(Constants.ATTACKING_FAR_TORIGHT), // 朝右边远攻
	ATTACKING_KILL_TOLEFT(Constants.ATTACKING_KILL_TOLEFT), // 朝左边必杀
	ATTACKING_KILL_TORIGHT(Constants.ATTACKING_KILL_TORIGHT), // 朝右边必杀
	
	WINNER(Constants.WINNER), // 游戏胜利
	LOSER(Constants.LOSER) // 游戏失败
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
	
	public static EntityState find(String str) { // 根据状态原始值查找状态枚举量
		for(EntityState s:EntityState.values()) {
			if (s.getState().equals(str)) {
				return s;
			}
		}
		return null;
	}
	
}
