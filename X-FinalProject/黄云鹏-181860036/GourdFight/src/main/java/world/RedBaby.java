package world;

import framework.Constants;
import javafx.scene.image.Image;
import output.URL;

public class RedBaby extends Entity { // 大娃实体类
	
	public RedBaby(String name) {
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
		setLifeValue(Constants.DEFAULT_LIFE_VALUE+20);
		setMoveSpeed(Constants.DEFAULT_MOVE_SPEED-1);
		setRunSpeed(Constants.DEFAULT_RUN_SPEED-2);
		setJumpSpeed(Constants.DEFAULT_JUMP_SPEED-1);
		setJumpHeight(Constants.DEFAULT_JUMP_HEIGHT-20);
//		
		setAttackNearValue(Constants.DEFAULT_ATTACK_NEAR_VALUE+5);
		setAttackNearDist(Constants.DEFAULT_ATTACK_NEAR_DIST-5);
		setAttackNearSpeed(Constants.DEFAULT_ATTACK_NEAR_SPEED+1);

		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 10);
		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H);
//		
		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE-5);
		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST-50);
		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED-2);

		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 15);
		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H - 10);
//		
		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE+5);
		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST-10);
		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED+1);

		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 10);
		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H - 10);
//		
		setDefendValue(Constants.DEFAULT_DEFEND_VALUE+1);
		setDefendDist(Constants.DEFAULT_DEFEND_DIST-5);
		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED+1);

		setDefendWidth(Constants.PLAYER_DEFAULT_DEFEND_W);
		setDefendHeight(Constants.PLAYER_DEFAULT_DEFEND_H + 60);	
		// 设置招式名称
		setAttackNearName(Constants.REDBABY_ATTACKNEAR_NAME);
		setAttackFarName(Constants.REDBABY_ATTACKFAR_NAME);
		setAttackKillName(Constants.REDBABY_ATTACKKILL_NAME);
		setDefendName(Constants.REDBABY_DEFEND_NAME);
	}

}