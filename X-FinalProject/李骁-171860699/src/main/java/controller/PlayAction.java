package controller;

import entity.FiveChess;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import view.ChessPane;

public class PlayAction implements EventHandler<MouseEvent> {

    /** fiveChess表示五子棋游戏模型 */

    private FiveChess fiveChess;

    /** chessPane表示五子棋显示面板 */

    private ChessPane chessPane;

    public PlayAction(FiveChess fiveChess, ChessPane chessPane) {
        this.chessPane = chessPane;

        this.fiveChess = fiveChess;

    }

    @Override
    public void handle(MouseEvent event) {
        // 处理鼠标点击事件
        double cell = fiveChess.getCellLen();

        // event.getX()获取鼠标点击x坐标，返回double类型
        double x = event.getX();
        double y = event.getY();

        int i = (int) ((x - 100 + cell / 2) / cell);
        int j = (int) ((y - 100 + cell / 2) / cell);

        System.out.println(i + " " + j);
        fiveChess.play(i, j);
        chessPane.drawChess(cell);
        if (!fiveChess.judgeGame(i, j, fiveChess.getCurrentSide() == 'B' ? 'W' : 'B')) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("五子棋游戏");
            alert.setHeaderText("提示信息");
            alert.setContentText((fiveChess.getCurrentSide() == 'B' ? "白" : "黑") + "方取得胜利！");
            alert.showAndWait();
        }

    }

}