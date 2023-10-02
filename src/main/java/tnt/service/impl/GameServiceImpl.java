package tnt.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import tnt.model.BoardPosition;
import tnt.model.BuildingBloc;
import tnt.model.Game;
import tnt.model.GameBoard;
import tnt.model.GameBoardCell;
import tnt.model.GameBuidingBlocs;
import tnt.model.GameNextAction;
import tnt.model.GameSettings;
import tnt.model.Player;
import tnt.model.Worker;
import tnt.service.IGameService;
import tnt.service.IGameSettingsView;
import tnt.service.IGameView;

public class GameServiceImpl implements IGameService {

	private Game game;
	private IGameView view;
	private IGameSettingsView settingsView;
	private GameMoveEvaluator gameMoveEvaluator;
	private static IGameService gameService;
	
	@Override
	public void restartGame() {
		initGame();
		view.returnToGameSettingsView();
	}

	private GameServiceImpl() {
		initGame();
	}

	public static synchronized IGameService getInstance() {
		if (gameService == null) {
			gameService = new GameServiceImpl();
		}
		return gameService;
	}

	@Override
	public void setSettingsView(IGameSettingsView settingsView) {
		this.settingsView = settingsView;
	}

	@Override
	public void setGameView(IGameView gameView) {
		this.view = gameView;
	}

	@Override
	public void initGame() {
		game = new Game();
		gameMoveEvaluator = new GameMoveEvaluator(this.game);
		game.setNextAction(GameNextAction.GET_PLAYERS_SETTINGS);
	}

	public GameBoard getGameBoard() {
		return game.getGameBoard();
	}

	@Override
	public Player getCurrentPlayer() {
		return game.getCurrentPlayer();
	}

	@Override
	public Player getOtherPlayer() {
		return game.getOtherPlayer();
	}

	@Override
	public void handleBoardPositionSelection(BoardPosition selectedPosition) {
		switch (game.getNextAction()) {

		case SELECT_PLAYER_FIRST_WORKER_INITIAL_POSITION:
			Worker firstWorker = getCurrentPlayer().getFirstWorker();
			movePlayerWorkerToSelectedPosition(firstWorker, selectedPosition);
			game.setNextAction(GameNextAction.SELECT_PLAYER_SECOND_WORKER_INITIAL_POSITION);

			List<BoardPosition> allowedPositions = findPossibleInitialMovePositionsForWorker();
			view.updatePossibleMovePositionsDisplay(allowedPositions);

			break;

		case SELECT_PLAYER_SECOND_WORKER_INITIAL_POSITION:
			Worker secondWorker = getCurrentPlayer().getSecondWorker();
			movePlayerWorkerToSelectedPosition(secondWorker, selectedPosition);

			if (getOtherPlayer().isOnGameBoard()) {
				game.setNextAction(GameNextAction.WORKER_TO_BE_MOVED_SELECTION);
			} else {
				game.setNextAction(GameNextAction.SELECT_PLAYER_FIRST_WORKER_INITIAL_POSITION);
				allowedPositions = findPossibleInitialMovePositionsForWorker();
				view.updatePossibleMovePositionsDisplay(allowedPositions);
			}

			changePlayerTurn();
			break;

		case CHECK_PLAYER_MOVE_POSITION_SELECTION:
			checkPositionAndExecutePlayerWorkerMove(selectedPosition);
			break;

		case CHECK_PLAYER_BUILDING_POSITION_SELECTION:
			checkPositionAndExecutePlayerBuidingBlocMove(selectedPosition);
			break;
			
		case RESTART_GAME:
			view.displayInfoMessage("You should re-start the game!");
			break;

		default:
		}

	}

	private void changePlayerTurn() {
		game.changePlayer();
		view.changePlayer();
	}

	private void checkPositionAndExecutePlayerWorkerMove(BoardPosition selectedPosition) {
		Worker selectedWorker = getCurrentPlayer().getSelectedWorker();
		List<BoardPosition> possiblePositions = gameMoveEvaluator
				.findPossibleNeighbourMovePositionsForWorker(selectedWorker);

		if (possiblePositions.contains(selectedPosition)) {
			view.clearMessage();
			moveWorkerToBoardPosition(selectedPosition, selectedWorker);
			view.moveWorkerToSelectedPosition(selectedWorker, selectedPosition);

			if (gameMoveEvaluator.isPositionContainingTopLevelThreeBuildingBloc(selectedPosition)) {
				view.displayInfoMessage(
						String.format("The worker is moved upon Buiding-Bloc with level 3. Player: %s has won!",
								getCurrentPlayer().getPlayerName()));
				game.setNextAction(GameNextAction.RESTART_GAME);
			} else {
				game.setNextAction(GameNextAction.SELECT_PLAYER_BUILDING_BLOC);
			}

		} else {
			String key = "game.view.wrong.position.for.worker";
			view.displayErrorMessage(key);
		}

	}

