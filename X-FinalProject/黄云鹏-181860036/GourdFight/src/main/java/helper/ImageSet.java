package helper;

import javafx.scene.image.Image;

public class ImageSet { // 存储实体一个状态下的动画图片序列，并提供调用方法
	
	private int num; // 图片数量
	private Image[] imgsLeft; // 图片序列(朝向左边)
	private Image[] imgsRight; // 图片序列(朝向右边)
	private int count; // 序列计数器
	
	private boolean isDone; // 是否播放完成
	
	// 初始化
	public ImageSet(int num) {
		this.num = num;
		imgsLeft = new Image[num];
		imgsRight = new Image[num];
		count = 0;
	}
	
	// Getter
	public int getNum() { // 获取图片数量
		return num;
	}
	
	public Image getCurrentImage(boolean isLeft) { // 获取当前图片帧

		if(count >= num) {
			return null;
		}
		
		if(isLeft) {
			Image img = imgsLeft[count];
			count++;
			return img;
		}
		else {
			Image img = imgsRight[count];
			count++;
			return img;
		}
		
	}
	
	public boolean isDone() { // 判断当前帧是否已经是图片序列的最后一帧
		return (count == num);
	}
	
	// Setter
	public void setImage(int idx,Image img,boolean isLeft) { // 设置图片帧
		if(idx < 0 || idx >= num) {
			return;
		}
		
		if(isLeft) {
			imgsLeft[idx] = img;
		}
		else {
			imgsRight[idx] = img;
		}
	}
	
	public void clearCount() { // 清零计数器
		count = 0;
	}

}
