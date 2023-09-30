package tnt.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tnt.model.GameBoard;
import tnt.model.GameBoardCell;
import tnt.service.IGameService;

class GameServiceTest {
	
	private IGameService gameService;
	
	@BeforeEach
	public void setup() {
		gameService = GameServiceImpl.getInstance();

	}

	@Test
	void testGameBoard() {
		GameBoard gameBoard = gameService.getGameBoard();
		assert(gameBoard.getRowsNumber() == GameBoard.DIMENSION);
		assert(gameBoard.getColumnsNumber() == GameBoard.DIMENSION);
	}

}
