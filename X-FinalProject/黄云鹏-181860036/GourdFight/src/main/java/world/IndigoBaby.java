package world;

import gourdfight.Constants;
import javafx.scene.image.Image;
import output.URL;

public class IndigoBaby extends Entity { // 六娃实体类
	
	public IndigoBaby(String name) {
		super(name);
		// 子类设置
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
		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 30);
		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 40);
//		
//		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE);
//		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST);
//		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED);
		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W);
		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H);
//		
//		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE);
//		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST);
//		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED);
		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 60);
		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 40);
//		
//		setDefendValue(Constants.DEFAULT_DEFEND_VALUE);
//		setDefendDist(Constants.DEFAULT_DEFEND_DIST);
//		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED);
		setDefendWidth(Constants.PLAYER_DEFAULT_DEFEND_W + 15);
		setDefendHeight(Constants.PLAYER_DEFAULT_DEFEND_H + 60);	
		setAttackNearName(Constants.INDIGOBABY_ATTACKNEAR_NAME);
		setAttackFarName(Constants.INDIGOBABY_ATTACKFAR_NAME);
		setAttackKillName(Constants.INDIGOBABY_ATTACKKILL_NAME);
		setDefendName(Constants.INDIGOBABY_DEFEND_NAME);
	}

}
