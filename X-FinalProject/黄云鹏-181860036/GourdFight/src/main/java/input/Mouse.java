package input;

import javafx.scene.input.MouseButton;

/**
 * 
 * @author 王浩天
 * @version	2020.12.24
 * @inherit MouseButton
 * @functions 鼠标按键枚举类，提供鼠标上的三个按键枚举
 * @properties Mouse.LEFT、Mouse.MIDDLE、Mouse.RIGHT
 * @methods 
 * 		getButton(): 获取按键的MouseButton枚举
 */

public enum Mouse {

	LEFT(MouseButton.PRIMARY), // 鼠标左键
	MIDDLE(MouseButton.MIDDLE), // 鼠标中键
	RIGHT(MouseButton.SECONDARY) // 鼠标右键
	;
	
	private final MouseButton button; // 鼠标按键枚举类，javaFX提供
	
	// 初始化
	private Mouse(MouseButton button) {
		this.button = button;
	}
	
	// Getter
	public MouseButton getButtoon() { // 获取鼠标按键
		return button;
	}
	
	public static Mouse find(MouseButton button) { // 查找鼠标按键
		for(Mouse m : values()) {
			if(m.button != null && m.button == button) {
				return m;
			}
		}
		return null;
	}
	
}
