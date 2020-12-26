package app;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class ImagePane extends Pane { // 用于绘画的控件
	
	Canvas canvas = new Canvas(); // 画布
	
	// 初始化
	public ImagePane() {
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
			// 重绘画布
			update();
			
		}
	
	// 刷新
	public void update() {
		
	}

}
