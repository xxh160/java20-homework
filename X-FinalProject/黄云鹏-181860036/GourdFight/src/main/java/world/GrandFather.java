package world;

import framework.Constants;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.27
 * @inherit Entity
 * @functions 爷爷的实体类
 * @properties 全部继承自父类
 * @methods  全部继承自父类
 */

public class GrandFather extends Entity { // 爷爷实体类

	public GrandFather(String name) {
		super(name);
		// 子类特殊设置
		setMobile(true);
//		setActive(true);
//		setAttackable(false);
//		setDefendable(false);
//		
//		setWidth(Constants. PLAYER_DEFAULT_W);
//		setHeight(Constants. PLAYER_DEFAULT_H);
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
//		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W);
//		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H);
//		
//		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE);
//		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST);
//		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED);
//		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W);
//		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H);
//		
//		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE);
//		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST);
//		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED);
//		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W);
//		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H);
//		
//		setDefendValue(Constants.DEFAULT_DEFEND_VALUE);
//		setDefendDist(Constants.DEFAULT_DEFEND_DIST);
//		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED);
//		setDefendWidth(Constants.PLAYER_DEFAULT_DEFEND_W);
//		setDefendHeight(Constants.PLAYER_DEFAULT_DEFEND_H);		
		setAttackNearName(Constants.GRANDFATHER_ATTACKNEAR_NAME);
		setAttackFarName(Constants.GRANDFATHER_ATTACKFAR_NAME);
		setAttackKillName(Constants.GRANDFATHER_ATTACKKILL_NAME);
		setDefendName(Constants.GRANDFATHER_DEFEND_NAME);
	}

}
