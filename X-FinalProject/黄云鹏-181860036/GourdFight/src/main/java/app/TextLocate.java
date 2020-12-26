package app;

public class TextLocate { // 文本定位类，封装文本绘制的内容、位置、属性等

	String text; // 文本内容
	double dx; // 目标左上角x轴坐标
	double dy; // 目标左上角y轴坐标
	
	
	// 初始化
	public TextLocate(String text, double dx, double dy) {
		this.text = text;
		this.dx = dx;
		this.dy = dy;
	}
	
	// Getter
	public String getText() { // 获取文本内容
		return text;
	}
	
	public double getX() { // 获取目标左上角x轴坐标
		return dx;
	}
	
	public double getY() { // 获取目标左上角y轴坐标
		return dy;
	}
	
	// Setter
	public void setText(String text) { // 设置文本内容
		this.text = text;
	}
	
	public void setX(double dx) { // 设置目标左上角x轴坐标
		this.dx = dx;
	}
	
	public void setY(double dy) { // 设置目标左上角y轴坐标
		this.dy = dy;
	}
	
}
