package world;

import java.util.Random;

import framework.Constants;
import javafx.scene.image.Image;
import output.URL;

public class PurpleBaby extends Entity { // 七娃实体类
	
	private RedBaby redBaby; // 大哥
	private OrangeBaby orangeBaby; // 二哥
	private YellowBaby yellowBaby; // 三哥
	private GreenBaby greenBaby; // 四哥
	private BlueBaby blueBaby; // 五哥
	private IndigoBaby indigoBaby; // 六哥
	
	private String attackNearBroName;
	private String attackFarBroName;
	private String attackKillBroName;
	private String defendBroName;
	
	// 初始化
 	public PurpleBaby(String name) {
		super(name);
		
		// 初始化各位哥哥
		redBaby = new RedBaby(Constants.REDBABY_NAME);
		orangeBaby = new OrangeBaby(Constants.ORANGEBABY_NAME);
		yellowBaby = new YellowBaby(Constants.YELLOWBABY_NAME);
		greenBaby = new GreenBaby(Constants.GREENBABY_NAME);
		blueBaby = new BlueBaby(Constants.BLUEBABY_NAME);
		indigoBaby = new IndigoBaby(Constants.INDIGOBABY_NAME);
		
		// 子类设置
		setMobile(true);
//		setActive(true);
//		setAttackable(false);
//		setDefendable(false);
//		
		setWidth(Constants. PLAYER_DEFAULT_W + 20);
//		setHeight(Constants. PLAYER_DEFAULT_H);
//		
//		setLifeValue(Constants.DEFAULT_LIFE_VALUE);
//		setMoveSpeed(Constants.DEFAULT_MOVE_SPEED);
//		setRunSpeed(Constants.DEFAULT_RUN_SPEED);
//		setJumpSpeed(Constants.DEFAULT_JUMP_SPEED);
//		setJumpHeight(Constants.DEFAULT_JUMP_HEIGHT);	
		
		// 随机挑选哥哥的属性来设置攻击和防御属性
		randomSetAttack();
		randomSetDefend();
		
	}

	private void randomSetAttack() { // 随机选择哥哥的攻击属性来设置攻击属性
		// 近攻随机设置
		Entity brother1 = randomBrother();
		brother1.attackNear();
		attackNearBroName = brother1.getName();
		setAttackNearValue(brother1.getCurrentAttackValue());
		setAttackNearDist(brother1.getCurrentAttackDist());
		setAttackNearSpeed(brother1.getCurrentAttackSpeed());
		setAttackNearWidth(brother1.getCurrentAttackWidth());
		setAttackNearHeight(brother1.getCurrentAttackHeight());
		setAttackNearName(brother1.getCurrentAttackName());
		String dirStr = Constants.MAIN_DIRECTORY;
		String lFilePath = URL.toPngPath(dirStr, brother1.getName(), EntityState.ATTACKING_NEAR_TOLEFT.getState());
		String rFilePath = URL.toPngPath(dirStr, brother1.getName(), EntityState.ATTACKING_NEAR_TORIGHT.getState());
		Image lImg = new Image(URL.toURL(lFilePath));
		Image rImg = new Image(URL.toURL(rFilePath));
		setAttackNearImage(lImg, rImg); 
		
		// 远攻随机设置
		Entity brother2 = randomBrother();
		brother2.attackFar();
		attackFarBroName = brother2.getName();
		setAttackFarValue(brother2.getCurrentAttackValue());
		setAttackFarDist(brother2.getCurrentAttackDist());
		setAttackFarSpeed(brother2.getCurrentAttackSpeed());
		setAttackFarWidth(brother2.getCurrentAttackWidth());
		setAttackFarHeight(brother2.getCurrentAttackHeight());
		setAttackFarName(brother2.getCurrentAttackName());
		lFilePath = URL.toPngPath(dirStr, brother2.getName(), EntityState.ATTACKING_FAR_TOLEFT.getState());
		rFilePath = URL.toPngPath(dirStr, brother2.getName(), EntityState.ATTACKING_FAR_TORIGHT.getState());
		lImg = new Image(URL.toURL(lFilePath));
		rImg = new Image(URL.toURL(rFilePath));
		setAttackFarImage(lImg, rImg); 
		
		// 必杀随机设置
		Entity brother3 = randomBrother();
		attackKillBroName = brother3.getName();
		brother3.attackKill(brother3.getFullEnergy());
		setAttackKillValue(brother3.getCurrentAttackValue());
		setAttackKillDist(brother3.getCurrentAttackDist());
		setAttackKillSpeed(brother3.getCurrentAttackSpeed());
		setAttackKillWidth(brother3.getCurrentAttackWidth());
		setAttackKillHeight(brother3.getCurrentAttackHeight());
		setAttackKillName(brother3.getCurrentAttackName());
		lFilePath = URL.toPngPath(dirStr, brother3.getName(), EntityState.ATTACKING_KILL_TOLEFT.getState());
		rFilePath = URL.toPngPath(dirStr, brother3.getName(), EntityState.ATTACKING_KILL_TORIGHT.getState());
		lImg = new Image(URL.toURL(lFilePath));
		rImg = new Image(URL.toURL(rFilePath));
		setAttackKillImage(lImg, rImg); 
	}
	
