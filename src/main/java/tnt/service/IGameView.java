package tnt.service;

import java.util.List;
import java.util.Set;

import tnt.gui.player.WorkerGui;
import tnt.model.BoardPosition;
import tnt.model.BuildingBloc;
import tnt.model.Player;
import tnt.model.Worker;

public interface IGameView {
	
	/**
	 * inform the view component to change and update the current player.
	 */
	void changePlayer();
	
	void clearMessage();
	
	/**
	 * inform the view component to display an error message
	 * @param message this is the error message to display
	 */
	void displayErrorMessage(String message);
	
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