	private void moveWorkerToBoardPosition(BoardPosition selectedPosition, Worker selectedWorker) {
		BoardPosition currentWorkerPosition = selectedWorker.getBoardPosition();
		if (currentWorkerPosition != null) {
			GameBoardCell currentWorkerBoardCell = game.getGameBoard().getGameBoardCellAtPosition(currentWorkerPosition);
			currentWorkerBoardCell.removeHostedWorker();
		}
		selectedWorker.setBoardPosition(selectedPosition);
		GameBoardCell gameBoardCell = game.getGameBoard().getGameBoardCellAtPosition(selectedPosition);
		gameBoardCell.setHostedWorker(selectedWorker);
		int highestBuildingBlocLevel = gameBoardCell.getHighestBuildingBlocLevel();
		selectedWorker.setLevel(highestBuildingBlocLevel);
	}

	private void checkPositionAndExecutePlayerBuidingBlocMove(BoardPosition selectedPosition) {
		GameBuidingBlocs buildingBlocs = game.getBuildingBlocs();
		int selectedBuildingBlocLevel = buildingBlocs.getSelectedBuildingBlocLevel();
		Set<BoardPosition> possibleNeighbourMovePositionsForBuildingBloc = gameMoveEvaluator
				.findPossibleNeighbourMovePositionsForBuildingBloc(selectedBuildingBlocLevel);
		if (possibleNeighbourMovePositionsForBuildingBloc.contains(selectedPosition)) {
			// move is possible
			view.clearMessage();
			GameBoardCell selectedGameBoardCell = getGameBoard().getGameBoardCellAtPosition(selectedPosition);
			BuildingBloc selectedBuildingBloc = new BuildingBloc(selectedBuildingBlocLevel);
			selectedGameBoardCell.addBuildingBloc(selectedBuildingBloc);
			buildingBlocs.decrementBuidingBlocsCountForLevel(selectedBuildingBlocLevel);
			view.disableBoardPositions(possibleNeighbourMovePositionsForBuildingBloc);
			view.moveSelectedBuildingBlocToPosition(selectedBuildingBloc, selectedPosition);
			game.setNextAction(GameNextAction.WORKER_TO_BE_MOVED_SELECTION);
			changePlayerTurn();
		} else {
			view.displayErrorMessage(
					"The position you selected for your building-bloc is neither valid nor hosting a compatible building-bloc level!");
		}
	}

	private void movePlayerWorkerToSelectedPosition(Worker worker, BoardPosition position) {
		if (isWorkerMovePossible(worker, position)) {
			moveWorkerToBoardPosition(position, worker);
			view.moveWorkerToSelectedPosition(worker, position);
		} else {
			view.displayErrorMessage(
					"The position you selected for your worker is neither free nor hosting a compatible building-bloc level!");
		}
	}

	private boolean isWorkerMovePossible(Worker worker, BoardPosition position) {
		List<BoardPosition> possibleMovePositions = (worker.isPlacedOnBoard())
				? gameMoveEvaluator.findPossibleNeighbourMovePositionsForWorker(worker)
				: findPossibleInitialMovePositionsForWorker();
		return possibleMovePositions.contains(position);
	}

	@Override
	public List<BoardPosition> findPossibleInitialMovePositionsForWorker() {
		return gameMoveEvaluator.findPossibleInitialMovePositionsForWorker();
	}

	@Override
	public void handleGameSettingsOnSubmit(GameSettings gameSettings) {
		if (isValid(gameSettings)) {
			game.setGameSettings(gameSettings);
			Player firstPlayer = new Player(gameSettings.getFirstPlayerName(),
					gameSettings.getFirstPlayerWorkerColor());
			Player secondPlayer = new Player(gameSettings.getSecondPlayerName(),
					gameSettings.getSecondPlayerWorkerColor());
			game.setFirstPlayer(firstPlayer);
			game.setSecondPlayer(secondPlayer);
			game.setNextAction(GameNextAction.SELECT_PLAYER_FIRST_WORKER_INITIAL_POSITION);
			settingsView.startGame();
		} else {
			settingsView
					.displayGameSettingsErrorMessage("Settings entered are not valid. Please set the settings again!");
		}
	}

	/**
	 * 
	 * @param gameSettings these are the game settings
	 * @return true if the selected game-settings are valid otherwise false
	 */
	private boolean isValid(GameSettings gameSettings) {

		if (gameSettings.getFirstPlayerName() == null || gameSettings.getFirstPlayerName().length() == 0) {
			return false;
		}

		if (gameSettings.getSecondPlayerName() == null || gameSettings.getSecondPlayerName().length() == 0) {
			return false;
		}

		if (gameSettings.getFirstPlayerName().equals(gameSettings.getSecondPlayerName())) {
			return false;
		}

		if (gameSettings.getFirstPlayerWorkerColor().equals(gameSettings.getSecondPlayerWorkerColor())) {
			return false;
		}

		return true;

	}

