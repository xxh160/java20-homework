package gourdfight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import app.ImageLocate;
import app.ImagePane;
import app.TextLocate;
import app.View;

import output.URL;
import world.AttackEntity;
import world.Background;
import world.Blood;
import world.BloodBar;
import world.BlueBaby;
import world.Chilopod;
import world.CountDown;
import world.Crocodile;
import world.DefendEntity;
import world.Energy;
import world.EnergyBar;
import world.Entity;
import world.EntityName;
import world.EntityState;
import world.GrandFather;
import world.GreenBaby;
import world.IndigoBaby;
import world.OrangeBaby;
import world.PurpleBaby;
import world.RedBaby;
import world.Scorpion;
import world.Snake;
import world.YellowBaby;
import framework.*;
import input.Key;
import javafx.scene.image.Image;
import network.Packet;
import network.TCPClient;
import network.TCPServer;

public class PlayView extends View { // 游戏页面类
	
	private HashMap<String, Entity> entityMap; // 游戏实体字典 
	private LinkedHashMap<String, ImageLocate> imgLocateMap; // 游戏实体图片定位字典
	private LinkedHashMap<String, TextLocate> textLocateMap; // 游戏实体文本定位字典
	
	private EntityName player1_name; // 玩家1的实体
	private EntityName player2_name; // 玩家2的实体
	
	private AttackEntity player1_attackEntity; // 玩家1的攻击实体
	private AttackEntity player2_attackEntity; // 玩家2的攻击实体
	private DefendEntity player1_defendEntity; // 玩家1的防御实体
	private DefendEntity player2_defendEntity; // 玩家2的防御实体
	
	private boolean isServer; // 是否作为服务器端(false 则为客户端, 默认为客户端)
	
	int frameCount; // 帧计数器
	int endFrame; // 末尾帧数(默认3秒)
	int endFrameCount; // 末尾帧计数器(计数一局游戏结束后的末尾帧)
	boolean mode; // 游戏模式(true为网络版本，false为单击版本)
	
	private TCPServer server; // 服务器端
	private TCPClient client; // 客户端
	
	private ArrayList<Packet> sendPktQueue; // 发送包队列，即自己的操作/状态序列
	private ArrayList<Packet> receivePktQueue; // 接受包队列，即对手的操作/状态序列
	int sendPktQueueIdx; // 发送包队列指针
	int receivePktQueueIdx; // 接受包队列指针
	
	boolean isStart; // 是否开始游戏(false则进入游戏倒计时)
	boolean isGameOver; // 是否一局游戏结束
	boolean player1Hurt; // 玩家1被攻击中(防止攻击实体在生命周期中连续攻击玩家)
	boolean player2Hurt; // 玩家2被攻击中(防止攻击实体在生命周期中连续攻击玩家)
	int round; // 游戏局数(默认是3)
	int roundCount; // 游戏局数计数器
	
	double currentEnergy1; // 玩家1当前能量值
	double currentEnergy2; // 玩家2当前能量值
	
	// 初始化
	public PlayView() {
		super(Constants.IMAGE_PANE);
		
		entityMap = new HashMap<>();
		imgLocateMap = new LinkedHashMap<>();
		textLocateMap = new LinkedHashMap<>();
		
		frameCount = 0;
		roundCount = 0;
		endFrame = 180;
		endFrameCount = 0;
		currentEnergy1 = 0;
		currentEnergy2 = 0;
		mode = true; 
		isStart = false;
		isGameOver = false;
		player1Hurt = false;
		player2Hurt = false;
		setRound(3);
		
		isServer = false; 
		server = new TCPServer("", Constants.PORT);
		client = new TCPClient("", Constants.PORT);
		
		sendPktQueue = new ArrayList<>();
		receivePktQueue = new ArrayList<>();
		sendPktQueueIdx = 0;
		receivePktQueueIdx = 0;
		
		bgmFileStr = Constants.PLAY_VIEW_BGM;
	}
	
	private void initEntity() { // 初始创建一些必要实体
		// 背景
		setBackground();
		// 玩家1
		setPlayer1();
		// 玩家2
		setPlayer2();
		// 血槽条
		setBloodBar();
		// 能量条
		setEnergyBar();
	}
	
	private void setBackground() { // 初设背景
		// 场景
		Background background = new Background(Constants.BACKGROUND);
		
		addEntity(Constants.BACKGROUND, background);
		
		ImageLocate background_imgLocate = new ImageLocate(
				background.getCurrentImage(),
				Constants.BACKGROUND_X,
				Constants.BACKGROUND_Y,
				background.getWidth(),
				background.getHeight());
		
		addImageLocate(Constants.BACKGROUND, background_imgLocate);
		// PK图标
		Background pk = new Background(Constants.BACKGROUND);
		pk.setWidth(Constants.PK_W);
		pk.setHeight(Constants.PK_H);
		
		String dirStr = "main";
		String filePath = URL.toPngPath(dirStr, pk.getName(),Constants.PK);
		Image img = new Image(URL.toURL(filePath)); 
		pk.addImage(EntityState.STANDING_TORIGHT, img);
		pk.setState(EntityState.STANDING_TORIGHT);
		
		addEntity(Constants.PK, pk);
		
		ImageLocate pk_imgLocate = new ImageLocate(
				pk.getCurrentImage(),
				Constants.PK_X,
				Constants.PK_Y,
				pk.getWidth(),
				pk.getHeight());
		
		addImageLocate(Constants.PK, pk_imgLocate);
	}
	
	private void setBloodBar() { // 初设血槽条
		// 左血槽
		BloodBar bloodBar1 = new BloodBar(Constants.BLOODBAR_NAME); 
		bloodBar1.setState(EntityState.STANDING_TOLEFT);
		
		addEntity(Constants.BLOODBAR1, bloodBar1);
		
		ImageLocate bloodBar1_imgLocate = new ImageLocate(
				bloodBar1.getCurrentImage(),
				Constants.BLOODBAR1_X,
				Constants.BLOODBAR1_Y,
				bloodBar1.getWidth(),
				bloodBar1.getHeight());
		
		addImageLocate(Constants.BLOODBAR1, bloodBar1_imgLocate);
		// 右血槽
		BloodBar bloodBar2 = new BloodBar(Constants.BLOODBAR_NAME); 
		bloodBar2.setState(EntityState.STANDING_TORIGHT);
		
		addEntity(Constants.BLOODBAR2, bloodBar2);
		
		ImageLocate bloodBar2_imgLocate = new ImageLocate(
				bloodBar2.getCurrentImage(),
				Constants.BLOODBAR2_X,
				Constants.BLOODBAR2_Y,
				bloodBar2.getWidth(),
				bloodBar2.getHeight());
		
		addImageLocate(Constants.BLOODBAR2, bloodBar2_imgLocate);
		// 左血条
		Blood blood1 = new Blood(Constants.BLOOD_NAME); 
		blood1.setState(EntityState.STANDING_TOLEFT);
		Entity player1 = entityMap.get(Constants.PLAYER1);
		if(player1 != null) {
			blood1.setFullLife(player1.getLifeValue());
		}
		
		addEntity(Constants.BLOOD1, blood1);
		
		ImageLocate blood1_imgLocate = new ImageLocate(
				blood1.getCurrentImage(),
				Constants.BLOOD1_X,
				Constants.BLOOD1_Y,
				blood1.getWidth(),
				blood1.getHeight());
		
		addImageLocate(Constants.BLOOD1, blood1_imgLocate);
		// 右血条
		Blood blood2 = new Blood(Constants.BLOOD_NAME); 
		blood2.setState(EntityState.STANDING_TORIGHT);
		Entity player2 = entityMap.get(Constants.PLAYER2);
		if(player2 != null) {
			blood2.setFullLife(player2.getLifeValue());
		}
		
		addEntity(Constants.BLOOD2, blood2);
		
		ImageLocate blood2_imgLocate = new ImageLocate(
				blood2.getCurrentImage(),
				Constants.BLOOD2_X,
				Constants.BLOOD2_Y,
				blood2.getWidth(),
				blood2.getHeight());
		
		addImageLocate(Constants.BLOOD2, blood2_imgLocate);
		
	}
	
	private void setEnergyBar() { // 初设能量槽条
		Entity player1 = entityMap.get(Constants.PLAYER1);
		Entity player2 = entityMap.get(Constants.PLAYER2);
		
		// 左能量槽
		EnergyBar energyBar1 = new EnergyBar(Constants.ENERGYBAR_NAME); 
		energyBar1.setState(EntityState.STANDING_TOLEFT);
		
		addEntity(Constants.ENERGYBAR1, energyBar1);
		
		ImageLocate energyBar1_imgLocate = new ImageLocate(
				energyBar1.getCurrentImage(),
				Constants.PLAYER1_INIT_X + player1.getDeltaX(),
				Constants.PLAYER1_INIT_Y + player1.getDeltaY()
				+ Constants.ENERGYBAR_DELTAY,
				energyBar1.getWidth(),
				energyBar1.getHeight());
		
		addImageLocate(Constants.ENERGYBAR1,energyBar1_imgLocate);
		// 右能量槽
		EnergyBar energyBar2 = new EnergyBar(Constants.ENERGYBAR_NAME); 
		energyBar2.setState(EntityState.STANDING_TOLEFT);
		
		addEntity(Constants.ENERGYBAR2, energyBar2);
		
		ImageLocate energyBar2_imgLocate = new ImageLocate(
				energyBar2.getCurrentImage(),
				Constants.PLAYER2_INIT_X + player2.getDeltaX(),
				Constants.PLAYER2_INIT_Y + player2.getDeltaY()
				+ Constants.ENERGYBAR_DELTAY,
				energyBar2.getWidth(),
				energyBar2.getHeight());
		
		addImageLocate(Constants.ENERGYBAR2, energyBar2_imgLocate);
		// 左能量条
		Energy energy1 = new Energy(Constants.ENERGY_NAME); 
		energy1.setState(EntityState.STANDING_TOLEFT);
		
		addEntity(Constants.ENERGY1, energy1);
		
		ImageLocate energy1_imgLocate = new ImageLocate(
				energy1.getCurrentImage(),
				Constants.PLAYER1_INIT_X + player1.getDeltaX(),
				Constants.PLAYER1_INIT_Y + player1.getDeltaY()
				+ Constants.ENERGY_DELTAY,
				energy1.getWidth() * (currentEnergy1  / player1.getFullEnergy()),
				energy1.getHeight());
		
		addImageLocate(Constants.ENERGY1, energy1_imgLocate);
		// 右能量条
		Energy energy2 = new Energy(Constants.ENERGY_NAME); 
		energy2.setState(EntityState.STANDING_TOLEFT);
		
		addEntity(Constants.ENERGY2, energy2);
		
		ImageLocate energy2_imgLocate = new ImageLocate(
				energy2.getCurrentImage(),
				Constants.PLAYER2_INIT_X + player2.getDeltaX(),
				Constants.PLAYER2_INIT_Y + player2.getDeltaY()
				+ Constants.ENERGY_DELTAY,
				energy2.getWidth() * (currentEnergy2  / player2.getFullEnergy()),
				energy2.getHeight());
		
		addImageLocate(Constants.ENERGY2, energy2_imgLocate);
		
	}
	
