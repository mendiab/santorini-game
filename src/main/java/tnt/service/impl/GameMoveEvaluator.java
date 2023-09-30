package tnt.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import tnt.model.BoardPosition;
import tnt.model.Game;
import tnt.model.GameBoard;
import tnt.model.GameBoardCell;
import tnt.model.GameBuidingBlocs;
import tnt.model.Player;
import tnt.model.Worker;

public class GameMoveEvaluator {

	private Game game;

	public GameMoveEvaluator(Game game) {
		this.game = game;
	}

	public List<BoardPosition> findPossibleNeighbourMovePositionsForWorker(Worker worker) {
		List<BoardPosition> possibleMovePositions = new LinkedList<>();
		GameBoard gameBoard = game.getGameBoard();
		// check only neighbourPositions
		List<BoardPosition> neighbourPositions = gameBoard.getNeighbourPositions(worker.getBoardPosition());
		for (BoardPosition neighbour : neighbourPositions) {
			if (gameBoard.getGameBoardCellAtPosition(neighbour).isFreeForWorkerWithLevel(worker.getLevel())) {
				possibleMovePositions.add(neighbour);
			}
		}
		return possibleMovePositions;
	}

	public boolean isCurrentPlayerMovePossible() {
		Player currentPlayer = game.getCurrentPlayer();
		Worker selectedWorker = currentPlayer.getSelectedWorker();
		if (isWorkerMovePossible(selectedWorker)) {
			return true;
		};
		
		return isWorkerMovePossible(currentPlayer.getOtherWorker());
	}

	private boolean isWorkerMovePossible(Worker worker) {
		boolean moveIsPossible = false;
		GameBoard gameBoard = game.getGameBoard();
		// check only neighbourPositions
		List<BoardPosition> neighbourPositions = gameBoard.getNeighbourPositions(worker.getBoardPosition());
		for (BoardPosition neighbour : neighbourPositions) {
			if (gameBoard.getGameBoardCellAtPosition(neighbour).isFreeForWorkerWithLevel(worker.getLevel())) {
				moveIsPossible = true;
				break;
			}
		}
		return moveIsPossible;
	}

	public boolean isPositionContainingTopLevelThreeBuildingBloc(BoardPosition selectedPosition) {
		GameBoardCell boardCellAtPosition = game.getGameBoard().getGameBoardCellAtPosition(selectedPosition);
		return boardCellAtPosition.getHighestBuildingBlocLevel() == 3;
	}
	
	public List<BoardPosition> findPossibleInitialMovePositionsForWorker() {
		List<BoardPosition> possibleMovePositions = new LinkedList<>();
		GameBoard gameBoard = game.getGameBoard();
		for (int rowIndex = 0; rowIndex < GameBoard.DIMENSION; rowIndex++) {
			for (int colIndex = 0; colIndex < GameBoard.DIMENSION; colIndex++) {
				if (gameBoard.getGameBoardCellAtPosition(new BoardPosition(rowIndex, colIndex)).isFreeForWorkerWithLevel(0)) {
					possibleMovePositions.add(new BoardPosition(rowIndex, colIndex));
				}
			}
		}
		return possibleMovePositions;
	}
	
	public Set<BoardPosition> findPossibleNeighbourMovePositionsForBuildingBloc(int buildingBlocLevel) {
		Player currentPlayer = game.getCurrentPlayer();
		Set<BoardPosition> possiblePositions = new HashSet<>();
		Worker selectedWorker = currentPlayer.getSelectedWorker();
		List<BoardPosition> workerNeighbourPositions = findPossibleNeighbourMovePositionsForBuildingBloc(
				selectedWorker.getBoardPosition(), buildingBlocLevel);
		possiblePositions.addAll(workerNeighbourPositions);
		return possiblePositions;
	}
	
	private List<BoardPosition> findPossibleNeighbourMovePositionsForBuildingBloc(BoardPosition workerPosition,
			int buildingBlocLevel) {

		List<BoardPosition> possibleNeighbourPositions = new ArrayList<>();

		GameBoard gameBoard = game.getGameBoard();
		List<BoardPosition> neighbourPositions = gameBoard.getNeighbourPositions(workerPosition);

		for (BoardPosition neighbour : neighbourPositions) {
			if (gameBoard.getGameBoardCellAtPosition(neighbour).isFreeForBuildingBlocWithLevel(buildingBlocLevel)) {
				possibleNeighbourPositions.add(neighbour);
			}
		}

		return possibleNeighbourPositions;
	}

	public boolean isCurrentPlayerBuildingBlocMovePossible() {
		GameBuidingBlocs buildingBlocs = game.getBuildingBlocs();
		List<Integer> availableLevels = buildingBlocs.getLevelsOfAvailableBuildingBlocs();
		
		if (availableLevels.isEmpty()) {
			return false;
		}
		
		GameBoard gameBoard = game.getGameBoard();
		Worker selectedWorker = game.getCurrentPlayer().getSelectedWorker();
		
		// neighbourPositions can not be empty since worker has already let its position free
		List<BoardPosition> neighbourPositions = gameBoard.getNeighbourPositions(selectedWorker.getBoardPosition());
		
		boolean isPossible = false;
		
		for (int level : availableLevels) {
			for (BoardPosition neighbour : neighbourPositions) {
				if (gameBoard.getGameBoardCellAtPosition(neighbour).isFreeForBuildingBlocWithLevel(level)) {
					isPossible = true;
					break;
				}
			}
		}
		
		return isPossible;
	}

}