	@Override
	public List<BoardPosition> handleWorkerSelection(Player player, Worker worker) {

		updateWorkersSelectionState(player, worker);

		List<BoardPosition> possibleMovePositions = null;

		switch (game.getNextAction()) {
		
		case RESTART_GAME:
			view.displayInfoMessage("You should re-start the game!");
			break;

		case WORKER_TO_BE_MOVED_SELECTION:
			if (getCurrentPlayer().hasWorker(worker)) {
				view.clearMessage();
				view.updateWorkersSelection();
				boolean moveIsPossible = gameMoveEvaluator.isCurrentPlayerMovePossible();

				if (moveIsPossible) {
					possibleMovePositions = gameMoveEvaluator.findPossibleNeighbourMovePositionsForWorker(worker);
					if (!possibleMovePositions.isEmpty()) {
						view.updatePossibleMovePositionsDisplay(possibleMovePositions);
						game.setNextAction(GameNextAction.CHECK_PLAYER_MOVE_POSITION_SELECTION);
					} else {
						view.displayErrorMessage(
								"With the current selected worker, there is no possible move. Try with the other worker!");
					}
				} else {
					String gameEndMessage = String.format(
							"You have loosed the game. You have no possible move for your workers. Winner is Player: %s",
							getOtherPlayer().getPlayerName());
					game.setNextAction(GameNextAction.RESTART_GAME);
					view.displayErrorMessage(gameEndMessage);
				}
				
			} else {
				view.displayErrorMessage("Selected Worker doesn't belong to the current player!");
			}
			break;

		default:
		}

		return possibleMovePositions;
	}

	private void updateWorkersSelectionState(Player player, Worker worker) {
		worker.setSelected(true);
		Worker otherWorker = Arrays.stream(player.getWorkers()).filter(wk -> wk != worker).findFirst().get();
		otherWorker.setSelected(false);
	}

	private void handleBuildingBlockSelection(int level) {

		switch (game.getNextAction()) {
		
		case RESTART_GAME:
			view.displayInfoMessage("You should re-start the game!");
			break;

		case SELECT_PLAYER_BUILDING_BLOC:
			GameBuidingBlocs buildingBlocs = game.getBuildingBlocs();

			boolean buidingBlocMoveIsPossible = gameMoveEvaluator.isCurrentPlayerBuildingBlocMovePossible();

			if (buidingBlocMoveIsPossible) {
				if (!buildingBlocs.isBuildingBlocWithGivenLevelAvailable(level)) {
					view.displayErrorMessage(
							"For this kind of Building Blocks, there is no one available!\n Choose another one!");
					return;
				}

				Set<BoardPosition> possiblePositions = gameMoveEvaluator.findPossibleNeighbourMovePositionsForBuildingBloc(level);
				if (possiblePositions.isEmpty()) {
					view.displayErrorMessage("No position available for building bloc. Choose another one!");
					return;
				}

				buildingBlocs.setSelectedBuildingBlocLevel(level);
				view.markSelectedBuidingBlocAsSelected(level);
				view.updatePossibleMovePositionsDisplay(new ArrayList<>(possiblePositions));
				game.setNextAction(GameNextAction.CHECK_PLAYER_BUILDING_POSITION_SELECTION);
			} else {
				game.setNextAction(GameNextAction.RESTART_GAME);
				view.displayInfoMessage(String.format(
						"The current player %s has lost the game because there is no position available for any remaining building bloc! The winner is player %s",
						getCurrentPlayer().getPlayerName(), getOtherPlayer().getPlayerName()));
			}

			break;

		default:

		}

	}

	@Override
	public void handleLevelOneBuildingBlockSelection() {
		handleBuildingBlockSelection(1);
	}

	@Override
	public void handleLevelTwoBuildingBlockSelection() {
		handleBuildingBlockSelection(2);
	}

	@Override
	public void handleLevelThreeBuildingBlockSelection() {
		handleBuildingBlockSelection(3);
	}

	@Override
	public void handleDomBuildingBlockSelection() {
		handleBuildingBlockSelection(4);
	}

	@Override
	public int getLevelOneRemainingBlocksCount() {
		GameBuidingBlocs buildingBlocs = game.getBuildingBlocs();
		return buildingBlocs.getLevelOneRemainingBlocsCount();
	}

	@Override
	public int getLevelTwoRemainingBlocksCount() {
		GameBuidingBlocs buildingBlocs = game.getBuildingBlocs();
		return buildingBlocs.getLevelTwoRemainingBlocsCount();
	}

	@Override
	public int getLevelThreeRemainingBlocksCount() {
		GameBuidingBlocs buildingBlocs = game.getBuildingBlocs();
		return buildingBlocs.getLevelThreeRemainingBlocsCount();
	}

	@Override
	public int getDomRemainingBlocksCount() {
		GameBuidingBlocs buildingBlocs = game.getBuildingBlocs();
		return buildingBlocs.getDomRemainingBlocsCount();
	}

}
