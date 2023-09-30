package tnt.gui.player;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PieceFigureFatory {
	private final static int SIZE_LEVEL_1 = 10;
	private final static int SIZE_LEVEL_2 = 20;
	private final static int SIZE_LEVEL_3 = 30;
	private static final int RADIUS = 30;

	public static Rectangle createBuildingBlocPieceForLevel(int level) {
		int size = computeRectangleSize(level);
		return createRectangle(size);
	}

	private static Rectangle createRectangle(int size) {
		Rectangle r = new Rectangle();
		r.setHeight(size);
		r.setWidth(size);
		r.setStroke(Color.BLACK);
		r.setFill(Color.WHITE);
		return r;
	}

	private static int computeRectangleSize(int level) {
		int size = 0;

		switch (level) {
		case 1:
			size = SIZE_LEVEL_1;
			break;

		case 2:
			size = SIZE_LEVEL_2;
			break;

		case 3:
			size = SIZE_LEVEL_3;
			break;

		default:
			throw new IllegalStateException("Building Blocc level must be between 1 and 3");
		}
		return size;
	}
	
	public static Circle createDom() {
		Circle circle = new Circle();
		circle.setRadius(RADIUS);
		circle.setFill(Color.DARKSLATEBLUE);
		circle.setStroke(Color.BLACK);
		return circle;
	}
	
	public static WorkerGui createWorkerWithColor(String name, Color color) {
		WorkerGui workerGui = new WorkerGui(name);
		workerGui.setRadius(RADIUS);
		workerGui.setFill(color);
		return workerGui;
	}
}
