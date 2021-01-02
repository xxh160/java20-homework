package world;

import gourdfight.Constants;

public class Scorpion extends Entity { // 蝎子精实体类

	public Scorpion(String name) {
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
		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 10);
		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 10);
//		
//		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE);
//		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST);
//		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED);
		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 50);
		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 10);
//		
//		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE);
//		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST);
//		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED);
		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 60);
		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 60);
//		
//		setDefendValue(Constants.DEFAULT_DEFEND_VALUE);
//		setDefendDist(Constants.DEFAULT_DEFEND_DIST);
//		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED);
		setDefendWidth(Constants.PLAYER_DEFAULT_DEFEND_W + 20);
		setDefendHeight(Constants.PLAYER_DEFAULT_DEFEND_H + 80);		
		setAttackNearName(Constants.SCORPION_ATTACKNEAR_NAME);
		setAttackFarName(Constants.SCORPION_ATTACKFAR_NAME);
		setAttackKillName(Constants.SCORPION_ATTACKKILL_NAME);
		setDefendName(Constants.SCORPION_DEFEND_NAME);
	}
	
	@Override
		public double getDeltaY() {
			return super.getDeltaY() - 70;
		}

}
