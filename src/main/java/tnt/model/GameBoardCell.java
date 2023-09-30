package tnt.model;

public class GameBoardCell {

	private BuildingBloc levelOneBloc;
	private BuildingBloc levelTwoBloc;
	private BuildingBloc levelThreeBloc;
	private BuildingBloc domBloc;
	private Worker hostedWorker;
	private BoardPosition position;
	private int highestBuildingBlocLevel;

	public GameBoardCell(int rowIndex, int colIndex) {
		highestBuildingBlocLevel = 0;
		this.position = new BoardPosition(rowIndex, colIndex);
	}

	public boolean isFreeForWorkerWithLevel(int workerLevel) {
		return  (hostedWorker == null) &&
				(highestBuildingBlocLevel - workerLevel <= 1) &&
				(domBloc == null);
	}
	
	public boolean isFreeForBuildingBlocWithLevel(int buildingBlocLevel) {
		return (hostedWorker == null) &&
				(buildingBlocLevel - highestBuildingBlocLevel == 1) &&
				(domBloc == null);
	}

	public BoardPosition getPosition() {
		return position;
	}

	public void setPosition(BoardPosition position) {
		this.position = position;
	}

	public Worker getHostedWorker() {
		return hostedWorker;
	}

	public void setHostedWorker(Worker worker) {
		this.hostedWorker = worker;
	}
	
	public void removeHostedWorker() {
		this.hostedWorker = null;
	}

	public boolean isFreeForBuildingBloc(BuildingBloc buildingBloc) {
		return (hostedWorker == null) && (buildingBloc.getLevel() - highestBuildingBlocLevel == 1) && (domBloc == null);
	}

	public void addBuildingBloc(BuildingBloc buildingBloc) {
		switch (buildingBloc.getLevel()) {
		case 1:
			levelOneBloc = buildingBloc;
			break;

		case 2:
			levelTwoBloc = buildingBloc;
			break;

		case 3:
			levelThreeBloc = buildingBloc;
			break;
		case 4:
			domBloc = buildingBloc;
			break;
		}
		highestBuildingBlocLevel = buildingBloc.getLevel();
	}

	public int getHighestBuildingBlocLevel() {
		return highestBuildingBlocLevel;
	}

}
