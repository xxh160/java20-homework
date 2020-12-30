package gourdfight;

public class Constants { // 常量类，提供游戏中所有需要的常量

	// 窗口
	public static final String WINDOW_TITLE = "GourdBaby VS Monsters"; // 游戏标题 
	public static final int WINDOW_WIDTH = 800; // 窗口宽度
	public static final int WINDOW_HEIGHT = 600; // 窗口高度
	
	// 页面
	public static final String HOME_VIEW_KEY = "Home View"; // 主页面键值
	public static final String PLAY_VIEW_KEY = "Play View"; // 游戏页面键值
	public static final String STACK_PANE = "Stack Pane"; //栈控件
	public static final String IMAGE_PANE = "Image Pane"; // 图片控件
	
	// 网络
	public static final int PORT = 8001; // 端口号
	
	// 实体身份/名称(注：后缀1的为自己，后缀2的为对手)
	public static final String BACKGROUND = "background"; // 背景
	public static final String STATEBAR = "statebar"; // 状态栏
	public static final String PLAYER1 = "player1"; // 玩家1
	public static final String PLAYER2 = "player2"; // 玩家2
	public static final String BLOOD1 = "blood1"; // 血条1
	public static final String BLOOD2 = "blood2"; // 血条2
	public static final String PROFILE1 = "profile1"; //头像1
	public static final String PROFILE2 = "profile2"; //头像2
	public static final String STATE1 = "state1"; // 状态1
	public static final String STATE2 = "state2"; // 状态2
	
	// 实体位置(注：后缀1的为自己，后缀2的为对手)
	public static final double BACKGROUND_X = 0; // 背景x轴坐标
	public static final double BACKGROUND_Y = 0; // 背景y轴坐标
	public static final double PLAYER1_INIT_X = 50; // 玩家1初始x轴坐标
	public static final double PLAYER1_INIT_Y = 400; // 玩家1初始y轴坐标
	public static final double PLAYER2_INIT_X = 400; // 玩家2初始x轴坐标
	public static final double PLAYER2_INIT_Y = 400; // 玩家2初始y轴坐标
	
	// 实体大小(注：后缀1的为自己，后缀2的为对手)
	public static final double BACKGROUND_W = WINDOW_WIDTH; // 背景宽度
	public static final double BACKGROUND_H = WINDOW_HEIGHT; // 背景宽度
	public static final double PLAYER1_INIT_W = 50; // 玩家1初始宽度
	public static final double PLAYER1_INIT_H = 50; // 玩家1初始高度
	public static final double PLAYER2_INIT_W = 50; // 玩家2初始宽度
	public static final double PLAYER2_INIT_H = 50; // 玩家2初始高度
	
	// 实体状态值
	public static final String STANDING_FORWARD = "standingForward"; // 朝正面站着
	public static final String STANDING_TOLEFT = "standingToLeft"; // 朝左边站着
	public static final String STANDING_TORIGHT = "standingToRight"; // 朝右边站着
	public static final String MOVING_TOLEFT = "movingToLeft"; // 朝左边移动
	public static final String MOVING_TORIGHT = "movingToRight"; // 朝右边移动
	public static final String RUNNING_TOLEFT = "runningToLeft"; // 朝左边冲刺
	public static final String RUNNING_TORIGHT = "runningToRight"; // 朝右边冲刺
	public static final String JUMPING_TOLEFT = "jumpingToLeft"; // 朝左边跳跃
	public static final String JUMPING_TORIGHT = "jumpingToRight"; // 朝右边跳跃
	public static final String LYING_TOLEFT = "lyingToLeft"; //朝左边倒地
	public static final String LYING_TORIGHT = "lyingToRight"; // 朝右边倒地
	public static final String ATTACKING_NEAR_TOLEFT = "attackingNearToLeft"; // 朝左边近攻
	public static final String ATTACKING_NEAR_TORIGHT = "attackingNearToRight"; // 朝右边近攻
	public static final String ATTACKING_FAR_TOLEFT = "attackingFarToLeft"; // 朝左边远攻
	public static final String ATTACKING_FAR_TORIGHT = "attackingFarToRight"; // 朝右边远攻
	public static final String ATTACKING_KILL_TOLEFT = "attackingKillToLeft"; // 朝左边必杀
	public static final String ATTACKING_KILL_TORIGHT = "attackingKillToRight"; // 朝右边必杀
	public static final String DEFENDING_TOLEFT = "defendingToLeft"; // 朝左边防御
	public static final String DEFENDING_TORIGHT = "defendingToRight"; // 朝右边防御
	
	
	// 实体图片(注：后缀1的为自己，后缀2的为对手)
	public static final String BACKGROUND_INIT_IMAGE = "background_init_image"; // 初始背景图片
	public static final String PLAYER1_INIT_IMAGE = "player1_init_image"; // 玩家1初始图片
	public static final String PLAYER2_INIT_IMAGE = "player2_init_image"; // 玩家2初始图片
	
}
