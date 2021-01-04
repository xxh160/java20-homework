package world;

import framework.Constants;

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
		setLifeValue(Constants.DEFAULT_LIFE_VALUE+20);
		setMoveSpeed(Constants.DEFAULT_MOVE_SPEED-1);
		setRunSpeed(Constants.DEFAULT_RUN_SPEED-1);
		setJumpSpeed(Constants.DEFAULT_JUMP_SPEED-1);
		setJumpHeight(Constants.DEFAULT_JUMP_HEIGHT-20);
//		
		setAttackNearValue(Constants.DEFAULT_ATTACK_NEAR_VALUE+5);
		setAttackNearDist(Constants.DEFAULT_ATTACK_NEAR_DIST-10);
		setAttackNearSpeed(Constants.DEFAULT_ATTACK_NEAR_SPEED+1);

		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 10);
		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 10);
//		
		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE);
		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST);
		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED);

		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 50);
		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 10);
//		
		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE);
		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST);
		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED);

		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 60);
		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 60);
//		
		setDefendValue(Constants.DEFAULT_DEFEND_VALUE+1);
		setDefendDist(Constants.DEFAULT_DEFEND_DIST-5);
		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED+1);

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
