package world;

import framework.Constants;
import javafx.scene.image.Image;
import output.URL;

/**
 * 
 * @author 黄云鹏
 * @version	2020.12.27
 * @inherit Entity
 * @functions 三娃的实体类
 * @properties 全部继承自父类
 * @methods  全部继承自父类
 */

public class YellowBaby extends Entity { // 三娃实体类
	
	public YellowBaby(String name) {
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
		setLifeValue(Constants.DEFAULT_LIFE_VALUE+30);
		setMoveSpeed(Constants.DEFAULT_MOVE_SPEED-1);
		setRunSpeed(Constants.DEFAULT_RUN_SPEED-2);
		setJumpSpeed(Constants.DEFAULT_JUMP_SPEED-2);
		setJumpHeight(Constants.DEFAULT_JUMP_HEIGHT-20);
//		
		setAttackNearValue(Constants.DEFAULT_ATTACK_NEAR_VALUE+2);
		setAttackNearDist(Constants.DEFAULT_ATTACK_NEAR_DIST-5);
		setAttackNearSpeed(Constants.DEFAULT_ATTACK_NEAR_SPEED);

		setAttackNearWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 30);
		setAttackNearHeight(Constants.PLAYER_DEFAULT_ATTACK_H + 30);
//		
		setAttackFarValue(Constants.DEFAULT_ATTACK_FAR_VALUE-5);
		setAttackFarDist(Constants.DEFAULT_ATTACK_FAR_DIST-20);
		setAttackFarSpeed(Constants.DEFAULT_ATTACK_FAR_SPEED-1);

		setAttackFarWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 120);
		setAttackFarHeight(Constants.PLAYER_DEFAULT_ATTACK_H - 10);
//		
		setAttackKillValue(Constants.DEFAULT_ATTACK_KILL_VALUE);
		setAttackKillDist(Constants.DEFAULT_ATTACK_KILL_DIST);
		setAttackKillSpeed(Constants.DEFAULT_ATTACK_KILL_SPEED);

		setAttackKillWidth(Constants.PLAYER_DEFAULT_ATTACK_W + 120);
		setAttackKillHeight(Constants.PLAYER_DEFAULT_ATTACK_H - 20);
//		
		setDefendValue(Constants.DEFAULT_DEFEND_VALUE+3);
		setDefendDist(Constants.DEFAULT_DEFEND_DIST+10);
		setDefendSpeed(Constants.DEFAULT_DEFEND_SPEED+2);

		setDefendWidth(Constants.PLAYER_DEFAULT_DEFEND_W + 30);
		setDefendHeight(Constants.PLAYER_DEFAULT_DEFEND_H + 30);		
		// 设置招式名称
		setAttackNearName(Constants.YELLOWBABY_ATTACKNEAR_NAME);
		setAttackFarName(Constants.YELLOWBABY_ATTACKFAR_NAME);
		setAttackKillName(Constants.YELLOWBABY_ATTACKKILL_NAME);
		setDefendName(Constants.YELLOWBABY_DEFEND_NAME);
	}

}