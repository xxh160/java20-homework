package runway;

import java.util.ArrayList;

import javafx.scene.shape.*;

import view.MainCanvas;

public class RunwayField {

    private ArrayList<Runway> runways; //跑道区

    private final int runwayFieldSize = 3;

    public RunwayField() {
        runways = new ArrayList<Runway>();
        for (int i = 0; i < runwayFieldSize; i++) {
            runways.add(new Runway(100, 50 + 100*i, 100, 600, i));
        }
        draw();
    }

    public void draw() {
        for (int i = 0; i < runwayFieldSize; i++) {
            Runway runway = runways.get(i);
            double startX = runway.getPosX();
            double startY = runway.getPosY() - 20;
            double endX = startX + runway.getLength();
            double endY = startY;
            Line line1 = new Line(startX, startY, endX, endY);
            Line line2 = new Line(startX, startY + runway.getWidth() -10, endX, endY + runway.getWidth()-10);
            //MainCanvas.root.getChildren().addAll(line1, line2);
            MainCanvas.addToPane(line1);
            MainCanvas.addToPane(line2);
        }
    }

	public ArrayList<Runway> getRunways() {
		return runways;
	}

    public int getRunwayFieldSize() {
        return runwayFieldSize;
    }
}