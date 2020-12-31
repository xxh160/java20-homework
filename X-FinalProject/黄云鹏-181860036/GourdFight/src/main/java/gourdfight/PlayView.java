package gourdfight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import app.ImageLocate;
import app.ImagePane;
import app.ImageSet;
import app.TextLocate;
import app.View;

import output.URL;
import world.AttackEntity;
import world.DefendEntity;
import world.Entity;
import world.EntityState;
import framework.*;
import input.Key;

import javafx.scene.image.Image;
import network.Packet;
import network.TCPClient;
import network.TCPServer;

public class PlayView extends View { // 游戏页面类
	
	HashMap<String, Entity> entityMap; // 游戏实体字典 
	LinkedHashMap<String, ImageLocate> imgLocateMap; // 游戏实体图片定位字典
	LinkedHashMap<String, TextLocate> textLocateMap; // 游戏实体文本定位字典
	
	AttackEntity player1_attackEntity; // 玩家1的攻击实体
	AttackEntity player2_attackEntity; // 玩家2的攻击实体
	DefendEntity player1_defendEntity; // 玩家1的防御实体
	DefendEntity player2_defendEntity; // 玩家2的防御实体
	
	private boolean isServer; // 是否作为服务器端(false 则为客户端, 默认为客户端)
	
	int frameCount; // 帧计数器
	
	TCPServer server; // 服务器端
	TCPClient client; // 客户端
	
	ArrayList<Packet> sendPktQueue; // 发送包队列，即自己的操作/状态序列
	ArrayList<Packet> receivePktQueue; // 接受包队列，即对手的操作/状态序列
	int sendPktQueueIdx; // 发送包队列指针
	int receivePktQueueIdx; // 接受包队列指针
	
	// 初始化
	public PlayView() {
		super(Constants.IMAGE_PANE);
		entityMap = new HashMap<>();
		imgLocateMap = new LinkedHashMap<>();
		textLocateMap = new LinkedHashMap<>();
		
		frameCount = 0;
		
		isServer = false; 
		server = new TCPServer("", Constants.PORT);
		client = new TCPClient("", Constants.PORT);
		
		sendPktQueue = new ArrayList<>();
		receivePktQueue = new ArrayList<>();
		sendPktQueueIdx = 0;
		receivePktQueueIdx = 0;
	}
	
	private void initEntity() { // 初始创建一些必要实体
		// 背景
		setBackground();
		// 玩家1
		setPlayer1();
		// 玩家2
		setPlayer2();
	}
	
	private void setBackground() { // 初设背景
		Entity background = new Entity(Constants.BACKGROUND);
		
		String[] bgs = new String[]{"door","desk","ground","hole","home"}; 
		
		String filePath = URL.toPngPath("main", "background",bgs[new Random().nextInt(5)]);
		Image background_img = new Image(URL.toURL(filePath)); 
		background.addImage(EntityState.STANDING_FORWARD, background_img);
		
		addEntity(Constants.BACKGROUND, background);
		
		ImageLocate background_imgLocate = new ImageLocate(
				background_img,
				Constants.BACKGROUND_X,
				Constants.BACKGROUND_Y,
				Constants.BACKGROUND_W,
				Constants.BACKGROUND_H);
		
		addImageLocate(Constants.BACKGROUND, background_imgLocate);
	}
	