	private void setPlayer1() { // 初设玩家1
		
		// 选择角色
		Entity player1 = null;
		switch (player1_name) {
		case REDBABY:
			player1 = new RedBaby(Constants.REDBABY_NAME);
			break;
		case ORANGEBABY:
			player1 = new OrangeBaby(Constants.ORANGEBABY_NAME);
			break;
		case YELLOWBABY:
			player1 = new YellowBaby(Constants.YELLOWBABY_NAME);
			break;
		case GREENBABY:
			player1 = new GreenBaby(Constants.GREENBABY_NAME);
			break;
		case BLUEBABY:
			player1 = new BlueBaby(Constants.BLUEBABY_NAME);
			break;
		case INDIGOBABY:
			player1 = new IndigoBaby(Constants.INDIGOBABY_NAME);
			break;
		case PURPLEBABY:
			player1 = new PurpleBaby(Constants.PURPLEBABY_NAME);
			break;
		case GRANDFATHER:
			player1 = new GrandFather(Constants.GRANDFATHER_NAME);
			break;
		case SNAKE:
			player1 = new Snake(Constants.SNAKE_NAME);
			break;
		case SCORPION:
			player1 = new Scorpion(Constants.SCORPION_NAME);
			break;
		case CHILOPOD:
			player1 = new Chilopod(Constants.CHILOPOD_NAME);
			break;
		case CROCODILE:
			player1 = new Crocodile(Constants.CROCODILE_NAME);
			break;
		default:
			player1 = new RedBaby(Constants.REDBABY_NAME);
			break;
		}
		player1.setDirection(false); // 初始朝向向右
		player1.setState(EntityState.STANDING_TORIGHT); // 初始状态向右站着

		// 添加实体
		addEntity(Constants.PLAYER1, player1);
				
		// 添加图片定位器
		ImageLocate player1_imgLocate = new ImageLocate(
				player1.getCurrentImage(),
				Constants.PLAYER1_INIT_X + player1.getDeltaX(),
				Constants.PLAYER1_INIT_Y + player1.getDeltaY(),
				player1.getWidth(),
				player1.getHeight());
		
		addImageLocate(Constants.PLAYER1, player1_imgLocate);
	}
	
	private void setPlayer2() { // 初设玩家2
		
		// 选择角色
		Entity player2 = null;
		switch (player2_name) {
		case REDBABY:
			player2 = new RedBaby(Constants.REDBABY_NAME);
			break;
		case ORANGEBABY:
			player2 = new OrangeBaby(Constants.ORANGEBABY_NAME);
			break;
		case YELLOWBABY:
			player2 = new YellowBaby(Constants.YELLOWBABY_NAME);
			break;
		case GREENBABY:
			player2 = new GreenBaby(Constants.GREENBABY_NAME);
			break;
		case BLUEBABY:
			player2 = new BlueBaby(Constants.BLUEBABY_NAME);
			break;
		case INDIGOBABY:
			player2 = new IndigoBaby(Constants.INDIGOBABY_NAME);
			break;
		case PURPLEBABY:
			player2 = new PurpleBaby(Constants.PURPLEBABY_NAME);
			break;
		case GRANDFATHER:
			player2 = new GrandFather(Constants.GRANDFATHER_NAME);
			break;
		case SNAKE:
			player2 = new Snake(Constants.SNAKE_NAME);
			break;
		case SCORPION:
			player2 = new Scorpion(Constants.SCORPION_NAME);
			break;
		case CHILOPOD:
			player2 = new Chilopod(Constants.CHILOPOD_NAME);
			break;
		case CROCODILE:
			player2 = new Crocodile(Constants.CROCODILE_NAME);
			break;
		default:
			player2 = new RedBaby(Constants.REDBABY_NAME);
			break;
		}
		player2.setDirection(true); // 初始朝向向左
		player2.setState(EntityState.STANDING_TOLEFT); // 初始状态向左站着
	
		// 添加实体
		addEntity(Constants.PLAYER2, player2);
				
		// 添加图片定位器
		ImageLocate player2_imgLocate = new ImageLocate(
				player2.getCurrentImage(),
				Constants.PLAYER2_INIT_X + player2.getDeltaX(),
				Constants.PLAYER2_INIT_Y + player2.getDeltaY(),
				player2.getWidth(),
				player2.getHeight());
		
		addImageLocate(Constants.PLAYER2, player2_imgLocate);
	}
	
	// Setter
	public void setServer(boolean s) { // 设置是否是服务器端
		isServer = s;
	}
	
	public void launchNetwork() { // ！！！！！！！！！开启网络通信，仅作测试用例，应当为上层页面调用 ！！！！！！！！！！
		if(isServer) { // 自己为服务器端
			server.start();
		}
		else { // 自己为客户端
			client.start();
		}
	}
	
	public void setMode(boolean m) { // 设置游戏模式
		mode = m;
	}
	
	public void setRound(int r) { // 设置游戏局数
		round = r;
	}
	
	private void reset() { // 重置游戏局
		entityMap.clear();
		imgLocateMap.clear();
		textLocateMap.clear();
		player1_attackEntity = null;
		player2_attackEntity = null;
		player1_defendEntity = null;
		player2_defendEntity = null;
		frameCount = 0;
		endFrameCount = 0;
		currentEnergy1 = 0;
		currentEnergy2 = 0;
		isStart = false;
		isGameOver = false;
		
		// 存档之后！！！！！！！！！！！！！！
		sendPktQueue.clear(); sendPktQueueIdx = 0;
		receivePktQueue.clear(); receivePktQueueIdx = 0;
	}
	
	private void countDown() { // 游戏进入倒计时

		if(!entityMap.containsKey(Constants.COUNTDOWN)) { // 创建倒计时实体类
			CountDown countDown = new CountDown(Constants.COUNTDOWN);
			countDown.setRound(roundCount+1);
			addEntity(Constants.COUNTDOWN, countDown);
			
			ImageLocate countDown_imgLocate = new ImageLocate(
					countDown.getCurrentImage(),
					Constants.COUNTDOWN_X,
					Constants.COUNTDOWN_Y,
					countDown.getWidth(),
					countDown.getHeight());
			
			addImageLocate(Constants.COUNTDOWN, countDown_imgLocate);
		}
		else {
			CountDown countDown = (CountDown)entityMap.get(Constants.COUNTDOWN);
			Image img = countDown.getCurrentImage();
			if(img == null) { // 倒计时结束
				removeEntity(Constants.COUNTDOWN);
				removeImageLocate(Constants.COUNTDOWN);
				isStart = true;
			}
			else {
				if(countDown.isRound()) { // 进入round显示
					imgLocateMap.get(Constants.COUNTDOWN).setX(Constants.ROUND_X);
					imgLocateMap.get(Constants.COUNTDOWN).setY(Constants.ROUND_Y);
					imgLocateMap.get(Constants.COUNTDOWN).setW(Constants.ROUND_W);
					imgLocateMap.get(Constants.COUNTDOWN).setW(Constants.ROUND_H);
				}
				imgLocateMap.get(Constants.COUNTDOWN).setImg(img);
			}
		}
	}
	
	private void updateGameOver() { // 检测一局游戏是否结束并进行相应的设置或跳转
		if(isGameOver) { // 游戏结束一局
			endFrameCount++;
			if(endFrameCount < endFrame) { // 游戏末尾帧计数
				return;
			}
			
			roundCount++;
			if(roundCount >= round) { // 游戏全部结束
				// 跳转到GameOverView页面
//				Framework.app.gotoView(Constants.GAMEOVER_VIEW_KEY);
				System.out.println("go to GameOverView"); // test!!!!!!!!!!!!!!!!!
			}
			else { 
				// 重新启动游戏
				onLaunch();
			}
		}
	}
	
