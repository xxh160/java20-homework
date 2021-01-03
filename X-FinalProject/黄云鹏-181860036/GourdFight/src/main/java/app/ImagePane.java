package app;

import java.util.Collection;

import framework.Constants;
import helper.ImageLocate;
import helper.TextLocate;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

public class ImagePane extends Pane { // 用于绘画的控件
	
	Canvas canvas; // 画布
	GraphicsContext gc; // 画布缓冲区
	
	// 初始化
	public ImagePane() {
		canvas = new Canvas(); 
		gc = canvas.getGraphicsContext2D(); 
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.TOP);
		getChildren().add(canvas);
	}
	
	@Override
	protected void layoutChildren() {
		// 获取控件大小
		double width = getWidth();
		double height = getHeight();
		// 设置画布缓冲区大小
		canvas.setWidth(width);
		canvas.setHeight(height);
		canvas.resizeRelocate(0, 0, width, height);
	}
	
	public void clear() {  // 清空画布
		gc.clearRect(0, 0, getWidth(), getHeight());
	}
	
	// 绘制图片到画布指定位置
	public void drawImage(ImageLocate imgLocate) {
		Image img = imgLocate.getImg();
		
		double sw = img.getWidth();
		double sh = img.getHeight();
		
		double dx = imgLocate.getX();
		if(dx < 0) {
			dx = 0;
		}
		double dy = imgLocate.getY();
		if(dy < 0) {
			dy = 0;
		}
		double dw = imgLocate.getW();
		if(dx > Constants.BACKGROUND_W) {
			dx = Constants.BACKGROUND_W;
		}
		double dh = imgLocate.getH();
		if(dy > Constants.BACKGROUND_H) {
			dx = Constants.BACKGROUND_H;
		}
		
		gc.drawImage(img, 0, 0, sw, sh, dx, dy, dw, dh);
	}
	
	// 绘制文本到画布指定位置
	public void drawText(TextLocate textLocate) {
		String text = textLocate.getText();
		double dx = textLocate.getX();
		double dy = textLocate.getY();
		gc.fillText(text, dx, dy);
	}
	
	// 刷新画布
	public void update(Collection<ImageLocate> imgLocates, Collection<TextLocate> textLocates) {
		
		clear();  // 清空画布
		
		for(ImageLocate imgLocate : imgLocates) { // 重绘所有图片
			drawImage(imgLocate);
		}
		for(TextLocate textLocate : textLocates) { // 重绘所有文本
			drawText(textLocate);
		}
	}
	
}
