package tnt.service;

import java.util.List;

import tnt.model.BoardPosition;
import tnt.model.GameBoard;
import tnt.model.GameSettings;
import tnt.model.Player;
import tnt.model.Worker;

public interface IGameService {
			
	List<BoardPosition> findPossibleInitialMovePositionsForWorker();
	
	GameBoard getGameBoard();
	
	Player getCurrentPlayer();
	
	Player getOtherPlayer();
	
	void handleBoardPositionSelection(BoardPosition selectedPosition);
	
	void handleGameSettingsOnSubmit(GameSettings gameSettings);
	
	List<BoardPosition> handleWorkerSelection(Player player, Worker worker);
	
	void initGame();

	void setSettingsView(IGameSettingsView settingsView);

	void setGameView(IGameView gameView);

	int getLevelOneRemainingBlocksCount();
	
	int getLevelTwoRemainingBlocksCount();
	
	int getLevelThreeRemainingBlocksCount();
	
	int getDomRemainingBlocksCount();

	void handleLevelOneBuildingBlockSelection();

	void handleLevelTwoBuildingBlockSelection();

	void handleLevelThreeBuildingBlockSelection();

	void handleDomBuildingBlockSelection();

	void restartGame();

}