	private void updatePlayer1() { // 更新玩家1
		
		Entity player1 = entityMap.get(Constants.PLAYER1);
		
		// 只有处于站着的静止状态才能响应用户的下一个输入
		// 有些状态虽然统一传递朝向左的动作，但是实际操作应该按照当时的朝向来定
		if(player1.isStanding()) {
			if(Framework.keyInput.isTyped(Key.A)) { // 向左移动
				
				Packet pkt = new Packet(frameCount,EntityState.MOVING_TOLEFT);
				sendPktQueue.add(pkt);
				if(isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
			}
			else if(Framework.keyInput.isTyped(Key.D)) { // 向右移动
				
				Packet pkt = new Packet(frameCount,EntityState.MOVING_TORIGHT);
				sendPktQueue.add(pkt);
				if(isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
				
			}
			else if(Framework.keyInput.isTyped(Key.S)) { // 冲刺
				Packet pkt = null;
				if(player1.isLeft()) {
					pkt = new Packet(frameCount,EntityState.RUNNING_TOLEFT);
				}else {
					pkt = new Packet(frameCount,EntityState.RUNNING_TORIGHT);
				}
				
				sendPktQueue.add(pkt);
				if(isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
			}
			else if(Framework.keyInput.isTyped(Key.W)) { // 跳跃
				Packet pkt = null;
				if(player1.isLeft()) {
					pkt = new Packet(frameCount,EntityState.JUMPING_TOLEFT);
				}else {
					pkt = new Packet(frameCount,EntityState.JUMPING_TORIGHT);
				}
				
				sendPktQueue.add(pkt);
				if(isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
				
			}
			else if(Framework.keyInput.isTyped(Key.K)) { // 防御
				Packet pkt = null;
				if(player1.isLeft()) {
					pkt = new Packet(frameCount,EntityState.DEFENDING_TOLEFT);
				}else {
					pkt = new Packet(frameCount,EntityState.DEFENDING_TORIGHT);
				}
				
				sendPktQueue.add(pkt);
				if(isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
			}
			else if(Framework.keyInput.isTyped(Key.J)) { // 近攻
				Packet pkt = null;
				if(player1.isLeft()) {
					pkt = new Packet(frameCount,EntityState.ATTACKING_NEAR_TOLEFT);
				}else {
					pkt = new Packet(frameCount,EntityState.ATTACKING_NEAR_TORIGHT);
				}
				
				sendPktQueue.add(pkt);
				if(isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
			}
			else if(Framework.keyInput.isTyped(Key.L)) { // 远攻
				Packet pkt = null;
				if(player1.isLeft()) {
					pkt = new Packet(frameCount,EntityState.ATTACKING_FAR_TOLEFT);
				}else {
					pkt = new Packet(frameCount,EntityState.ATTACKING_FAR_TORIGHT);
				}
				
				sendPktQueue.add(pkt);
				if(isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
			}
			else if(Framework.keyInput.isTyped(Key.I)) { // 必杀
				Packet pkt = null;
				if(player1.isLeft()) {
					pkt = new Packet(frameCount,EntityState.ATTACKING_KILL_TOLEFT);
				}else {
					pkt = new Packet(frameCount,EntityState.ATTACKING_KILL_TORIGHT);
				}
				
				sendPktQueue.add(pkt);
				if(isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
			}
			else {  // 玩家没做任何操作，角色保持站立
				
				// 传递一个向左站着的状态，但不表示动作切换，而是上一个动作延续
				Packet pkt = new Packet(frameCount,EntityState.STANDING_TOLEFT); 
				sendPktQueue.add(pkt);
				if(isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
			}
		}
		
		else { // 当前正在处于非静止状态，不响应玩家操作，但是仍然要发送当前帧的状态包，保证每一帧都有记录
			// 传递一个向左站着的状态，但不表示动作切换，而是上一个动作延续
			Packet pkt = new Packet(frameCount,EntityState.STANDING_TOLEFT); 
			sendPktQueue.add(pkt);
			if(isServer) {
				server.setSendPacket(pkt);
			}
			else {
				client.setSendPacket(pkt);
			}
		}
	}
	
	private void updatePlayer2(boolean mode) { // 更新玩家2
		if(mode) { // 网络版本
			if(isServer) {
				Packet pkt = server.getReceivePakcet();
				if(pkt == null) {
					return;
				}
				if (receivePktQueue.isEmpty()) { // 若未曾接收过包，则pkt一定是新包
					receivePktQueue.add(pkt);
				}
				else {
					int lastFrame = receivePktQueue.get(receivePktQueue.size()-1).getFrame();
					if(pkt.getFrame() > lastFrame) { // 帧数大于队列的最后一帧，则为新包
						receivePktQueue.add(pkt);
					}
				}
			}
			else {
				Packet pkt = client.getReceivePakcet();
				if(pkt == null) {
					return;
				}
				if (receivePktQueue.isEmpty()) { // 若未曾接收过包，则pkt一定是新包
					receivePktQueue.add(pkt);
				}
				else {
					int lastFrame = receivePktQueue.get(receivePktQueue.size()-1).getFrame();
					if(pkt.getFrame() > lastFrame) { // 帧数大于队列的最后一帧，则为新包
						receivePktQueue.add(pkt);
					}
				}
			}
		}
		else { // 单机版本
			Entity player2 = entityMap.get(Constants.PLAYER2);
			
			// 只有处于站着的静止状态才能响应用户的下一个输入
			// 有些状态虽然统一传递朝向左的动作，但是实际操作应该按照当时的朝向来定
			if(player2.isStanding()) {
				if(Framework.keyInput.isTyped(Key.LEFT)) { // 向左移动
					
					Packet pkt = new Packet(frameCount,EntityState.MOVING_TOLEFT);
					receivePktQueue.add(pkt);
					if(!isServer) {
						server.setSendPacket(pkt);
					}
					else {
						client.setSendPacket(pkt);
					}
				}
				else if(Framework.keyInput.isTyped(Key.RIGHT)) { // 向右移动
					
					Packet pkt = new Packet(frameCount,EntityState.MOVING_TORIGHT);
					receivePktQueue.add(pkt);
					if(!isServer) {
						server.setSendPacket(pkt);
					}
					else {
						client.setSendPacket(pkt);
					}
					
				}
				else if(Framework.keyInput.isTyped(Key.DOWN)) { // 冲刺
					Packet pkt = null;
					if(player2.isLeft()) {
						pkt = new Packet(frameCount,EntityState.RUNNING_TOLEFT);
					}else {
						pkt = new Packet(frameCount,EntityState.RUNNING_TORIGHT);
					}
					
					receivePktQueue.add(pkt);
					if(!isServer) {
						server.setSendPacket(pkt);
					}
					else {
						client.setSendPacket(pkt);
					}
				}
				else if(Framework.keyInput.isTyped(Key.UP)) { // 跳跃
					Packet pkt = null;
					if(player2.isLeft()) {
						pkt = new Packet(frameCount,EntityState.JUMPING_TOLEFT);
					}else {
						pkt = new Packet(frameCount,EntityState.JUMPING_TORIGHT);
					}
					
					receivePktQueue.add(pkt);
					if(!isServer) {
						server.setSendPacket(pkt);
					}
					else {
						client.setSendPacket(pkt);
					}
					
				}
				else if(Framework.keyInput.isTyped(Key.B)) { // 防御
					Packet pkt = null;
					if(player2.isLeft()) {
						pkt = new Packet(frameCount,EntityState.DEFENDING_TOLEFT);
					}else {
						pkt = new Packet(frameCount,EntityState.DEFENDING_TORIGHT);
					}
					
					receivePktQueue.add(pkt);
					if(!isServer) {
						server.setSendPacket(pkt);
					}
					else {
						client.setSendPacket(pkt);
					}
				}
				else if(Framework.keyInput.isTyped(Key.V)) { // 近攻
					Packet pkt = null;
					if(player2.isLeft()) {
						pkt = new Packet(frameCount,EntityState.ATTACKING_NEAR_TOLEFT);
					}else {
						pkt = new Packet(frameCount,EntityState.ATTACKING_NEAR_TORIGHT);
					}
					
					receivePktQueue.add(pkt);
					if(!isServer) {
						server.setSendPacket(pkt);
					}
					else {
						client.setSendPacket(pkt);
					}
				}
				else if(Framework.keyInput.isTyped(Key.N)) { // 远攻
					Packet pkt = null;
					if(player2.isLeft()) {
						pkt = new Packet(frameCount,EntityState.ATTACKING_FAR_TOLEFT);
					}else {
						pkt = new Packet(frameCount,EntityState.ATTACKING_FAR_TORIGHT);
					}
					
					receivePktQueue.add(pkt);
					if(!isServer) {
						server.setSendPacket(pkt);
					}
					else {
						client.setSendPacket(pkt);
					}
				}
				else if(Framework.keyInput.isTyped(Key.G)) { // 必杀
					Packet pkt = null;
					if(player2.isLeft()) {
						pkt = new Packet(frameCount,EntityState.ATTACKING_KILL_TOLEFT);
					}else {
						pkt = new Packet(frameCount,EntityState.ATTACKING_KILL_TORIGHT);
					}
					
					receivePktQueue.add(pkt);
					if(!isServer) {
						server.setSendPacket(pkt);
					}
					else {
						client.setSendPacket(pkt);
					}
				}
				else {  // 玩家没做任何操作，角色保持站立
					
					// 传递一个向左站着的状态，但不表示动作切换，而是上一个动作延续
					Packet pkt = new Packet(frameCount,EntityState.STANDING_TORIGHT); 
					receivePktQueue.add(pkt);
					if(!isServer) {
						server.setSendPacket(pkt);
					}
					else {
						client.setSendPacket(pkt);
					}
				}
			}
			
			else { // 当前正在处于非静止状态，不响应玩家操作，但是仍然要发送当前帧的状态包，保证每一帧都有记录
				// 传递一个向左站着的状态，但不表示动作切换，而是上一个动作延续
				Packet pkt = new Packet(frameCount,EntityState.STANDING_TORIGHT); 
				receivePktQueue.add(pkt);
				if(!isServer) {
					server.setSendPacket(pkt);
				}
				else {
					client.setSendPacket(pkt);
				}
			}
		}
	}
	
	private void updateBloodBar() { // 更新血条/血槽
		Blood blood1 = (Blood)entityMap.get(Constants.BLOOD1);
		Blood blood2 = (Blood)entityMap.get(Constants.BLOOD2);
		Entity player1 = entityMap.get(Constants.PLAYER1);
		Entity player2 = entityMap.get(Constants.PLAYER2);
		
		double scale1 = player1.getLifeValue() / blood1.getFullLife(); // 左血条缩短比例
		double scale2 = player2.getLifeValue() / blood2.getFullLife(); // 右血条缩短比例
		
		imgLocateMap.get(Constants.BLOOD1).setW(blood1.getWidth()*scale1);
		imgLocateMap.get(Constants.BLOOD2).setX(Constants.BLOOD2_X + blood2.getWidth()*(1-scale2));
		imgLocateMap.get(Constants.BLOOD2).setW(blood2.getWidth()*scale2);
	}
	
	private void updateEnergyBar() { // 更新能量条/能量槽
		
		Energy energy1 = (Energy)entityMap.get(Constants.ENERGY1);
		Energy energy2 = (Energy)entityMap.get(Constants.ENERGY2);
		Entity player1 = entityMap.get(Constants.PLAYER1);
		Entity player2 = entityMap.get(Constants.PLAYER2);
		
		if(player1.isActive()) {
			currentEnergy1++;
		}else {
			currentEnergy1 = 0;
		}
		if(player2.isActive()) {
			currentEnergy2++;
		}else {
			currentEnergy2 = 0;
		}
		
		double scale1 = currentEnergy1 / player1.getFullEnergy(); // 左能量条增长比例
		double scale2 = currentEnergy2 / player2.getFullEnergy(); // 右能量条增长比例
		
		// 限制在[0,1]
		if(scale1 >= 1) 
			scale1 = 1;
		if(scale2 >= 1)
			scale2 = 1;
		
		// 更新能量槽
		imgLocateMap.get(Constants.ENERGYBAR1).setX(Constants.PLAYER1_INIT_X+player1.getDeltaX());
		imgLocateMap.get(Constants.ENERGYBAR1).setY(Constants.PLAYER1_INIT_Y+player1.getDeltaY()
		+ Constants.ENERGYBAR_DELTAY);
		
		imgLocateMap.get(Constants.ENERGYBAR2).setX(Constants.PLAYER2_INIT_X+player2.getDeltaX());
		imgLocateMap.get(Constants.ENERGYBAR2).setY(Constants.PLAYER2_INIT_Y+player2.getDeltaY()
		+ Constants.ENERGYBAR_DELTAY);
		
		// 更新能量条
		imgLocateMap.get(Constants.ENERGY1).setX(Constants.PLAYER1_INIT_X+player1.getDeltaX());
		imgLocateMap.get(Constants.ENERGY1).setY(Constants.PLAYER1_INIT_Y+player1.getDeltaY()
		+ Constants.ENERGY_DELTAY);
		
		imgLocateMap.get(Constants.ENERGY2).setX(Constants.PLAYER2_INIT_X+player2.getDeltaX());
		imgLocateMap.get(Constants.ENERGY2).setY(Constants.PLAYER2_INIT_Y+player2.getDeltaY()
		+ Constants.ENERGY_DELTAY);
		
		imgLocateMap.get(Constants.ENERGY1).setW(energy1.getWidth()*scale1);
		imgLocateMap.get(Constants.ENERGY2).setW(energy2.getWidth()*scale2);
	}
	
	private void updatePlayer1AttackEntity() { // 更新玩家1攻击实体
		if(player1_attackEntity == null) { // 攻击实体尚未被设置，则不可能存在于图片定位字典中
			return;
		}
		else if(!player1_attackEntity.isActive()) { // 攻击实体被设置过
			String name = player1_attackEntity.getCurrentAttackName();
			if(imgLocateMap.containsKey(name)) { // 还存在于图片定位字典中，说明刚刚死亡
				removeImageLocate(name);
			}
			
		}else {
			Entity player1 = entityMap.get(Constants.PLAYER1);
			String name = player1_attackEntity.getCurrentAttackName();
			Image img = player1_attackEntity.getCurrentAttackImg();
			if(!imgLocateMap.containsKey(name)) { // 没在图片定位字典中，说明刚刚生成
				addImageLocate(name, 
						new ImageLocate(
						img, 
						Constants.PLAYER1_INIT_X + player1_attackEntity.getDeltaX(), 
						Constants.PLAYER1_INIT_Y, 
						player1_attackEntity.getWidth(),
						player1_attackEntity.getHeight()));
			}else { // 否则让攻击实体向前移动
				if(player1_attackEntity.isLeft()) {
					player1_attackEntity.moveLeft();
				}else {
					player1_attackEntity.moveRight();
				}
				
				double dx = player1_attackEntity.getDeltaX();
				double dist = player1_attackEntity.getCurrentAttackDist();
				// 如果攻击实体移动距离超过攻击距离
				if(!player1_attackEntity.isLeft() && dx >= dist) { 
					dx = dist;
					player1_attackEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				else if(player1_attackEntity.isLeft() && dx <= -dist) { 
					dx = -dist;
					player1_attackEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				// 达到边界，也算最大距离
				else if(Constants.PLAYER1_INIT_X + dx <= 0 ||
					Constants.PLAYER1_INIT_X + dx + player1_attackEntity.getWidth()
					>= Constants.BACKGROUND_W) { 
					player1_attackEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				imgLocateMap.get(name).setX(
						Constants.PLAYER1_INIT_X + dx);
				
				if(!player1_attackEntity.isActive()) {
					player2Hurt = false; // 下一次可继续伤害玩家2
					if(player1.isAttacking()) {
						player1.resetToStand();
					}
				}
			}
		}
	}
	
	private void updatePlayer1DefendEntity() { // 更新玩家1防御实体
		if(player1_defendEntity == null) { // 防御实体尚未被设置，则不可能存在于图片定位字典中
			return;
		}
		else if(!player1_defendEntity.isActive()) { // 防御实体被设置过
			String name = player1_defendEntity.getDefendName();
			if(imgLocateMap.containsKey(name)) { // 还存在于图片定位字典中，说明刚刚死亡
				removeImageLocate(name);
			}
			
		}else {
			Entity player1 = entityMap.get(Constants.PLAYER1);
			String name = player1_defendEntity.getDefendName();
			Image img = player1_defendEntity.getCurrentDefendImg();
			if(!imgLocateMap.containsKey(name)) { // 没在图片定位字典中，说明刚刚生成
				addImageLocate(name, 
						new ImageLocate(
						img, 
						Constants.PLAYER1_INIT_X + player1_defendEntity.getDeltaX(), 
						Constants.PLAYER1_INIT_Y, 
						player1_defendEntity.getWidth(),
						player1_defendEntity.getHeight()));
			}else { // 否则让防御实体向前移动
				if(player1_defendEntity.isLeft()) {
					player1_defendEntity.moveLeft();
				}else {
					player1_defendEntity.moveRight();
				}
				
				double dx = player1_defendEntity.getDeltaX();
				double dist = player1_defendEntity.getDefendDist();
				// 如果防御实体移动距离超过攻击距离
				if(!player1_defendEntity.isLeft() && dx >= dist) { 
					dx = dist;
					player1_defendEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				else if(player1_defendEntity.isLeft() && dx <= -dist) { 
					dx = -dist;
					player1_defendEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				// 达到边界，也算最大距离
				else if(Constants.PLAYER1_INIT_X + dx <= 0 ||
					Constants.PLAYER1_INIT_X + dx + player1_defendEntity.getWidth()
					>= Constants.BACKGROUND_W) { 
					player1_defendEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				imgLocateMap.get(name).setX(
						Constants.PLAYER1_INIT_X + dx);
				
				if(!player1_defendEntity.isActive()) {
					if(player1.isDefending()) {
						player1.resetToStand();
					}
				}
			}
		}
	}
	
	private void updatePlayer2AttackEntity() { // 更新玩家1攻击实体
		if(player2_attackEntity == null) { // 攻击实体尚未被设置，则不可能存在于图片定位字典中
			return;
		}
		else if(!player2_attackEntity.isActive()) { // 攻击实体被设置过
			String name = player2_attackEntity.getCurrentAttackName();
			if(imgLocateMap.containsKey(name)) { // 还存在于图片定位字典中，说明刚刚死亡
				removeImageLocate(name);
			}
			
		}else {
			Entity player2 = entityMap.get(Constants.PLAYER2);
			String name = player2_attackEntity.getCurrentAttackName();
			Image img = player2_attackEntity.getCurrentAttackImg();
			if(!imgLocateMap.containsKey(name)) { // 没在图片定位字典中，说明刚刚生成
				addImageLocate(name, 
						new ImageLocate(
						img, 
						Constants.PLAYER2_INIT_X + player2_attackEntity.getDeltaX(), 
						Constants.PLAYER2_INIT_Y, 
						player2_attackEntity.getWidth(),
						player2_attackEntity.getHeight()));
			}else { // 否则让攻击实体向前移动
				if(player2_attackEntity.isLeft()) {
					player2_attackEntity.moveLeft();
				}else {
					player2_attackEntity.moveRight();
				}
				
				double dx = player2_attackEntity.getDeltaX();
				double dist = player2_attackEntity.getCurrentAttackDist();
				// 如果攻击实体移动距离超过攻击距离
				if(!player2_attackEntity.isLeft() && dx >= dist) { 
					dx = dist;
					player2_attackEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				else if(player2_attackEntity.isLeft() && dx <= -dist) { 
					dx = -dist;
					player2_attackEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				// 达到边界，也算最大距离
				else if(Constants.PLAYER2_INIT_X + dx <= 0 ||
					Constants.PLAYER2_INIT_X + dx + player2_attackEntity.getWidth()
					>= Constants.BACKGROUND_W) { 
					player2_attackEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				imgLocateMap.get(name).setX(
						Constants.PLAYER2_INIT_X + dx);
				
				if(!player2_attackEntity.isActive()) {
					player1Hurt = false; // 下一次可继续伤害玩家1
					if(player2.isAttacking()) {
						player2.resetToStand();
					}
				}
			}
		}
	}
	
	private void updatePlayer2DefendEntity() { // 更新玩家1防御实体
		if(player2_defendEntity == null) { // 防御实体尚未被设置，则不可能存在于图片定位字典中
			return;
		}
		else if(!player2_defendEntity.isActive()) { // 防御实体被设置过
			String name = player2_defendEntity.getDefendName();
			if(imgLocateMap.containsKey(name)) { // 还存在于图片定位字典中，说明刚刚死亡
				removeImageLocate(name);
			}
			
		}else {
			Entity player2 = entityMap.get(Constants.PLAYER2);
			String name = player2_defendEntity.getDefendName();
			Image img = player2_defendEntity.getCurrentDefendImg();
			if(!imgLocateMap.containsKey(name)) { // 没在图片定位字典中，说明刚刚生成
				addImageLocate(name, 
						new ImageLocate(
						img, 
						Constants.PLAYER2_INIT_X + player2_defendEntity.getDeltaX(), 
						Constants.PLAYER2_INIT_Y, 
						player2_defendEntity.getWidth(),
						player2_defendEntity.getHeight()));
			}else { // 否则让防御实体向前移动
				if(player2_defendEntity.isLeft()) {
					player2_defendEntity.moveLeft();
				}else {
					player2_defendEntity.moveRight();
				}
				
				double dx = player2_defendEntity.getDeltaX();
				double dist = player2_defendEntity.getDefendDist();
				// 如果防御实体移动距离超过攻击距离
				if(!player2_defendEntity.isLeft() && dx >= dist) { 
					dx = dist;
					player2_defendEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				else if(player2_defendEntity.isLeft() && dx <= -dist) { 
					dx = -dist;
					player2_defendEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				// 达到边界，也算最大距离
				else if(Constants.PLAYER2_INIT_X + dx <= 0 ||
					Constants.PLAYER2_INIT_X + dx + player2_defendEntity.getWidth()
					>= Constants.BACKGROUND_W) { 
					player2_defendEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				imgLocateMap.get(name).setX(
						Constants.PLAYER2_INIT_X + dx);
				
				if(!player2_defendEntity.isActive()) {
					if(player2.isDefending()) {
						player2.resetToStand();
					}
				}
			}
		}
	}
	
	private void collisionDetect() { // 碰撞检测
		collisionDetectA1A2(); // 玩家1攻击实体 vs 玩家2攻击实体
		collisionDetectA1D2(); // 玩家1攻击实体 vs 玩家2防御实体
		collisionDetectA2D1(); // 玩家2攻击实体 vs 玩家1防御实体
		collisionDetectA1P2(); // 玩家1攻击实体 vs 玩家2实体
		collisionDetectA2P1(); // 玩家2攻击实体 vs 玩家1实体
	}
	
	private void collisionDetectA1A2(){ // 碰撞检测：玩家1攻击实体 vs 玩家2攻击实体
		
		if(player1_attackEntity != null && player1_attackEntity.isActive() 
				&& player2_attackEntity != null && player2_attackEntity.isActive()) {
			double x1 = Constants.PLAYER1_INIT_X + player1_attackEntity.getDeltaX();
			double y1 = Constants.PLAYER1_INIT_Y + player1_attackEntity.getDeltaY();
			double w1 = player1_attackEntity.getWidth();
			double h1 = player1_attackEntity.getHeight();
			double x2 = Constants.PLAYER2_INIT_X + player2_attackEntity.getDeltaX();
			double y2 = Constants.PLAYER2_INIT_Y + player2_attackEntity.getDeltaY();
			double w2 = player2_attackEntity.getWidth();
			double h2 = player2_attackEntity.getHeight();
			
			boolean isCollided = isCollided(x1, y1, w1, h1, x2, y2, w2, h2);
			if(isCollided) { // 如果碰撞
				double attackValue1 = player1_attackEntity.getCurrentAttackValue();
				double attackValue2 = player2_attackEntity.getCurrentAttackValue();
				player1_attackEntity.getHurt(attackValue2);
				player2_attackEntity.getHurt(attackValue1);
			}
			// 检测是否死亡
			Entity player1 = entityMap.get(Constants.PLAYER1);
			if(!player1_attackEntity.isActive()) {
				if(player1.isAttacking()) {
					player1.resetToStand();
				}
			}
			Entity player2 = entityMap.get(Constants.PLAYER2);
			if(!player2_attackEntity.isActive()) {
				if(player2.isAttacking()) {
					player2.resetToStand();
				}
			}
		}
	}
	
	private void collisionDetectA1D2(){ // 碰撞检测：玩家1攻击实体 vs 玩家2防御实体
		if(player1_attackEntity != null && player1_attackEntity.isActive() 
				&& player2_defendEntity != null && player2_defendEntity.isActive()) {
			double x1 = Constants.PLAYER1_INIT_X + player1_attackEntity.getDeltaX();
			double y1 = Constants.PLAYER1_INIT_Y + player1_attackEntity.getDeltaY();
			double w1 = player1_attackEntity.getWidth();
			double h1 = player1_attackEntity.getHeight();
			double x2 = Constants.PLAYER2_INIT_X + player2_defendEntity.getDeltaX();
			double y2 = Constants.PLAYER2_INIT_Y + player2_defendEntity.getDeltaY();
			double w2 = player2_defendEntity.getWidth();
			double h2 = player2_defendEntity.getHeight();
			
			boolean isCollided = isCollided(x1, y1, w1, h1, x2, y2, w2, h2);
			if(isCollided) { // 如果碰撞
				double attackValue1 = player1_attackEntity.getCurrentAttackValue();
				double defendValue2 = player2_defendEntity.getDefendValue();
				player1_attackEntity.getHurt(defendValue2);
				player2_defendEntity.getHurt(attackValue1);
			}
			// 检测是否死亡
			Entity player1 = entityMap.get(Constants.PLAYER1);
			if(!player1_attackEntity.isActive()) {
				if(player1.isAttacking()) {
					player1.resetToStand();
				}
			}
			Entity player2 = entityMap.get(Constants.PLAYER2);
			if(!player2_defendEntity.isActive()) {
				if(player2.isDefending()) {
					player2.resetToStand();
				}
			}
		}
	}
	
	private void collisionDetectA2D1(){ // 碰撞检测：玩家2攻击实体 vs 玩家1防御实体
		if(player2_attackEntity != null && player2_attackEntity.isActive() 
				&& player1_defendEntity != null && player1_defendEntity.isActive()) {
			double x1 = Constants.PLAYER2_INIT_X + player2_attackEntity.getDeltaX();
			double y1 = Constants.PLAYER2_INIT_Y + player2_attackEntity.getDeltaY();
			double w1 = player2_attackEntity.getWidth();
			double h1 = player2_attackEntity.getHeight();
			double x2 = Constants.PLAYER1_INIT_X + player1_defendEntity.getDeltaX();
			double y2 = Constants.PLAYER1_INIT_Y + player1_defendEntity.getDeltaY();
			double w2 = player1_defendEntity.getWidth();
			double h2 = player1_defendEntity.getHeight();
			
			boolean isCollided = isCollided(x1, y1, w1, h1, x2, y2, w2, h2);
			if(isCollided) { // 如果碰撞
				double attackValue2 = player2_attackEntity.getCurrentAttackValue();
				double defendValue1 = player1_defendEntity.getDefendValue();
				player2_attackEntity.getHurt(defendValue1);
				player1_defendEntity.getHurt(attackValue2);
			}
			// 检测是否死亡
			Entity player2 = entityMap.get(Constants.PLAYER2);
			if(!player2_attackEntity.isActive()) {
				if(player2.isAttacking()) {
					player2.resetToStand();
				}
			}
			Entity player1 = entityMap.get(Constants.PLAYER1);
			if(!player1_defendEntity.isActive()) {
				if(player1.isDefending()) {
					player1.resetToStand();
				}
			}
		}
	}
	
	private void collisionDetectA1P2(){ // 碰撞检测：玩家1攻击实体 vs 玩家2实体
	
		Entity player1 = entityMap.get(Constants.PLAYER1);
		Entity player2 = entityMap.get(Constants.PLAYER2);
		
		if(player1_attackEntity != null && player1_attackEntity.isActive() 
				&& player2 != null && player2.isActive()) {
			double x1 = Constants.PLAYER1_INIT_X + player1_attackEntity.getDeltaX();
			double y1 = Constants.PLAYER1_INIT_Y + player1_attackEntity.getDeltaY();
			double w1 = player1_attackEntity.getWidth();
			double h1 = player1_attackEntity.getHeight();
			double x2 = Constants.PLAYER2_INIT_X + player2.getDeltaX();
			double y2 = Constants.PLAYER2_INIT_Y + player2.getDeltaY();
			double w2 = player2.getWidth();
			double h2 = player2.getHeight();
			
			boolean isCollided = isCollided(x1, y1, w1, h1, x2, y2, w2, h2);
			if(isCollided && !player2Hurt) { // 如果是第一次碰撞
				double attackValue1 = player1_attackEntity.getCurrentAttackValue();
				double hurt = player2.getHurt(attackValue1);
				if(hurt > 0) { 
					player2Hurt = true;
					currentEnergy2 += hurt*Constants.ENERGY_HURT_SCALE; // 如果玩家2受伤，则能量值激增
					boolean opposite = player1.isLeft() ^ player2.isLeft();
					if(opposite) // 如果面对面，则玩家2后退几步，否则玩家2前进几步
						player2.setBack(true);
					
					if(player2.isLeft()) {
						player2.setState(EntityState.MOVING_TOLEFT);
					}else {
						player2.setState(EntityState.MOVING_TORIGHT);
					}
				}
			}
			if(player2Hurt) { // 如果成功攻击到了玩家, 则直接进入倒计时(若本身达到最大距离，则加速)
				player1_attackEntity.countEndFrame(); 
			}
			
			// 检测是否死亡
			if(!player1_attackEntity.isActive()) {
				player2Hurt = false;
				if(player1.isAttacking()) {
					player1.resetToStand();
				}
			}
			
			if(!player2.isActive() && !isGameOver) { // 玩家2刚刚死亡，游戏结束
				
				if(player2.isLeft()) {
					player2.setState(EntityState.LYING_TOLEFT);
				}else {
					player2.setState(EntityState.LYING_TORIGHT);
				}
				
				isGameOver = true;
			}
		}
	}
	
	private void collisionDetectA2P1(){ // 碰撞检测：玩家2攻击实体 vs 玩家1实体
		
		Entity player1 = entityMap.get(Constants.PLAYER1);
		Entity player2 = entityMap.get(Constants.PLAYER2);
		
		if(player2_attackEntity != null && player2_attackEntity.isActive() 
				&& player1 != null && player1.isActive()) {
			double x1 = Constants.PLAYER2_INIT_X + player2_attackEntity.getDeltaX();
			double y1 = Constants.PLAYER2_INIT_Y + player2_attackEntity.getDeltaY();
			double w1 = player2_attackEntity.getWidth();
			double h1 = player2_attackEntity.getHeight();
			double x2 = Constants.PLAYER1_INIT_X + player1.getDeltaX();
			double y2 = Constants.PLAYER1_INIT_Y + player1.getDeltaY();
			double w2 = player1.getWidth();
			double h2 = player1.getHeight();
			
			boolean isCollided = isCollided(x1, y1, w1, h1, x2, y2, w2, h2);
			if(isCollided && !player1Hurt) { // 如果是第一次碰撞
				double attackValue2 = player2_attackEntity.getCurrentAttackValue();
				double hurt = player1.getHurt(attackValue2);
				if(hurt > 0) { 
					player1Hurt = true;
					currentEnergy1 += hurt * Constants.ENERGY_HURT_SCALE; // 如果玩家1受伤，则能量值激增
					boolean opposite = player1.isLeft() ^ player2.isLeft();
					if(opposite) // 如果面对面，则玩家1后退几步，否则玩家1前进几步
						player1.setBack(true);
					
					if(player1.isLeft()) {
						player1.setState(EntityState.MOVING_TOLEFT);
					}else {
						player1.setState(EntityState.MOVING_TORIGHT);
					}
				}
			}
			if(player1Hurt) { // 如果成功攻击到了玩家, 则直接进入倒计时(若本身达到最大距离，则加速)
				player2_attackEntity.countEndFrame(); 
			}
			
			// 检测是否死亡
			if(!player2_attackEntity.isActive()) {
				player1Hurt = false;
				if(player2.isAttacking()) {
					player2.resetToStand();
				}
			}
			
			if(!player1.isActive()  && !isGameOver) { // 玩家1刚刚死亡，游戏结束
				
				if(player1.isLeft()) {
					player1.setState(EntityState.LYING_TOLEFT);
				}else {
					player1.setState(EntityState.LYING_TORIGHT);
				}
				
				isGameOver = true;
			}
		}
	}
	
	private boolean isCollided(double x1,double y1,double w1,double h1,
			double x2, double y2, double w2, double h2) { 
		// 是否两个矩阵产生碰撞，用于碰撞检测
		boolean xCollided = false; // x轴是否碰撞
		boolean yCollided = false; // y轴是否碰撞
		
		if(x1 + w1 < x2 || x2 + w2 < x1) { // x轴相离
			xCollided = false;
		}else { // x轴相交/相切
			xCollided = true;
		}
		
		if(y1 + h1 < y2 || y2 + h2 < y1) { // y轴相离
			yCollided = false;
		}else { // y轴相交/相切
			yCollided = true;
		}
		
		return xCollided & yCollided; // x轴且y轴碰撞，则两个矩形碰撞
	}
	
	private void parseQueue() { // 解析操作/状态队列
		
		if(!sendPktQueue.isEmpty() && !receivePktQueue.isEmpty()) {
			int player1_frame = sendPktQueue.get(sendPktQueueIdx).getFrame(); // 自己队列中的最小帧
			int player2_frame = receivePktQueue.get(receivePktQueueIdx).getFrame(); // 对手队列中的最小帧
			
			if(player1_frame > player2_frame) { // 说明对手的同步帧非该最小帧
				
				// 寻找对手的同步帧
				while(player1_frame > player2_frame && receivePktQueueIdx < receivePktQueue.size()-1) {
					receivePktQueueIdx++;
					player2_frame = receivePktQueue.get(receivePktQueueIdx).getFrame();
				}
				
				if(player1_frame > player2_frame) { // 对手的同步帧还未到，直接返回，等待
					return;
				}
				else if(player1_frame < player2_frame) { // 对手的最近帧已经超过自己，说明中间丢包，只能寻找自己的同步帧
					// 注意，由于自己的包是一定同步的，因此一定能匹配到对手的最近帧
					while(player1_frame < player2_frame && sendPktQueueIdx < sendPktQueue.size()-1) {
						sendPktQueueIdx++;
						player1_frame = sendPktQueue.get(sendPktQueueIdx).getFrame();
					}
					if(player1_frame == player2_frame) { // 帧已经同步
						parsePlayer1Action();
						parsePlayer2Action();
					}
				}
				else { // 帧已经同步
					parsePlayer1Action();
					parsePlayer2Action();
				}
				
			}
			else if(player1_frame < player2_frame) { // 自己的最小帧非同步帧
				// 寻找自己的同步帧
				// 注意，由于自己的包是一定同步的，因此一定能匹配到对手的同步帧
				while(player1_frame < player2_frame && sendPktQueueIdx < sendPktQueue.size()-1) {
					sendPktQueueIdx++;
					player1_frame = sendPktQueue.get(sendPktQueueIdx).getFrame();
				}
				if(player1_frame == player2_frame) { // 帧已经同步
					parsePlayer1Action();
					parsePlayer2Action();
				}
			}
			else { // 帧已经同步
				parsePlayer1Action();
				parsePlayer2Action();
			}
		}
	}
	
	private void parsePlayer1Action() { // 解析完操作队列并找到同步帧后，进而解析自己的同步帧的动作
		if(sendPktQueue.isEmpty())
			return;
		EntityState player1_action = sendPktQueue.get(sendPktQueueIdx).getAction(); // 自己的同步帧动作
		sendPktQueueIdx++;
		
		// 解析自己的动作
		Entity player1 = entityMap.get(Constants.PLAYER1);
		if(player1_action == EntityState.STANDING_TOLEFT) { // 保持上一个状态的延续
			
		}
		else if(player1_action == EntityState.ATTACKING_KILL_TOLEFT || player1_action
				== EntityState.ATTACKING_KILL_TORIGHT) { // 如果是想使用必杀技
			if(currentEnergy1 < player1.getFullEnergy()) { // 但是能量值不足
				
			}else{
				player1.setState(player1_action); // 否则切换状态为同步帧的动作
			}
		}
		else {
			player1.setState(player1_action); // 否则切换状态为同步帧的动作
		}
		
		EntityState player1_state = player1.getState();
		removeText1(); // 删除文本
		
		switch (player1_state) {
		case MOVING_TOLEFT: // 向左移动
		{	
			if(player1.isBack()) { // 倒推
				player1.moveRight();
			}else {
				player1.moveLeft();
			}
			
			double deltaX = player1.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(Constants.PLAYER1_INIT_X + deltaX);
		}break;
		case MOVING_TORIGHT: // 向右移动
		{
			if(player1.isBack()) { // 倒推
				player1.moveLeft();
			}else {
				player1.moveRight();
			}
		
			double deltaX = player1.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(Constants.PLAYER1_INIT_X + deltaX);
		}break;
		case RUNNING_TOLEFT: // 向左冲刺
		{
			player1.runLeft();
			double deltaX = player1.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(Constants.PLAYER1_INIT_X + deltaX);
		}break;
		case RUNNING_TORIGHT: // 向右冲刺
		{
			player1.runRight();
			double deltaX = player1.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(Constants.PLAYER1_INIT_X + deltaX);
		}break;
		case JUMPING_TOLEFT: // 向左跳跃
		{
			player1.jump();
			double deltaY = player1.getDeltaY();
			
			imgLocateMap.get(Constants.PLAYER1).setY(Constants.PLAYER1_INIT_Y + deltaY);
		}break;
		case JUMPING_TORIGHT: // 向右跳跃
		{
			player1.jump();
			double deltaY = player1.getDeltaY();
			
			imgLocateMap.get(Constants.PLAYER1).setY(Constants.PLAYER1_INIT_Y + deltaY);
		}break;
		case LYING_TOLEFT: // 向左倒地
		{
			player1.lieDown();
			double deltaY = player1.getDeltaY();
			deltaY -= (player1.getWidth() - player1.getHeight());
			
			imgLocateMap.get(Constants.PLAYER1).setY(Constants.PLAYER1_INIT_Y + deltaY);
			imgLocateMap.get(Constants.PLAYER1).setW(player1.getHeight());
			imgLocateMap.get(Constants.PLAYER1).setH(player1.getWidth());
		}break;
		case LYING_TORIGHT: // 向右倒地
		{
			player1.lieDown();
			double deltaY = player1.getDeltaY();
			deltaY -= (player1.getWidth() - player1.getHeight());
			
			imgLocateMap.get(Constants.PLAYER1).setY(Constants.PLAYER1_INIT_Y + deltaY);
			imgLocateMap.get(Constants.PLAYER1).setW(player1.getHeight());
			imgLocateMap.get(Constants.PLAYER1).setH(player1.getWidth());
		}break;
		case DEFENDING_TOLEFT: // 向左防御
		{
			player1.defend();
			// 防御招式名称图片显示
			addDefendText1();
			// 设置防御实体
			setDefendEntity1();
			
		}break;
		case DEFENDING_TORIGHT: // 向右防御
		{
			player1.defend();
			// 防御招式名称图片显示
			addDefendText1();
			// 设置防御实体
			setDefendEntity1();
		}break;
		case ATTACKING_NEAR_TOLEFT: // 向左近攻
		{
			player1.attackNear();
			// 攻击招式名称图片显示
			addAttackText1();
			// 设置攻击实体
			setAttackEntity1();
		}break;
		case ATTACKING_NEAR_TORIGHT: // 向右近攻
		{
			player1.attackNear();
			// 攻击招式名称图片显示
			addAttackText1();
			// 设置攻击实体
			setAttackEntity1();
		}break;
		case ATTACKING_FAR_TOLEFT: // 向左远攻
		{
			player1.attackFar();
			// 攻击招式名称图片显示
			addAttackText1();
			// 设置攻击实体
			setAttackEntity1();
		}break;
		case ATTACKING_FAR_TORIGHT: // 向右远攻
		{
			player1.attackFar();
			// 攻击招式名称图片显示
			addAttackText1();
			// 设置攻击实体
			setAttackEntity1();
		}break;
		case ATTACKING_KILL_TOLEFT: // 向左必杀
		{
			boolean isKilled = false;
			if(player1_attackEntity != null && player1_attackEntity.isActive())
				isKilled = player1.attackKill(player1.getFullEnergy());
			else {
				isKilled = player1.attackKill(currentEnergy1);
			}
			if(!isKilled) // 如果不满阈值，则不能开启必杀技
				break;
			currentEnergy1 = 0;
			// 攻击招式名称图片显示
			addAttackText1();
			// 设置攻击实体
			setAttackEntity1();
		}break;
		case ATTACKING_KILL_TORIGHT: // 向右必杀
		{
			boolean isKilled = false;
			if(player1_attackEntity != null && player1_attackEntity.isActive())
				isKilled = player1.attackKill(player1.getFullEnergy());
			else {
				isKilled = player1.attackKill(currentEnergy1);
			}
			if(!isKilled) // 如果不满阈值，则不能开启必杀技
				break;
			currentEnergy1 = 0;
			// 攻击招式名称图片显示
			addAttackText1();
			// 设置攻击实体
			setAttackEntity1();
		}break;
		default:
			break;
		}
		imgLocateMap.get(Constants.PLAYER1).setImg(player1.getCurrentImage()); // 设置图片
	}
	
	private void parsePlayer2Action() { // 解析完操作队列并找到同步帧后，进而解析对手的同步帧的动作
		if(receivePktQueue.isEmpty())
			return;
		EntityState player2_action = receivePktQueue.get(receivePktQueueIdx).getAction(); // 对手的同步帧动作
		receivePktQueueIdx++;
		
		// 解析自己的动作
		Entity player2 = entityMap.get(Constants.PLAYER2);
		removeText2(); // 删除文本
		
		if(player2_action == EntityState.STANDING_TORIGHT) { // 保持上一个状态的延续
			
		}
		else if(player2_action == EntityState.ATTACKING_KILL_TOLEFT || player2_action
				== EntityState.ATTACKING_KILL_TORIGHT) { // 如果是想使用必杀技
			if(currentEnergy2 < player2.getFullEnergy()) { // 但是能量值不足
				
			}else{
				player2.setState(player2_action); // 否则切换状态为同步帧的动作
			}
		}
		else {
			player2.setState(player2_action); // 否则切换状态为同步帧的动作
		}
		
		EntityState player2_state = player2.getState();
		switch (player2_state) {
		case MOVING_TOLEFT: // 向左移动
		{
			if(player2.isBack()) { // 倒推
				player2.moveRight();
			}else {
				player2.moveLeft();
			}
			double deltaX = player2.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER2).setX(Constants.PLAYER2_INIT_X + deltaX);
		}break;
		case MOVING_TORIGHT: // 向右移动
		{
			if(player2.isBack()) { // 倒推
				player2.moveLeft();
			}else {
				player2.moveRight();
			}
			double deltaX = player2.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER2).setX(Constants.PLAYER2_INIT_X + deltaX);
		}break;
		case RUNNING_TOLEFT: // 向左冲刺
		{
			player2.runLeft();
			double deltaX = player2.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER2).setX(Constants.PLAYER2_INIT_X + deltaX);
		}break;
		case RUNNING_TORIGHT: // 向右冲刺
		{
			player2.runRight();
			double deltaX = player2.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER2).setX(Constants.PLAYER2_INIT_X + deltaX);
		}break;
		case JUMPING_TOLEFT: // 向左跳跃
		{
			player2.jump();
			double deltaY = player2.getDeltaY();
			
			imgLocateMap.get(Constants.PLAYER2).setY(Constants.PLAYER2_INIT_Y + deltaY);
		}break;
		case JUMPING_TORIGHT: // 向右跳跃
		{
			player2.jump();
			double deltaY = player2.getDeltaY();
			
			imgLocateMap.get(Constants.PLAYER2).setY(Constants.PLAYER2_INIT_Y + deltaY);
		}break;
		case LYING_TOLEFT: // 向左倒地
		{
			player2.lieDown();
			double deltaY = player2.getDeltaY();
			deltaY -= (player2.getWidth() - player2.getHeight());
			
			imgLocateMap.get(Constants.PLAYER2).setY(Constants.PLAYER2_INIT_Y + deltaY);
			imgLocateMap.get(Constants.PLAYER2).setW(player2.getHeight());
			imgLocateMap.get(Constants.PLAYER2).setH(player2.getWidth());
		}break;
		case LYING_TORIGHT: // 向右倒地
		{
			player2.lieDown();
			double deltaY = player2.getDeltaY();
			deltaY -= (player2.getWidth() - player2.getHeight());
			
			imgLocateMap.get(Constants.PLAYER2).setY(Constants.PLAYER2_INIT_Y + deltaY);
			imgLocateMap.get(Constants.PLAYER2).setW(player2.getHeight());
			imgLocateMap.get(Constants.PLAYER2).setH(player2.getWidth());
		}break;
		case DEFENDING_TOLEFT: // 向左防御
		{
			player2.defend();
			// 防御招式名称图片显示
			addDefendText2();
			// 设置防御实体
			setDefendEntity2();
		}break;
		case DEFENDING_TORIGHT: // 向右防御
		{
			player2.defend();
			// 防御招式名称图片显示
			addDefendText2();
			// 设置防御实体
			setDefendEntity2();
		}break;
		case ATTACKING_NEAR_TOLEFT: // 向左近攻
		{
			player2.attackNear();
			// 攻击招式名称图片显示
			addAttackText2();
			// 设置攻击实体
			setAttackEntity2();
		}break;
		case ATTACKING_NEAR_TORIGHT: // 向右近攻
		{
			player2.attackNear();
			// 攻击招式名称图片显示
			addAttackText2();
			// 设置攻击实体
			setAttackEntity2();
		}break;
		case ATTACKING_FAR_TOLEFT: // 向左远攻
		{
			player2.attackFar();
			// 攻击招式名称图片显示
			addAttackText2();
			// 设置攻击实体
			setAttackEntity2();
		}break;
		case ATTACKING_FAR_TORIGHT: // 向右远攻
		{
			player2.attackFar();
			// 攻击招式名称图片显示
			addAttackText2();
			// 设置攻击实体
			setAttackEntity2();
		}break;
		case ATTACKING_KILL_TOLEFT: // 向左必杀
		{
			boolean isKilled = false;
			if(player2_attackEntity != null && player2_attackEntity.isActive())
				isKilled = player2.attackKill(player2.getFullEnergy());
			else {
				isKilled = player2.attackKill(currentEnergy2);
			}
			if(!isKilled) // 如果不满阈值，则不能开启必杀技
				break;
			currentEnergy2 = 0;
			// 攻击招式名称图片显示
			addAttackText2();
			// 设置攻击实体
			setAttackEntity2();
		}break;
		case ATTACKING_KILL_TORIGHT: // 向右必杀
		{
			boolean isKilled = false;
			if(player2_attackEntity != null && player2_attackEntity.isActive())
				isKilled = player2.attackKill(player2.getFullEnergy());
			else {
				isKilled = player2.attackKill(currentEnergy2);
			}
			if(!isKilled) // 如果不满阈值，则不能开启必杀技
				break;
			currentEnergy2 = 0;
			// 攻击招式名称图片显示
			addAttackText2();
			// 设置攻击实体
			setAttackEntity2();
		}break;
		default:
			break;
		}
		imgLocateMap.get(Constants.PLAYER2).setImg(player2.getCurrentImage()); // 设置图片
	}
	
	private void updateFrame() { // 更新帧

		((ImagePane) pane).update(
				imgLocateMap.values(),
				textLocateMap.values());
	}
	
	public void addEntity(String id, Entity entity) { // 添加实体
		if(id != null && entity != null) {
			entityMap.put(id, entity);
		}
	}
	
	public void removeEntity(String id) { // 删除实体
		if(id != null) {
			entityMap.remove(id);
		}
	}
	
	public void addImageLocate(String id, ImageLocate imgLocate) { // 添加图片定位
		if(id != null && imgLocate != null) {
				imgLocateMap.put(id, imgLocate);
		}
	}
	
	public void removeImageLocate(String id) { // 删除图片定位
		if(id != null) {
			imgLocateMap.remove(id);
		}
	}

	public void addTextLocate(String id, TextLocate textLocate) { // 添加文本定位
		if(id != null && textLocate != null) {
			textLocateMap.put(id, textLocate);
		}
	}	
	
	public void removeTextLocate(String id) { // 删除文本定位
		if(id != null) {
			textLocateMap.remove(id);
		}
		onLaunch();
	}
	
	private void setDefendEntity1() { // 为玩家1设置防御实体
		// 如果上一个防御实体还没有消失，则不能发射下一个防御
		if(player1_defendEntity != null && player1_defendEntity.isActive()) {
			return; 
		}
		// 否则添加新防御实体
		Entity player1 = entityMap.get(Constants.PLAYER1);
		String name = player1.getDefendName();
		boolean isLeft = player1.isLeft();
		double dx = player1.getDeltaX();
		double dist = player1.getDefendDist();
		if(isLeft) {
			dx -= player1.getDefendWidth();
			dist -= dx;
		}else {
			dx += player1.getWidth(); 
			dist += dx;
		}
		DefendEntity defendEntity = new DefendEntity(name,isLeft,dx);
		defendEntity.setDefendName(player1.getDefendName());
		defendEntity.setDefendImg(player1.getDefendLeftImage(),player1.getDefendRightImage());
		defendEntity.setDefendValue(player1.getDefendValue());
		defendEntity.setDefendDist(dist);
		defendEntity.setDefendSpeed(player1.getDefendSpeed());
		defendEntity.setDefendWidth(player1.getDefendWidth());
		defendEntity.setDefendHeight(player1.getDefendHeight());
		player1_defendEntity = defendEntity;
	}
	
	private void setDefendEntity2() { // 为玩家2设置防御实体
		// 如果上一个防御实体还没有消失，则不能发射下一个防御
		if(player2_defendEntity != null && player2_defendEntity.isActive()) {
			return; 
		}
		// 否则设置新防御实体
		Entity player2 = entityMap.get(Constants.PLAYER2);
		String name = player2.getDefendName();
		boolean isLeft = player2.isLeft();
		double dx = player2.getDeltaX();
		double dist = player2.getDefendDist();
		if(isLeft) {
			dx -= player2.getDefendWidth();
			dist -= dx;
		}else {
			dx += player2.getWidth(); 
			dist += dx;
		}
		DefendEntity defendEntity = new DefendEntity(name,isLeft,dx);
		defendEntity.setDefendName(player2.getDefendName());
		defendEntity.setDefendImg(player2.getDefendLeftImage(),player2.getDefendRightImage());
		defendEntity.setDefendValue(player2.getDefendValue());
		defendEntity.setDefendDist(dist);
		defendEntity.setDefendSpeed(player2.getDefendSpeed());
		defendEntity.setDefendWidth(player2.getDefendWidth());
		defendEntity.setDefendHeight(player2.getDefendHeight());
		player2_defendEntity = defendEntity;
	}
	
	private void setAttackEntity1() { // 为玩家1设置攻击实体
		// 如果上一个攻击实体还没有消失，则不能发射下一个攻击
		if(player1_attackEntity != null && player1_attackEntity.isActive()) {
			return; 
		}
		// 否则添加新攻击实体
		Entity player1 = entityMap.get(Constants.PLAYER1);
		String name = player1.getCurrentAttackName();
		boolean isLeft = player1.isLeft();
		double dx = player1.getDeltaX();
		double dist = player1.getCurrentAttackDist();
		if(isLeft) {
			dx -= player1.getCurrentAttackWidth();
			dist -= dx;
		}else {
			dx += player1.getWidth();
			dist += dx;
		}
		AttackEntity attackEntity = new AttackEntity(name,isLeft,dx);
		attackEntity.setCurrentAttackName(player1.getCurrentAttackName());
		attackEntity.setCurrentAttackImg(player1.getCurrentAttackImg());
		attackEntity.setCurrentAttackValue(player1.getCurrentAttackValue());
		attackEntity.setCurrentAttackDist(dist);
		attackEntity.setCurrentAttackSpeed(player1.getCurrentAttackSpeed());
		attackEntity.setCurrentAttackWidth(player1.getCurrentAttackWidth());
		attackEntity.setCurrentAttackHeight(player1.getCurrentAttackHeight());
		player1_attackEntity = attackEntity;
	}
	
	private void setAttackEntity2() { // 为玩家2设置攻击实体
		// 如果上一个攻击实体还没有消失，则不能发射下一个攻击
		if(player2_attackEntity != null && player2_attackEntity.isActive()) {
			return; 
		}
		// 否则设置新攻击实体
		Entity player2 = entityMap.get(Constants.PLAYER2);
		String name = player2.getCurrentAttackName();
		boolean isLeft = player2.isLeft();
		double dx = player2.getDeltaX();
		double dist = player2.getCurrentAttackDist();
		if(isLeft) {
			dx -= player2.getCurrentAttackWidth();
			dist -= dx;
		}else {
			dx += player2.getWidth();
			dist += dx;
		}
		AttackEntity attackEntity = new AttackEntity(name,isLeft,dx);
		attackEntity.setCurrentAttackName(player2.getCurrentAttackName());
		attackEntity.setCurrentAttackImg(player2.getCurrentAttackImg());
		attackEntity.setCurrentAttackValue(player2.getCurrentAttackValue());
		attackEntity.setCurrentAttackDist(dist);
		attackEntity.setCurrentAttackSpeed(player2.getCurrentAttackSpeed());
		attackEntity.setCurrentAttackWidth(player2.getCurrentAttackWidth());
		attackEntity.setCurrentAttackHeight(player2.getCurrentAttackHeight());
		player2_attackEntity = attackEntity;
	}
	
	private void addDefendText1() { // 为玩家1添加防御文本
		Entity player1 = entityMap.get(Constants.PLAYER1);
		String defendName = player1.getDefendName()+Constants.TEXT;
		String dirStr = "main";
		String filePath = URL.toPngPath(dirStr, player1.getName(),defendName); 
		Image img = new Image(URL.toURL(filePath)); 
		ImageLocate imgLocate = new ImageLocate(
				img,
				Constants.PLAYER1_INIT_X + player1.getDeltaX(),
				Constants.PLAYER1_INIT_Y + player1.getDeltaY()
				+ Constants.TEXT_DELTAY,
				Constants.TEXT_W,
				Constants.TEXT_H);
		
		addImageLocate(defendName,imgLocate);
	}
	
	private void addDefendText2() { // 为玩家2添加防御文本
		Entity player2 = entityMap.get(Constants.PLAYER2);
		String defendName = player2.getDefendName()+Constants.TEXT;
		String dirStr = "main";
		String filePath = URL.toPngPath(dirStr, player2.getName(),defendName); 
		Image img = new Image(URL.toURL(filePath)); 
		ImageLocate imgLocate = new ImageLocate(
				img,
				Constants.PLAYER2_INIT_X + player2.getDeltaX(),
				Constants.PLAYER2_INIT_Y + player2.getDeltaY()
				+ Constants.TEXT_DELTAY,
				Constants.TEXT_W,
				Constants.TEXT_H);
		
		addImageLocate(defendName,imgLocate);
	}
	
	private void addAttackText1() { // 为玩家1添加攻击文本
		Entity player1 = entityMap.get(Constants.PLAYER1);
		String attackName = player1.getCurrentAttackName()+Constants.TEXT;
		String dirStr = "main";
		String filePath = URL.toPngPath(dirStr, player1.getName(),attackName); 
		Image img = new Image(URL.toURL(filePath)); 
		ImageLocate imgLocate = new ImageLocate(
				img,
				Constants.PLAYER1_INIT_X + player1.getDeltaX(),
				Constants.PLAYER1_INIT_Y + player1.getDeltaY()
				+ Constants.TEXT_DELTAY,
				Constants.TEXT_W,
				Constants.TEXT_H);
		
		addImageLocate(attackName,imgLocate);
	}
	
	private void addAttackText2() { // 为玩家2添加攻击文本
		Entity player2 = entityMap.get(Constants.PLAYER2);
		String attackName = player2.getCurrentAttackName()+Constants.TEXT;
		String dirStr = "main";
		String filePath = URL.toPngPath(dirStr, player2.getName(),attackName); 
		Image img = new Image(URL.toURL(filePath)); 
		ImageLocate imgLocate = new ImageLocate(
				img,
				Constants.PLAYER2_INIT_X + player2.getDeltaX(),
				Constants.PLAYER2_INIT_Y + player2.getDeltaY()
				+ Constants.TEXT_DELTAY,
				Constants.TEXT_W,
				Constants.TEXT_H);
		
		addImageLocate(attackName,imgLocate);
	}
	
	private void removeText1() { // 删除玩家1招式文本
		Entity player1 = entityMap.get(Constants.PLAYER1);
		removeImageLocate(player1.getDefendName()+Constants.TEXT);
		removeImageLocate(player1.getCurrentAttackName()+Constants.TEXT);
	}
	
	private void removeText2() { // 删除玩家2招式文本
		Entity player2 = entityMap.get(Constants.PLAYER2);
		removeImageLocate(player2.getDefendName()+Constants.TEXT);
		removeImageLocate(player2.getCurrentAttackName()+Constants.TEXT);
	}
	
	public void setPlayer1Name(EntityName name) { // 设置player1的名称
		player1_name = name;
	}
	
	public void setPlayer2Name(EntityName name) { // 设置player2的名称
		player2_name = name;
	}
	
	// 生命周期管理
	public void onLaunch() { // 页面启动设置
		reset(); // 先清空上一次启动时的字典
		initEntity(); // 初始化游戏实体
		
	}
	
	@Override
	public void onFinish() {
		super.onFinish();
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
	}
	
	@Override
	public void onLeave() {
		super.onLeave();
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onUpdate(double time) {
		frameCount++;
		
		if(!isStart) { // 未开始则进入游戏倒计时
			countDown();
		}
		else {
			// 更新玩家实体
			updatePlayer1(); // 更新玩家1
			updatePlayer2(mode); // 更新玩家2
			
			// 解析操作队列
			parseQueue();
//			parsePlayer1Action(); // !!!!!!!!!!!!!!!!!!test!!!!!!!!!!!!!!!!
//			parsePlayer2Action(); // !!!!!!!!!!!!!!!!!!test!!!!!!!!!!!!!!!!
			
			// 更新攻击/防御实体
			updatePlayer1AttackEntity();
			updatePlayer2AttackEntity();
			updatePlayer1DefendEntity();
			updatePlayer2DefendEntity();
			
			// 碰撞检测
			collisionDetect();
//			System.out.println("player1's life = "+ entityMap.get(Constants.PLAYER1).getLifeValue()); // test
//			System.out.println("player2's life = "+ entityMap.get(Constants.PLAYER2).getLifeValue()); // test
			
			// 更新血条
			updateBloodBar();
			
			// 更新能量条
			updateEnergyBar();
		}
		

		// 更新帧图
		updateFrame();
		super.onUpdate(time);
		
		// 检测游戏是否结束
		updateGameOver();
		
}
	
	@Override
	public void onStop() {
		super.onStop();
	}

}
