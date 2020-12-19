package view;

import entity.FiveChess;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ChessPane extends Pane {
    public Canvas getCanvas() {
        return canvas;
    }

    public Canvas canvas;

    public GraphicsContext getGc() {
        return gc;
    }

    public GraphicsContext gc;

    public FiveChess getFiveChess() {
        return fiveChess;
    }

    public void setFiveChess(FiveChess fiveChess) {
        this.fiveChess = fiveChess;
    }

    public FiveChess fiveChess;

    public ChessPane(FiveChess fiveChess) {
        this.fiveChess = fiveChess;
        double cell = fiveChess.getCellLen();
        drawPane(cell);
        drawChess(cell);
        getChildren().add(canvas);
    }

    public void drawPane(double cell) {
        canvas = new Canvas(800, 700);
        gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 绘制棋盘
        gc.setStroke(Color.BLACK);

        for (int i = 0; i < fiveChess.getWidth(); i++)
            for (int j = 0; j < fiveChess.getHeight(); j++) {
                gc.strokeRect(100 + i * cell, 100 + cell * j, cell, cell);// 清理一个矩形取区域的内容
            }

    }

    public void drawChess(double cell) {

        char[][] chess = fiveChess.getChess();
        for (int i = 0; i < fiveChess.getHeight(); i++)
            for (int j = 0; j < fiveChess.getWidth(); j++) {
                if (chess[i][j] == 'B') {
                    gc.setFill(Color.BLACK);// 设置填充色
                    gc.fillOval(100 + i * cell - cell / 2, 100 + j * cell - cell / 2, cell, cell);
                } else if (chess[i][j] == 'W') {
                    gc.setFill(Color.WHITE);

                    gc.fillOval(100 + i * cell - cell / 2, 100 + j * cell - cell / 2, cell, cell);// 填充椭圆
                    gc.strokeOval(100 + i * cell - cell / 2, 100 + j * cell - cell / 2, cell, cell);// 绘制轮廓
                }
            }
    }
}