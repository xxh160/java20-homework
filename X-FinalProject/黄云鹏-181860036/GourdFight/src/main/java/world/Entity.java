package world;

import java.util.HashMap;

import javafx.scene.image.Image;

public class Entity { // 游戏实体类，所有游戏角色、道具等的父类
	
	private String name; // 实体名称
	private EntityState state; // 实体状态(默认"朝左边站着")
	
	private boolean isMobile; // 是否可移动(默认不可移动)
	private boolean isActive; // 是否活跃(默认活跃)
	
	private double deltaX; // x轴位移
	private double deltaY; // y轴位移
	
	private HashMap<String,Image> imageMap; // 实体图片字典
	private HashMap<String,String> textMap; // 实体文本字典 
	
	// 初始化
	public Entity(String name) {
		imageMap = new HashMap<>();
		textMap = new HashMap<>();
		deltaX = 0;
		deltaY = 0;
		setName(name);
		setState(EntityState.STANDING_TOLEFT);
		setMobile(false);
		setActive(true);
	}
	
	// Getter
	public String getName() { // 获取名称
		return name;
	}
	
	public EntityState getState() { // 获取当前状态
		return state;
	}
	
	public boolean isMobile() { // 判断实体是否可移动
		return this.isMobile;
	}
	
	public boolean isActive() { // 判断实体是否活跃
		return this.isActive;
	}
	
	public Image getImage(String id) { // 获取图片
		return imageMap.get(id);
	}
	
	public String getText(String id) { // 获取文本
		return textMap.get(id);
	}
	
	public double getDeltaX() { // 获取x轴位移
		return deltaX;
	}
	
	public double getDeltaY() { // 获取y轴位移
		return deltaY;
	}
	
	// Setter
	public void setName(String name) { // 设置名称
		this.name = name;
	}
	
	public void setState(EntityState state) { // 设置实体状态
		this.state = state;
	}
	
	public void setMobile(boolean m) { // 设置实体是否可移动
		isMobile = m;
	}
	
	public void setActive(boolean a) { // 设置实体是否活跃
		isActive = a;
	}
	
	public void addImage(String id, Image img) { // 添加图片
		if(img != null && id != null) {
			imageMap.put(id, img);
		}
	}
	
	public void addText(String id, String text) { // 添加文本
		if(text != null && id != null) {
			textMap.put(id, text);
		}
	}
	
	// 移动
	public void moveRight() { // 向右移动
		if(isActive() && isMobile()) {
			deltaX += 1;
		}
	}
	
	public void moveLeft() { // 向左移动
		if(isActive() && isMobile()) {
			deltaX -= 1;
		}
	}
	
	public void moveUp() { // 向上移动
		if(isActive() && isMobile()) {
			deltaY -= 1;
		}
	}
	
	public void moveDown() { // 向下移动
		if(isActive() && isMobile()) {
			deltaY += 1;
		}
	}
	
	
}