	private void setPlayer1() { // 初设玩家1
		
		// 基本属性设置
		Entity player1 = new Entity(Constants.PLAYER1);
		player1.setMobile(true);
		player1.setState(EntityState.STANDING_TORIGHT);
	
		String nameStr = "greenBaby"; // 四娃测试
		
		
		// 状态图片设置
		for(EntityState state: EntityState.values()){
			String filePath = "";
			switch (state) {
			case STANDING_FORWARD:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TOLEFT);
				break;
			case STANDING_TOLEFT:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TOLEFT);
				break;
			case STANDING_TORIGHT:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TORIGHT);
				break;
			case MOVING_TOLEFT:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TOLEFT);
				break;
			case MOVING_TORIGHT:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TORIGHT);
				break;
			case RUNNING_TOLEFT:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TOLEFT);
				break;
			case RUNNING_TORIGHT:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TORIGHT);
				break;
			case LYING_TOLEFT:
				filePath = URL.toPngPath("main", nameStr, Constants.LYING_TOLEFT);
				break;
			case LYING_TORIGHT:
				filePath = URL.toPngPath("main", nameStr, Constants.LYING_TORIGHT);
				break;
			case JUMPING_TOLEFT:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TOLEFT);
				break;
			case JUMPING_TORIGHT:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TORIGHT);
				break;
			case DEFENDING_TOLEFT:
				filePath = URL.toPngPath("main", nameStr, Constants.ATTACKING_TOLEFT);
				break;
			case DEFENDING_TORIGHT:
				filePath = URL.toPngPath("main", nameStr, Constants.ATTACKING_TORIGHT);
				break;
			case ATTACKING_NEAR_TOLEFT:
				filePath = URL.toPngPath("main", nameStr, Constants.ATTACKING_TOLEFT);
				break;
			case ATTACKING_NEAR_TORIGHT:
				filePath = URL.toPngPath("main", nameStr, Constants.ATTACKING_TORIGHT);
				break;
			case ATTACKING_FAR_TOLEFT:
				filePath = URL.toPngPath("main", nameStr, Constants.ATTACKING_TOLEFT);
				break;
			case ATTACKING_FAR_TORIGHT:
				filePath = URL.toPngPath("main", nameStr, Constants.ATTACKING_TORIGHT);
				break;
			case ATTACKING_KILL_TOLEFT:
				filePath = URL.toPngPath("main", nameStr, Constants.ATTACKING_TOLEFT);
				break;
			case ATTACKING_KILL_TORIGHT:
				filePath = URL.toPngPath("main", nameStr, Constants.ATTACKING_TORIGHT);
				break;

			default:
				filePath = URL.toPngPath("main", nameStr, Constants.STANDING_TOLEFT);
				break;
			}
			
			Image img = new Image(URL.toURL(filePath));
			player1.addImage(state,img);
		}
		
		// 设置攻击实体图片
		String lFilePath = URL.toPngPath("main", nameStr, Constants.GREENBABY_ATTACKNEAR_LEFT);
		String rFilePath = URL.toPngPath("main", nameStr, Constants.GREENBABY_ATTACKNEAR_RIGHT);
		Image lImg = new Image(URL.toURL(lFilePath));
		Image rImg = new Image(URL.toURL(rFilePath));
		player1.setAttackNearName(Constants.GREENBABY_ATTACKNEAR_NAME);
		player1.setAttackNearImage(lImg, rImg); // 近攻
		
		lFilePath = URL.toPngPath("main", nameStr, Constants.GREENBABY_ATTACKFAR_LEFT);
		rFilePath = URL.toPngPath("main", nameStr, Constants.GREENBABY_ATTACKFAR_RIGHT);
		lImg = new Image(URL.toURL(lFilePath));
		rImg = new Image(URL.toURL(rFilePath));
		player1.setAttackFarName(Constants.GREENBABY_ATTACKFAR_NAME);
		player1.setAttackFarImage(lImg, rImg); // 远攻
		
		lFilePath = URL.toPngPath("main", nameStr, Constants.GREENBABY_ATTACKKILL_LEFT);
		rFilePath = URL.toPngPath("main", nameStr, Constants.GREENBABY_ATTACKKILL_RIGHT);
		lImg = new Image(URL.toURL(lFilePath));
		rImg = new Image(URL.toURL(rFilePath));
		player1.setAttackKillName(Constants.GREENBABY_ATTACKKILL_NAME);
		player1.setAttackKillImage(lImg, rImg); // 必杀
		
		// 添加实体
		addEntity(Constants.PLAYER1, player1);
				
		// 添加图片定位器
		ImageLocate player1_imgLocate = new ImageLocate(
				player1.getCurrentImage(),
				Constants.PLAYER1_INIT_X,
				Constants.PLAYER1_INIT_Y,
				player1.getWidth(),
				player1.getHeight());
		
		addImageLocate(Constants.PLAYER1, player1_imgLocate);
	}
	
	private void setPlayer2() { // 初设玩家2
		
	}
	
	// Setter
	private void setServer(boolean s) { // 设置是否是服务器端
		isServer = s;
	}
	
	private void launchNetwork() { // ！！！！！！！！！开启网络通信，仅作测试用例，应当为上层页面调用 ！！！！！！！！！！
		if(isServer) { // 自己为服务器端
			server.start();
		}
		else { // 自己为客户端
			client.start();
		}
	}
	
	private void reset() { // 复位实体字典
		entityMap.clear();
		imgLocateMap.clear();
		textLocateMap.clear();
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
	
	private void updatePlayer2() { // 更新玩家2
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
	
	private void updateAttackEntity() { // 更新攻击实体
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
					Constants.PLAYER1_INIT_X + dx + Constants.PLAYER1_ATTACK_W
					>= Constants.BACKGROUND_W) { 
					player1_attackEntity.countEndFrame(); // 计数最大距离驻留帧时
				}
				imgLocateMap.get(name).setX(
						Constants.PLAYER1_INIT_X + dx);
				
				if(!player1_attackEntity.isActive()) {
					if(player1.isAttacking()) {
						player1.resetToStand();
					}
				}
			}
		}
	}
	
	private void updateDefendEntity() { // 更新防御实体
		
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
			
		}else {
			player1.setState(player1_action); // 否则切换状态为同步帧的动作
		}
		
		EntityState player1_state = player1.getState();
		switch (player1_state) {
		case MOVING_TOLEFT: // 向左移动
		{
			player1.moveLeft();
			double deltaX = player1.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(Constants.PLAYER1_INIT_X + deltaX);
		}break;
		case MOVING_TORIGHT: // 向右移动
		{
			player1.moveRight();
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
		case DEFENDING_TOLEFT: // 向左防御
		{
			player1.defend();
		}break;
		case DEFENDING_TORIGHT: // 向右防御
		{
			player1.defend();
		}break;
		case ATTACKING_NEAR_TOLEFT: // 向左近攻
		{
			player1.attackNear();
			if(player1_attackEntity != null && player1_attackEntity.isActive()) {
				break; // 如果上一个攻击实体还没有消失，则不能发射下一个攻击
			}
			String name = player1.getCurrentAttackName();
			boolean isLeft = player1.isLeft();
			double dx = player1.getDeltaX();
			double dist = player1.getCurrentAttackDist();
			if(isLeft) {
				dx -= Constants.PLAYER1_ATTACK_W; 
				dist -= dx;
			}else {
				dx += Constants.PLAYER1_INIT_W; 
				dist += dx;
			}
			AttackEntity attackEntity = new AttackEntity(name,isLeft,dx);
			attackEntity.setCurrentAttackName(player1.getCurrentAttackName());
			attackEntity.setCurrentAttackImg(player1.getCurrentAttackImg());
			attackEntity.setCurrentAttackValue(player1.getCurrentAttackvalue());
			attackEntity.setCurrentAttackDist(dist);
			attackEntity.setCurrentAttackSpeed(player1.getCurrentAttackSpeed());
			attackEntity.setCurrentAttackWidth(player1.getCurrentAttackWidth());
			attackEntity.setCurrentAttackHeight(player1.getCurrentAttackHeight());
			player1_attackEntity = attackEntity;
		}break;
		case ATTACKING_NEAR_TORIGHT: // 向右近攻
		{
			player1.attackNear();
			if(player1_attackEntity != null && player1_attackEntity.isActive()) {
				break; // 如果上一个攻击实体还没有消失，则不能发射下一个攻击
			}
			String name = player1.getCurrentAttackName();
			boolean isLeft = player1.isLeft();
			double dx = player1.getDeltaX();
			double dist = player1.getCurrentAttackDist();
			if(isLeft) {
				dx -= Constants.PLAYER1_ATTACK_W; 
				dist -= dx;
			}else {
				dx += Constants.PLAYER1_INIT_W; 
				dist += dx;
			}
			AttackEntity attackEntity = new AttackEntity(name,isLeft,dx);
			attackEntity.setCurrentAttackName(player1.getCurrentAttackName());
			attackEntity.setCurrentAttackImg(player1.getCurrentAttackImg());
			attackEntity.setCurrentAttackValue(player1.getCurrentAttackvalue());
			attackEntity.setCurrentAttackDist(dist);
			attackEntity.setCurrentAttackSpeed(player1.getCurrentAttackSpeed());
			attackEntity.setCurrentAttackWidth(player1.getCurrentAttackWidth());
			attackEntity.setCurrentAttackHeight(player1.getCurrentAttackHeight());
			player1_attackEntity = attackEntity;
		}break;
		case ATTACKING_FAR_TOLEFT: // 向左远攻
		{
			player1.attackFar();
			if(player1_attackEntity != null && player1_attackEntity.isActive()) {
				break; // 如果上一个攻击实体还没有消失，则不能发射下一个攻击
			}
			String name = player1.getCurrentAttackName();
			boolean isLeft = player1.isLeft();
			double dx = player1.getDeltaX();
			double dist = player1.getCurrentAttackDist();
			if(isLeft) {
				dx -= Constants.PLAYER1_ATTACK_W; 
				dist -= dx;
			}else {
				dx += Constants.PLAYER1_INIT_W; 
				dist += dx;
			}
			AttackEntity attackEntity = new AttackEntity(name,isLeft,dx);
			attackEntity.setCurrentAttackName(player1.getCurrentAttackName());
			attackEntity.setCurrentAttackImg(player1.getCurrentAttackImg());
			attackEntity.setCurrentAttackValue(player1.getCurrentAttackvalue());
			attackEntity.setCurrentAttackDist(dist);
			attackEntity.setCurrentAttackSpeed(player1.getCurrentAttackSpeed());
			attackEntity.setCurrentAttackWidth(player1.getCurrentAttackWidth());
			attackEntity.setCurrentAttackHeight(player1.getCurrentAttackHeight());
			player1_attackEntity = attackEntity;
		}break;
		case ATTACKING_FAR_TORIGHT: // 向右远攻
		{
			player1.attackFar();
			if(player1_attackEntity != null && player1_attackEntity.isActive()) {
				break; // 如果上一个攻击实体还没有消失，则不能发射下一个攻击
			}
			String name = player1.getCurrentAttackName();
			boolean isLeft = player1.isLeft();
			double dx = player1.getDeltaX();
			double dist = player1.getCurrentAttackDist();
			if(isLeft) {
				dx -= Constants.PLAYER1_ATTACK_W; 
				dist -= dx;
			}else {
				dx += Constants.PLAYER1_INIT_W; 
				dist += dx;
			}
			AttackEntity attackEntity = new AttackEntity(name,isLeft,dx);
			attackEntity.setCurrentAttackName(player1.getCurrentAttackName());
			attackEntity.setCurrentAttackImg(player1.getCurrentAttackImg());
			attackEntity.setCurrentAttackValue(player1.getCurrentAttackvalue());
			attackEntity.setCurrentAttackDist(dist);
			attackEntity.setCurrentAttackSpeed(player1.getCurrentAttackSpeed());
			attackEntity.setCurrentAttackWidth(player1.getCurrentAttackWidth());
			attackEntity.setCurrentAttackHeight(player1.getCurrentAttackHeight());
			player1_attackEntity = attackEntity;
		}break;
		case ATTACKING_KILL_TOLEFT: // 向左必杀
		{
			player1.attackKill();
			if(player1_attackEntity != null && player1_attackEntity.isActive()) {
				break; // 如果上一个攻击实体还没有消失，则不能发射下一个攻击
			}
			String name = player1.getCurrentAttackName();
			boolean isLeft = player1.isLeft();
			double dx = player1.getDeltaX();
			double dist = player1.getCurrentAttackDist();
			if(isLeft) {
				dx -= Constants.PLAYER1_ATTACK_W; 
				dist -= dx;
			}else {
				dx += Constants.PLAYER1_INIT_W; 
				dist += dx;
			}
			AttackEntity attackEntity = new AttackEntity(name,isLeft,dx);
			attackEntity.setCurrentAttackName(player1.getCurrentAttackName());
			attackEntity.setCurrentAttackImg(player1.getCurrentAttackImg());
			attackEntity.setCurrentAttackValue(player1.getCurrentAttackvalue());
			attackEntity.setCurrentAttackDist(dist);
			attackEntity.setCurrentAttackSpeed(player1.getCurrentAttackSpeed());
			attackEntity.setCurrentAttackWidth(player1.getCurrentAttackWidth());
			attackEntity.setCurrentAttackHeight(player1.getCurrentAttackHeight());
			player1_attackEntity = attackEntity;
		}break;
		case ATTACKING_KILL_TORIGHT: // 向右必杀
		{
			player1.attackKill();
			if(player1_attackEntity != null && player1_attackEntity.isActive()) {
				break; // 如果上一个攻击实体还没有消失，则不能发射下一个攻击
			}
			String name = player1.getCurrentAttackName();
			boolean isLeft = player1.isLeft();
			double dx = player1.getDeltaX();
			double dist = player1.getCurrentAttackDist();
			if(isLeft) {
				dx -= Constants.PLAYER1_ATTACK_W; 
				dist -= dx;
			}else {
				dx += Constants.PLAYER1_INIT_W; 
				dist += dx;
			}
			AttackEntity attackEntity = new AttackEntity(name,isLeft,dx);
			attackEntity.setCurrentAttackName(player1.getCurrentAttackName());
			attackEntity.setCurrentAttackImg(player1.getCurrentAttackImg());
			attackEntity.setCurrentAttackValue(player1.getCurrentAttackvalue());
			attackEntity.setCurrentAttackDist(dist);
			attackEntity.setCurrentAttackSpeed(player1.getCurrentAttackSpeed());
			attackEntity.setCurrentAttackWidth(player1.getCurrentAttackWidth());
			attackEntity.setCurrentAttackHeight(player1.getCurrentAttackHeight());
			player1_attackEntity = attackEntity;
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
		Entity player2 = entityMap.get(Constants.PLAYER1);
		if(player2_action == EntityState.STANDING_TOLEFT) { // 保持上一个状态的延续
			
		}else {
			player2.setState(player2_action); // 否则切换状态为同步帧的动作
		}
		
		EntityState player2_state = player2.getState();
		switch (player2_state) {
		case MOVING_TOLEFT: // 向左移动
		{
			player2.moveLeft();
			double deltaX = player2.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(Constants.PLAYER1_INIT_X + deltaX);
		}break;
		case MOVING_TORIGHT: // 向右移动
		{
			player2.moveRight();
			double deltaX = player2.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(Constants.PLAYER1_INIT_X + deltaX);
		}break;
		case RUNNING_TOLEFT: // 向左冲刺
		{
			player2.runLeft();
			double deltaX = player2.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(Constants.PLAYER1_INIT_X + deltaX);
		}break;
		case RUNNING_TORIGHT: // 向右冲刺
		{
			player2.runRight();
			double deltaX = player2.getDeltaX();
			
			imgLocateMap.get(Constants.PLAYER1).setX(Constants.PLAYER1_INIT_X + deltaX);
		}break;
		case JUMPING_TOLEFT: // 向左跳跃
		{
			player2.jump();
			double deltaY = player2.getDeltaY();
			
			imgLocateMap.get(Constants.PLAYER1).setY(Constants.PLAYER1_INIT_Y + deltaY);
		}break;
		case JUMPING_TORIGHT: // 向右跳跃
		{
			player2.jump();
			double deltaY = player2.getDeltaY();
			
			imgLocateMap.get(Constants.PLAYER1).setY(Constants.PLAYER1_INIT_Y + deltaY);
		}break;
		case DEFENDING_TOLEFT: // 向左防御
		{
			player2.defend();
		}break;
		case DEFENDING_TORIGHT: // 向右防御
		{
			player2.defend();
		}break;
		case ATTACKING_NEAR_TOLEFT: // 向左近攻
		{
			player2.attackNear();
		}break;
		case ATTACKING_NEAR_TORIGHT: // 向右近攻
		{
			player2.attackNear();
		}break;
		case ATTACKING_FAR_TOLEFT: // 向左远攻
		{
			player2.attackFar();
		}break;
		case ATTACKING_FAR_TORIGHT: // 向右远攻
		{
			player2.attackFar();
		}break;
		case ATTACKING_KILL_TOLEFT: // 向左必杀
		{
			player2.attackKill();
		}break;
		case ATTACKING_KILL_TORIGHT: // 向右必杀
		{
			player2.attackKill();
		}break;
		default:
			break;
		}
		imgLocateMap.get(Constants.PLAYER1).setImg(player2.getCurrentImage()); // 设置图片
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
	
	// 页面生命周期管理
	
	
	// 生命周期管理
	// 生命周期管理
	public void onLaunch() { // 页面启动设置
		reset(); // 先清空上一次启动时的字典
//		setServer(true); // ！！！！！！！！！！！！！！！！！！！测试用例，应当在上层页面设置！！！！！！！！！！！！！！
//		launchNetwork(); // ！！！！！！！！！！！！！！！！！！！测试用例，应当在上层页面调用！！！！！！！！！！！！！！
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
		
		// 更新玩家实体
		updatePlayer1(); // 更新玩家1
//		updatePlayer2(); // 更新玩家2 // !!!!!!!!!!!!!test!!!!!!!!!!!!!!!!
		
		// 解析操作队列
//		parseQueue(); // !!!!!!!!!!!!!!!!!!test!!!!!!!!!!!!!!!!
		parsePlayer1Action(); // !!!!!!!!!!!!!!!!!!test!!!!!!!!!!!!!!!!
		
		// 更新攻击/防御实体
		updateAttackEntity();
		updateDefendEntity();
		
		// 更新帧
		updateFrame();
		
		super.onUpdate(time);
	}
	
	
	@Override
	public void onStop() {
		super.onStop();
	}
	

}
