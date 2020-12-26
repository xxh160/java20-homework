package app;

import java.util.ArrayList;
import java.util.Collection;

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
	
	// 绘制图片到画布指定位置
	private void drawImage(ImageLocate imgLocate) {
		Image img = imgLocate.getImg();
		
		double sw = img.getWidth();
		double sh = img.getHeight();
		
		double dx = imgLocate.getX();
		double dy = imgLocate.getY();
		double dw = imgLocate.getW();
		double dh = imgLocate.getH();
		
		gc.drawImage(img, 0, 0, sw, sh, dx, dy, dw, dh);
	}
	
	// 绘制文本到画布指定位置
	private void drawText(TextLocate textLocate) {
		String text = textLocate.getText();
		double dx = textLocate.getX();
		double dy = textLocate.getY();
		gc.fillText(text, dx, dy);
	}
	
	// 刷新画布
	public void update(Collection<ImageLocate> imgLocates, Collection<TextLocate> textLocates) {
		gc.clearRect(0, 0, getWidth(), getHeight()); // 清空画布
		for(ImageLocate imgLocate : imgLocates) { // 重绘所有图片
			drawImage(imgLocate);
		}
		for(TextLocate textLocate : textLocates) { // 重绘所有文本
			drawText(textLocate);
		}
	}
	
}