	private void randomSetDefend() { // 随机选择哥哥的防御属性来设置防御属性
		// 防御随机设置
		Entity brother1 = randomBrother();
		brother1.defend();
		defendBroName = brother1.getName();
		setDefendValue(brother1.getDefendValue());
		setDefendDist(brother1.getDefendDist());
		setDefendSpeed(brother1.getDefendSpeed());
		setDefendWidth(brother1.getDefendWidth());
		setDefendHeight(brother1.getDefendHeight());	
		setDefendName(brother1.getDefendName());

		String dirStr = Constants.MAIN_DIRECTORY;
		String lFilePath = URL.toPngPath(dirStr, brother1.getName(), EntityState.DEFENDING_TOLEFT.getState());
		String rFilePath = URL.toPngPath(dirStr, brother1.getName(), EntityState.DEFENDING_TORIGHT.getState());
		Image lImg = new Image(URL.toURL(lFilePath));
		Image rImg = new Image(URL.toURL(rFilePath));
		setDefendImg(lImg, rImg); 
	}

	private Entity randomBrother() { // 随机从6个兄弟之中选择一个
		int order = new Random().nextInt(6)+1;
		switch (order) {
		case 1:
			return redBaby;
		case 2:
			return orangeBaby;
		case 3:
			return yellowBaby;
		case 4:
			return greenBaby;
		case 5:
			return blueBaby;
		case 6:
			return indigoBaby;
		default:
			return redBaby;
		}
	}

	private String geCurrentName() {
		switch (state) {
		case DEFENDING_TOLEFT:
			return defendBroName;
		case DEFENDING_TORIGHT:
			return defendBroName;
		case ATTACKING_NEAR_TOLEFT:
			return attackNearBroName;
		case ATTACKING_NEAR_TORIGHT:
			return attackNearBroName;
		case ATTACKING_FAR_TOLEFT:
			return attackFarBroName;
		case ATTACKING_FAR_TORIGHT:
			return attackFarBroName;
		case ATTACKING_KILL_TOLEFT:
			return attackKillBroName;
		case ATTACKING_KILL_TORIGHT:
			return attackKillBroName;
		default:
			return name;
	}
	}
	
	@Override
	public String getName() {
		switch (state) {
		case DEFENDING_TOLEFT:
			return defendBroName;
		case DEFENDING_TORIGHT:
			return defendBroName;
		case ATTACKING_NEAR_TOLEFT:
			return attackNearBroName;
		case ATTACKING_NEAR_TORIGHT:
			return attackNearBroName;
		case ATTACKING_FAR_TOLEFT:
			return attackFarBroName;
		case ATTACKING_FAR_TORIGHT:
			return attackFarBroName;
		case ATTACKING_KILL_TOLEFT:
			return attackKillBroName;
		case ATTACKING_KILL_TORIGHT:
			return attackKillBroName;
		default:
			return super.getName();
		}
	}

}
