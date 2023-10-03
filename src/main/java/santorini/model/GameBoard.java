package santorini.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameBoard {
	public static final int DIMENSION = 5;
	private GameBoardCell[][] cellsArray;

	public GameBoard() {
		cellsArray = new GameBoardCell[DIMENSION][DIMENSION];
		IntStream.range(0, DIMENSION).forEach(rowIndex -> populateRow(rowIndex));
	}

	private void populateRow(int rowIndex) {
		IntStream.range(0, DIMENSION).forEach(colIndex -> populateRowColumn(rowIndex, colIndex));
	}

	private void populateRowColumn(int rowIndex, int colIndex) {
		cellsArray[rowIndex][colIndex] = new GameBoardCell(rowIndex, colIndex);
	}
	
	public GameBoardCell getGameBoardCellAtPosition(BoardPosition boardPosition) {
		return cellsArray[boardPosition.getRow()][boardPosition.getColumn()];
	}

	public List<BoardPosition> getNeighbourPositions(BoardPosition boardPosition) {
		
		BoardPosition[] neighbours = { 
				new BoardPosition(boardPosition.getRow() - 1, boardPosition.getColumn() - 1),
				new BoardPosition(boardPosition.getRow() - 1, boardPosition.getColumn()),
				new BoardPosition(boardPosition.getRow() - 1, boardPosition.getColumn() + 1),
				new BoardPosition(boardPosition.getRow(), boardPosition.getColumn() - 1),
				new BoardPosition(boardPosition.getRow(), boardPosition.getColumn() + 1),
				new BoardPosition(boardPosition.getRow() + 1, boardPosition.getColumn() - 1),
				new BoardPosition(boardPosition.getRow() + 1, boardPosition.getColumn()),
				new BoardPosition(boardPosition.getRow() + 1, boardPosition.getColumn() + 1), 
				};

		return Arrays.stream(neighbours).filter(position -> isPositionInBoard(position)).collect(Collectors.toList());

	}
	
	public int getRowsNumber() {
		return cellsArray.length;
	}
	
	public int getColumnsNumber() {
		return cellsArray[0].length;
	}

	private boolean isPositionInBoard(BoardPosition position) {
		if (position.getColumn() < 0 || position.getRow() < 0) {
			return false;
		}

		if (position.getColumn() >= DIMENSION || position.getRow() >= DIMENSION) {
			return false;
		}

		return true;
	}
}
