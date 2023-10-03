package santorini.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import santorini.model.GameBoard;
import santorini.service.IGameService;

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
