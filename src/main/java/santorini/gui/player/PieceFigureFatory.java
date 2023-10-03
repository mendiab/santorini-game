package santorini.gui.player;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PieceFigureFatory {
	private static final int RADIUS = 30;
	
	public static WorkerGui createWorkerWithColor(String name, Color color) {
		WorkerGui workerGui = new WorkerGui(name);
		workerGui.setRadius(RADIUS);
		workerGui.setFill(color);
		return workerGui;
	}
}
