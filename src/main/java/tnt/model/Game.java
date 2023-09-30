package tnt.model;

public class Game {

	private static final int LEVEL_ONE_BUILDING_BLOCS_MAX_COUNT = 14;
	private static final int LEVEL_TWO_BUIDING_BLOCS_MAX_COUNT = 18;
	private static final int LEVEL_THREE_BUILDING_BLOCS_MAX_COUNT = 22;
	private static final int DOM_BUILDING_BLOCS_MAX_COUNT = 18;
	private GameBoard gameBoard;
	private Players players;
	private GameBuidingBlocs buildingBlocs;
	private GameSettings gameSettings;
	private GameNextAction nextAction;

	public Game() {
		gameBoard = new GameBoard();
		players = new Players();
		buildingBlocs = 
				new GameBuidingBlocs(
						LEVEL_ONE_BUILDING_BLOCS_MAX_COUNT, 
						LEVEL_TWO_BUIDING_BLOCS_MAX_COUNT,
				        LEVEL_THREE_BUILDING_BLOCS_MAX_COUNT,
				        DOM_BUILDING_BLOCS_MAX_COUNT
				);
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public Player getCurrentPlayer() {
		return players.getCurrentPlayer();
	}

	public Player getOtherPlayer() {
		return players.getOtherPlayer();
	}

	public GameNextAction getNextAction() {
		return nextAction;
	}

	public void setNextAction(GameNextAction nextAction) {
		this.nextAction = nextAction;
	}

	public void setGameSettings(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
	}

	public GameSettings getGameSettings() {
		return gameSettings;
	}

	public void setFirstPlayer(Player player) {
		players.setFirstPlayer(player);
	}

	public void setSecondPlayer(Player player) {
		players.setSecondPlayer(player);
	}

	public Player getFirstPlayer() {
		return players.getFirstPlayer();
	}

	public Player getSecondPlayer() {
		return players.getSecondPlayer();
	}

	public boolean isFirstPlayerTurn() {
		return players.getFirstPlayer().equals(players.getCurrentPlayer());
	}

	public boolean isSecondPlayerTurn() {
		return players.getSecondPlayer().equals(players.getCurrentPlayer());
	}

	public GameBuidingBlocs getBuildingBlocs() {
		return buildingBlocs;
	}

	public void changePlayer() {
		players.changePlayer();
	}

}
