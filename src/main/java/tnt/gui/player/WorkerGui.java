package tnt.gui.player;

import javafx.scene.shape.Circle;

public class WorkerGui extends Circle {
	
	private String name;
	
	public WorkerGui(String name) {
		this.name = name;
	}
	
	private boolean isSelected;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
