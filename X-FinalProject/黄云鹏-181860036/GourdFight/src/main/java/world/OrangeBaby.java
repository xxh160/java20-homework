package world;

import framework.Constants;
import javafx.scene.image.Image;
import output.URL;

public class OrangeBaby extends Entity { // 二娃实体类
	
	public OrangeBaby(String name) {
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
		setLifeValue(Constants.DEFAULT_LIFE_VALUE-10);
		setMoveSpeed(Constants.DEFAULT_MOVE_SPEED+1);
		setRunSpeed(Constants.DEFAULT_RUN_SPEED+1);
		setJumpSpeed(Constants.DEFAULT_JUMP_SPEED+1);
		setJumpHeight(Constants.DEFAULT_JUMP_HEIGHT-10);
//		
		setAttackNearValue(Constants.DEFAULT_ATTACK_NEAR_VALUE-5);
		setAttackNearDist(Constants.DEFAULT_ATTACK_NEAR_DIST-10);
		setAttackNearSpeed(Constants.DEFAULT_ATTACK_NEAR_SPEED-1);

		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 30);
		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 40);
//		
		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE+5);
		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST+50);
		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED+2);

		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W);
		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H);
//		
		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE-5);
		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST+10);
		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED-1);

		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 60);
		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 40);
//		
		setDefendValue(Constants.DEFAULT_DEFEND_VALUE-1);
		setDefendDist(Constants.DEFAULT_DEFEND_DIST+5);
		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED);

		setDefendWidth(Constants.PLAYER_DEFAULT_DEFEND_W + 15);
		setDefendHeight(Constants.PLAYER_DEFAULT_DEFEND_H + 60);
		// 设置招式名称
		setAttackNearName(Constants.ORANGEBABY_ATTACKNEAR_NAME);
		setAttackFarName(Constants.ORANGEBABY_ATTACKFAR_NAME);
		setAttackKillName(Constants.ORANGEBABY_ATTACKKILL_NAME);
		setDefendName(Constants.ORANGEBABY_DEFEND_NAME);
	}
	
}

