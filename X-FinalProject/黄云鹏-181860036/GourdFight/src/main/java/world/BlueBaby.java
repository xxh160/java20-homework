package world;

import framework.Constants;
import javafx.scene.image.Image;
import output.URL;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.27
 * @inherit Entity
 * @functions 五娃的实体类
 * @properties 全部继承自父类
 * @methods  全部继承自父类
 */

public class BlueBaby extends Entity { // 五娃实体类
	
	public BlueBaby(String name) {
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
		setLifeValue(Constants.DEFAULT_LIFE_VALUE);
		setMoveSpeed(Constants.DEFAULT_MOVE_SPEED);
		setRunSpeed(Constants.DEFAULT_RUN_SPEED);
		setJumpSpeed(Constants.DEFAULT_JUMP_SPEED);
		setJumpHeight(Constants.DEFAULT_JUMP_HEIGHT);
//		
		setAttackNearValue(Constants.DEFAULT_ATTACK_NEAR_VALUE+2);
		setAttackNearDist(Constants.DEFAULT_ATTACK_NEAR_DIST+5);
		setAttackNearSpeed(Constants.DEFAULT_ATTACK_NEAR_SPEED);

		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 50);
		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H);
//		
		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE+2);
		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST+5);
		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED);

		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 100);
		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H);
//		
		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE+2);
		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST+5);
		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED);

		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 90);
		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 30);
//		
		setDefendValue(Constants.DEFAULT_DEFEND_VALUE);
		setDefendDist(Constants.DEFAULT_DEFEND_DIST);
		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED);

		setDefendWidth(Constants.PLAYER_DEFAULT_DEFEND_W + 30);
		setDefendHeight(Constants.PLAYER_DEFAULT_DEFEND_H + 40);		
		// 设置招式名称
		setAttackNearName(Constants.BLUEBABY_ATTACKNEAR_NAME);
		setAttackFarName(Constants.BLUEBABY_ATTACKFAR_NAME);
		setAttackKillName(Constants.BLUEBABY_ATTACKKILL_NAME);
		setDefendName(Constants.BLUEBABY_DEFEND_NAME);

	}

}
