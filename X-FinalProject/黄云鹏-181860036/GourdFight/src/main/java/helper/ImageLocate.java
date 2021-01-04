package helper;

import javafx.scene.image.Image;

/**
 * 
 * @author 王浩天
 * @version	2020.12.24
 * @inherit 
 * @functions 提供图片以及图片应该绘制的位置和大小信息
 * @properties Image、dx、dy、dw、dh
 * @methods  	
 */

public class ImageLocate { // 图片定位类，用于封装图片应该放置的位置和大小
	
	private Image img; // 图片
	private double dx; // 目标左上角x轴坐标
	private double dy; // 目标左上角y轴坐标
	private double dw; // 目标宽度
	private double dh; // 目标高度

	// 初始化
	public ImageLocate(Image img,double dx, double dy, double dw, double dh) {
		this.img = img;
		this.dx = dx;
		this.dy = dy;
		this.dw = dw;
		this.dh = dh;
	}
	
	// Getter
	public double getX() { // 获取目标左上角x轴坐标
		return dx;
	}
	
	public double getY() { // 获取目标左上角y轴坐标
		return dy;
	}
	
	public double getW() { // 获取目标宽度
		return dw;
	}
	
	public double getH() { // 获取目标高度
		return dh;
	}
	
	public Image getImg() { // 获取图片
		return img;
	}
	
	// Setter
	public void setX(double dx) { // 设置目标左上角x轴坐标
		this.dx = dx;
	}
		
	public void setY(double dy) { // 设置目标左上角y轴坐标
		this.dy = dy;
	}
		
	public void setW(double dw) { // 设置目标宽度
		this.dw = dw;
	}
		
	public void setH(double dh) { // 设置目标高度
		this.dh = dh;
	}
	
	public void setImg(Image img) { // 设置图片
		this.img = img;
	}
}
