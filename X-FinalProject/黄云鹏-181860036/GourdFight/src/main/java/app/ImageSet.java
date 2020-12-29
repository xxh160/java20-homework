package app;

import javafx.scene.image.Image;

public class ImageSet { // 存储实体一个状态下的动画图片序列，并提供调用方法
	
	private int num; // 图片数量
	private Image[] imgs; // 图片序列
	private int count; // 序列计数器
	
	// 初始化
	public ImageSet(int num) {
		this.num = num;
		imgs = new Image[num];
		count = 0;
	}
	
	// Getter
	public int getNum() { // 获取图片数量
		return num;
	}
	
	public Image getCurrentImage() { // 获取当前图片帧
		Image img = imgs[count];
		count = (count + 1) % num;
		return img;
	}
	
	public boolean isDone() { // 判断当前帧是否已经是图片序列的最后一帧
		return (count == num);
	}
	
	// Setter
	public void setImage(int idx, Image img) { // 设置图片帧
		if(idx < 0 || idx >= num) {
			return;
		}
		imgs[idx] = img;
	}
	
	public void clearCount() { // 清零计数器
		count = 0;
	}

}
