package santorini.service;

import java.util.List;

import santorini.model.BoardPosition;
import santorini.model.GameBoard;
import santorini.model.GameSettings;
import santorini.model.Player;
import santorini.model.Worker;

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
