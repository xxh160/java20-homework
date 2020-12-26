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
	
	// 实体图片(注：后缀1的为自己，后缀2的为对手)
	public static final String BACKGROUND_INIT_IMAGE = "background_init_image"; // 初始背景图片
	public static final String PLAYER1_INIT_IMAGE = "player1_init_image"; // 玩家1初始图片
	public static final String PLAYER2_INIT_IMAGE = "player2_init_image"; // 玩家2初始图片
	
}
