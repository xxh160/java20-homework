package world;

import gourdfight.Constants;

public class Snake extends Entity { // 蛇精实体类

	public Snake(String name) {
		super(name);
		// 子类特殊设置
		setMobile(true);
//		setActive(true);
//		setAttackable(false);
//		setDefendable(false);
//		
		setWidth(Constants. PLAYER_DEFAULT_W + 70);
		setHeight(Constants. PLAYER_DEFAULT_H + 70);
//		
//		setLifeValue(Constants.DEFAULT_LIFE_VALUE);
//		setMoveSpeed(Constants.DEFAULT_MOVE_SPEED);
//		setRunSpeed(Constants.DEFAULT_RUN_SPEED);
//		setJumpSpeed(Constants.DEFAULT_JUMP_SPEED);
//		setJumpHeight(Constants.DEFAULT_JUMP_HEIGHT);
//		
//		setAttackNearValue(Constants.DEFAULT_ATTACK_NEAR_VALUE);
//		setAttackNearDist(Constants.DEFAULT_ATTACK_NEAR_DIST);
//		setAttackNearSpeed(Constants.DEFAULT_ATTACK_NEAR_SPEED);
		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 70);
		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H - 30);
//		
//		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE);
//		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST);
//		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED);
		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 90);
		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H - 10);
//		
//		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE);
//		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST);
//		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED);
		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 140);
		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 110);
//		
//		setDefendValue(Constants.DEFAULT_DEFEND_VALUE);
//		setDefendDist(Constants.DEFAULT_DEFEND_DIST);
//		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED);
		setDefendWidth(Constants.PLAYER_DEFAULT_DEFEND_W + 30);
		setDefendHeight(Constants.PLAYER_DEFAULT_DEFEND_H + 100);		
		// 设置招式名称
		setAttackNearName(Constants.SNAKE_ATTACKNEAR_NAME);
		setAttackFarName(Constants.SNAKE_ATTACKFAR_NAME);
		setAttackKillName(Constants.SNAKE_ATTACKKILL_NAME);
		setDefendName(Constants.SNAKE_DEFEND_NAME);
	}
	
	@Override
	public double getDeltaY() {
		return super.getDeltaY() - 70;
	}

}
