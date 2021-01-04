package world;

import framework.Constants;

public class Chilopod extends Entity { // 蜈蚣精实体类

	public Chilopod(String name) {
		super(name);
		// 子类特殊设置
		setMobile(true);
//		setActive(true);
//		setAttackable(false);
//		setDefendable(false);
//		
		setWidth(Constants. PLAYER_DEFAULT_W + 20);
		setHeight(Constants. PLAYER_DEFAULT_H);
//		
		setLifeValue(Constants.DEFAULT_LIFE_VALUE);
		setMoveSpeed(Constants.DEFAULT_MOVE_SPEED);
		setRunSpeed(Constants.DEFAULT_RUN_SPEED);
		setJumpSpeed(Constants.DEFAULT_JUMP_SPEED);
		setJumpHeight(Constants.DEFAULT_JUMP_HEIGHT);
//		
		setAttackNearValue(Constants.DEFAULT_ATTACK_NEAR_VALUE);
		setAttackNearDist(Constants.DEFAULT_ATTACK_NEAR_DIST);
		setAttackNearSpeed(Constants.DEFAULT_ATTACK_NEAR_SPEED);

		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 20);
		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 20);
//		
		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE+1);
		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST+10);
		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED-1);

		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 20);
		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 40);
//		
		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE);
		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST);
		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED);

		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 80);
		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 40);
//		
		setDefendValue(Constants.DEFAULT_DEFEND_VALUE-1);
		setDefendDist(Constants.DEFAULT_DEFEND_DIST+5);
		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED);

		setDefendWidth(Constants.PLAYER_DEFAULT_DEFEND_W + 20);
		setDefendHeight(Constants.PLAYER_DEFAULT_DEFEND_H + 80);	
		// 设置招式名称
		setAttackNearName(Constants.CHILOPOD_ATTACKNEAR_NAME);
		setAttackFarName(Constants.CHILOPOD_ATTACKFAR_NAME);
		setAttackKillName(Constants.CHILOPOD_ATTACKKILL_NAME);
		setDefendName(Constants.CHILOPOD_DEFEND_NAME);
	}

}
