package world;

import java.util.Random;

import framework.Constants;
import javafx.scene.image.Image;
import output.URL;

public class Background extends Entity { // 背景场景类

	public Background(String name) {
		super(name);
		// 子类特殊设置
//		setMobile(false);
//		setActive(true);
//		setAttackable(false);
//		setDefendable(false);
//		
		setWidth(Constants.BACKGROUND_W);
		setHeight(Constants.BACKGROUND_H);
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
		
		// 随机选择一个场景
		String[] bgs = new String[]{"door","desk","ground","hole","home"}; 
		String dirStr = "main";
		String filePath = URL.toPngPath(dirStr, name,bgs[new Random().nextInt(5)]);
//		String filePath = URL.toPngPath(dirStr, name,bgs[0]); // test door
//		String filePath = URL.toPngPath(dirStr, name,bgs[1]); // test desk
//		String filePath = URL.toPngPath(dirStr, name,bgs[2]); // test ground
//		String filePath = URL.toPngPath(dirStr, name,bgs[3]); // test hole
//		String filePath = URL.toPngPath(dirStr, name,bgs[4]); // test home
		Image background_img = new Image(URL.toURL(filePath)); 
		addImage(EntityState.STANDING_FORWARD, background_img);
		setState(EntityState.STANDING_FORWARD);
	}

}
