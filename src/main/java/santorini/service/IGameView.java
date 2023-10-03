package santorini.service;

import java.util.List;
import java.util.Set;

import santorini.gui.player.WorkerGui;
import santorini.model.BoardPosition;
import santorini.model.BuildingBloc;
import santorini.model.Player;
import santorini.model.Worker;

public interface IGameView {
	
	/**
	 * inform the view component to change and update the current player.
	 */
	void changePlayer();
	
	void clearMessage();
	
	/**
	 * inform the view component to display an error message
	 * @param key is the resource-bundle key of the error message to display
	 */
	void displayErrorMessage(String key);
	
	void displayInfoMessage(String message);
	
	void moveWorkerToSelectedPosition(Worker worker, BoardPosition position);
	
	void onInitGameView();

	void onPlayerWorkerSelection(List<BoardPosition> possibleMovePositions);

	void updatePossibleMovePositionsDisplay(List<BoardPosition> possibleMovePositions);

	void markSelectedBuidingBlocAsSelected(int level);

	void moveSelectedBuildingBlocToPosition(BuildingBloc selectedBuildingBloc, BoardPosition selectedPosition);

	void disableBoardPositions(Set<BoardPosition> possibleNeighbourMovePositionsForBuildingBloc);

	void updateWorkersSelection();

	void returnToGameSettingsView();

}
