package game.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import game.BattleGround;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import tool.point.Grid;

public class SceneView implements Serializable {
    private static final long serialVersionUID = -5809782578272943999L;
    public HashSet<Circle> bulletViewSet = new HashSet<>();
    public double RedWidth[] = new double[12];
    public double BlueWidth[] = new double[12];
    public Grid grid[] = new Grid[12];
    public Circle flag = new Circle();

    public SceneView() {
    }

    public SceneView(Set<BulletView> bulletViewSet, CharacterView[] characterView) {
        for (int i = 0; i < 12; i++) {
            if (characterView[i] == null) {
                grid[i] = new Grid(0, 0);
                continue;
            }
            RedWidth[i] = characterView[i].getRed().getWidth();
            BlueWidth[i] = characterView[i].getBlue().getWidth();
            grid[i] = characterView[i].getGrid();

        }
        for (BulletView bv : bulletViewSet) {
            Circle view = bv.getView();
            // System.out.println("get "+view.getLayoutX()+" "+view.getLayoutY()+"
            // "+view.getRadius());
            Circle cir = new Circle(0, 0, view.getRadius());
            cir.setLayoutX(view.getLayoutX());
            cir.setLayoutY(view.getLayoutY());
            // System.out.println("after "+cir.getLayoutX()+" "+cir.getLayoutY()+"
            // "+cir.getRadius());
            cir.setFill(view.getFill());
            this.bulletViewSet.add(cir);

        }
        /*
         * this.flag=new Circle(0,0,flag.getRadius());
         * this.flag.setLayoutX(flag.getLayoutX());
         * this.flag.setLayoutY(flag.getLayoutY()); this.flag.setFill(Color.YELLOW);
         */
    }

    public void writeObject(DataOutputStream out) throws IOException {
        // System.out.println("write Scene");
        for (int i = 0; i < 12; i++) {
            // System.out.println("write "+RedWidth[i]);
            out.writeDouble(RedWidth[i]);
        }
        for (int i = 0; i < 12; i++) {
            // System.out.println("write "+BlueWidth[i]);
            out.writeDouble(BlueWidth[i]);
        }
        for (int i = 0; i < 12; i++) {
            // System.out.println("write "+grid[i].getX());
            // System.out.println("write "+grid[i].getY());
            out.writeInt(grid[i].getX());
            out.writeInt(grid[i].getY());
        }
        /*
         * out.writeDouble(flag.getLayoutX()); out.writeDouble(flag.getLayoutY());
         * out.writeDouble(flag.getRadius());
         */
        // System.out.print("write num"+bulletViewSet.size());
        out.writeInt(bulletViewSet.size());
        // System.out.print("prepare loop");
        for (Circle bv : bulletViewSet) {
            // System.out.println("here");
            out.writeDouble(bv.getLayoutX());
            out.writeDouble(bv.getLayoutY());
            out.writeDouble(bv.getRadius());
            Color col = (Color) Paint.valueOf(bv.getFill().toString());
            out.writeDouble(col.getRed());
            out.writeDouble(col.getGreen());
            out.writeDouble(col.getBlue());
            out.writeDouble(col.getOpacity());
            /*
             * System.out.println("write "+bv.getLayoutX());
             * System.out.println("write "+bv.getLayoutY());
             * System.out.println("write "+bv.getRadius());
             * System.out.println("write "+col.getRed());
             * System.out.println("write "+col.getGreen());
             * System.out.println("write "+col.getBlue());
             * System.out.println("write "+col.getOpacity());
             */
        }
    }

    public void readObject(DataInputStream in) throws IOException, ClassNotFoundException {
        // System.out.println("read Scene");
        // System.out.println(in.available());
        for (int i = 0; i < 12; i++) {
            // System.out.println(in);
            RedWidth[i] = in.readDouble();
            // System.out.println(RedWidth[i]);
        }
        for (int i = 0; i < 12; i++) {
            BlueWidth[i] = in.readDouble();
            // System.out.println(RedWidth[i]);
        }
        for (int i = 0; i < 12; i++) {
            grid[i] = new Grid(in.readInt(), in.readInt());
            // System.out.println(grid[i].getX()+","+grid[i].getY());
        }
        /*
         * flag=new Circle(in.readDouble(),in.readDouble(),in.readDouble());
         * flag.setFill(Color.YELLOW);
         */
        int num = in.readInt();
        // System.out.println("num "+num);
        for (int i = 0; i < num; i++) {
            // System.out.println("index"+i);
            Circle cir = new Circle(in.readDouble(), in.readDouble(), in.readDouble());
            cir.setFill(new Color(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble()));
            // cir.setLayoutX(x);
            // cir.setLayoutY(y);
            bulletViewSet.add(cir);
        }
        // System.out.println("end");
    }

    public void show(Pane root) {
        for (int i = 0; i < 12; i++) {
            CharacterView cv = BattleGround.characterView[i];
            if (cv == null) {
                continue;
            }
            if (RedWidth[i] <= 0) {
                Platform.runLater(() -> {
                    cv.removeScene(root);
                });
                continue;
            }
            cv.show(grid[i], RedWidth[i], BlueWidth[i]);
        }
        for (Circle bv : bulletViewSet) {
            // System.out.println("hello "+bv.getLayoutX()+" "+bv.getLayoutY());
            Platform.runLater(() -> {
                root.getChildren().add(bv);
            });

        }
        BattleGround.updateFlag();
    }

    public void erase(Pane root) {
        for (Circle bv : bulletViewSet) {
            Platform.runLater(() -> {
                root.getChildren().remove(bv);
            });
        }
    }
}