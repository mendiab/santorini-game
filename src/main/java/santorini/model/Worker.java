package santorini.model;

public class Worker {
	
	private EWorkerColor color;
	private BoardPosition boardPosition;
	private boolean isSelected;
	private int level;

	public Worker(EWorkerColor workerColor) {
		level = 0;
		color = workerColor;
	}
	
	public boolean isPlacedOnBoard() {
		return boardPosition != null;
	}
	
	public void setBoardPosition(BoardPosition position) {
		this.boardPosition = position;
	}
	
	public EWorkerColor getColor() {
		return color;
	}

	public void setSelected(boolean status) {
		this.isSelected = status;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public BoardPosition getBoardPosition() {
		return boardPosition;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
