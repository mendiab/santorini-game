package santorini.gui;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import santorini.factory.GameFactory;
import santorini.gui.player.PieceFigureFatory;
import santorini.gui.player.PlayerView;
import santorini.gui.player.PlayersViews;
import santorini.gui.player.WorkerGui;
import santorini.model.BoardPosition;
import santorini.model.BuildingBloc;
import santorini.model.GameBoard;
import santorini.model.Player;
import santorini.model.Worker;
import santorini.service.IGameService;
import santorini.service.IGameView;

public class GameView implements IGameView {

	private PlayersViews playersGui;
	
	@FXML
    private ResourceBundle resources; // MENOUER: VERY IMPORTANT: to be recognized the ResourceBundle field MUST BE "resources". Any other name will not work!!
	
	@FXML
	private AnchorPane gameMainPane;

	@FXML
	private GridPane gameBoardGridPane;

	@FXML
	private TextArea messageToPlayer;
	
	@FXML
	private Button restartGameButton;

	private IGameService gameService;

	private StackPane[][] boardGridArray;

	// ########################################## Player-Views Nested Components
	// ###################
	@FXML
	private Parent firstPlayerView;

	@FXML
	private PlayerView firstPlayerViewController; // SEHR WICHTIG NAME SOLL Name-View + Controller also firstPlayerView
													// and Controller in order to be injected

	@FXML
	private Parent secondPlayerView;

	@FXML
	private PlayerView secondPlayerViewController;

	@FXML
	private Parent gameBuidingBlocksView;

	@FXML
	private GameBuildingBlocksView gameBuidingBlocksViewController;

	// ########################################## Building-Blocks Nested Views
	// Nested Components ###################

	public GameView() {
		gameService = GameFactory.getGameService();
		gameService.setGameView(this);
		playersGui = new PlayersViews();
	}

	@Override
	public void changePlayer() {
		playersGui.changePlayer();
		markCurrentPlayerGuiAsSelected();
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		onInitGameView();
	}
	
	@FXML
	public void restartGame() {
		gameService.restartGame();
	}
    
	@Override
	public void returnToGameSettingsView() {
		try {
			Parent gameSettingsPane = FXMLLoader.load(getClass().getResource("/views/game-settings-view.fxml"));
			Stage stage = (Stage) gameMainPane.getScene().getWindow();
			stage.setScene(new Scene(gameSettingsPane));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onInitGameView() {
		initGameBoardArray();

		playersGui.initFirstPlayerGui(firstPlayerViewController);
		playersGui.initSecondPlayerGui(secondPlayerViewController);

		initWorkersFlowPane(gameService.getCurrentPlayer(), playersGui.getCurrentPlayerGui());
		initWorkersFlowPane(gameService.getOtherPlayer(), playersGui.getOtherPlayerGui());

		markCurrentPlayerGuiAsSelected();

		markPossibleMovePositions(gameService.findPossibleInitialMovePositionsForWorker());

	}

	private EventHandler<? super MouseEvent> handleGameBoardPositionOnClick() {
		return (MouseEvent e) -> {
			StackPane c = (StackPane) e.getSource();
			BoardPosition boardPosition = findGameBoardPosition(c);
			gameService.handleBoardPositionSelection(boardPosition);
		};
	}

	private EventHandler<MouseEvent> handleWorkerOnClick(Player player, Worker worker) {
		return (MouseEvent e) -> {
			if (worker.isPlacedOnBoard()) {
				WorkerGui selectedWorkerGui = (WorkerGui) e.getSource();
				// Menouer: use consume to avoid that other components handle the event.
				e.consume();
				gameService.handleWorkerSelection(player, worker);
			}
		};
	}

	private void markCurrentPlayerGuiAsSelected() {
		PlayerView currentPlayerGui = playersGui.getCurrentPlayerGui();
		currentPlayerGui.getPlayerTurnIndicator().setVisible(true);

		// Other player
		PlayerView otherPlayerGui = playersGui.getOtherPlayerGui();
		otherPlayerGui.getPlayerTurnIndicator().setVisible(false);

		// select current player workers
		markWorkerAsSelected(currentPlayerGui.getFirstWorker());
		markWorkerAsSelected(currentPlayerGui.getSecondWorker());

		// disable other player workers
		markWorkerAsNotSelected(otherPlayerGui.getFirstWorker());
		markWorkerAsNotSelected(otherPlayerGui.getSecondWorker());

	}

	private void markPossibleMovePositions(List<BoardPosition> positions) {
		updatePossibleMovePositionsDisplay(positions);
	}

	@Override
	public void markSelectedBuidingBlocAsSelected(int level) {
		gameBuidingBlocksViewController.markSelectedBuidingBlocAsSelected(level);
	}

	private void markWorkerAsNotSelected(WorkerGui workerGui) {
		workerGui.setSelected(false);
		workerGui.getStrokeDashArray().clear();
		workerGui.setStrokeWidth(0);
	}

	private void markWorkerAsSelected(WorkerGui workerGui) {
		workerGui.setSelected(true);
		workerGui.setStroke(Color.BLACK);
		workerGui.getStrokeDashArray().addAll(5.0, 5.0, 5.0, 5.0);
		workerGui.setStrokeWidth(3);
	}

	@Override
	public void moveWorkerToSelectedPosition(Worker worker, BoardPosition position) {
		moveSelectedWorkerGuiToTargetPosition(position);
		removeSelectedWorkerFromCurrentPlayerGui();
		markWorkerAsNotSelected(playersGui.getCurrentPlayerGui().getSelectedWorkerGui());
	}

	private void moveSelectedWorkerGuiToTargetPosition(BoardPosition position) {
		WorkerGui selectedWorkerGui = playersGui.getCurrentPlayerGui().getSelectedWorkerGui();
		StackPane stackPane = boardGridArray[position.getRow()][position.getColumn()];
		stackPane.getChildren().add(selectedWorkerGui);
	}

	@Override
	public void moveSelectedBuildingBlocToPosition(BuildingBloc selectedBuildingBloc, BoardPosition selectedPosition) {
		int level = selectedBuildingBloc.getLevel();
		ImageView selectedImageView = gameBuidingBlocksViewController.getSelectedBuildingBloc(level);
		StackPane stackPane = boardGridArray[selectedPosition.getRow()][selectedPosition.getColumn()];
		stackPane.getChildren().add(selectedImageView);
		gameBuidingBlocksViewController.updateRemainingBlocksCountOfSelectedLevel(level);
		gameBuidingBlocksViewController.markSelectedBuidingBlocAsDisabled(level);
	}

	private void initGameBoardArray() {
		this.boardGridArray = new StackPane[GameBoard.DIMENSION][GameBoard.DIMENSION];
		int rowIndex = 0;
		int colIndex = 0;
		for (Node node : this.gameBoardGridPane.getChildren()) {
			boardGridArray[rowIndex][colIndex] = (StackPane) node;
			node.setOnMouseClicked(handleGameBoardPositionOnClick());
	
			colIndex++;
			if (colIndex == GameBoard.DIMENSION) {
				colIndex = 0;
				rowIndex++;
			}
		}
	}

	private void initWorkersFlowPane(Player player, PlayerView playerGui) {
		WorkerGui firstWorker = PieceFigureFatory.createWorkerWithColor("First-Worker",
				Color.valueOf(player.getWorkerColor().name()));
		WorkerGui secondWorker = PieceFigureFatory.createWorkerWithColor("Second-Worker",
				Color.valueOf(player.getWorkerColor().name()));

		firstWorker.setOnMouseClicked(handleWorkerOnClick(player, player.getFirstWorker()));
		secondWorker.setOnMouseClicked(handleWorkerOnClick(player, player.getSecondWorker()));
		FlowPane playerFlowPane = playerGui.getPlayerFlowPane();
		playerGui.setFirstWorker(firstWorker);
		playerGui.setSecondWorker(secondWorker);
		playerFlowPane.getChildren().addAll(firstWorker, secondWorker);
	}

	private BoardPosition findGameBoardPosition(StackPane c) {
		for (int row = 0; row < GameBoard.DIMENSION; row++) {
			for (int col = 0; col < GameBoard.DIMENSION; col++) {
				if (boardGridArray[row][col].equals(c)) {
					return new BoardPosition(row, col);
				}
			}
		}
		throw new IllegalStateException("GameBoardPosition not found!");
	}

	private void removeSelectedWorkerFromCurrentPlayerGui() {
		PlayerView currentPlayerGui = playersGui.getCurrentPlayerGui();
		WorkerGui selectedWorkerGui = currentPlayerGui.getSelectedWorkerGui();
		currentPlayerGui.getPlayerFlowPane().getChildren().remove(selectedWorkerGui);
	}

	@Override
	public void onPlayerWorkerSelection(List<BoardPosition> possibleMovePositions) {
		if (possibleMovePositions != null) {
			markPossibleMovePositions(possibleMovePositions);
		}
	}

	@Override
	public void displayErrorMessage(String key) {
		String error = resources.getString(key);
		messageToPlayer.setText(String.format("ERROR-MESSAGE: %s", error));
	}

	@Override
	public void displayInfoMessage(String message) {
		messageToPlayer.setText(String.format("INFO-MESSAGE: %s", message));
	}

	@Override
	public void clearMessage() {
		messageToPlayer.clear();
	}

	@Override
	public void updatePossibleMovePositionsDisplay(List<BoardPosition> possibleMovePositions) {

		for (Node node : gameBoardGridPane.getChildren()) {
			node.setEffect(null);
		}

		InnerShadow ds = new InnerShadow(20, Color.RED);

		for (BoardPosition boardPosition : possibleMovePositions) {
			StackPane node = boardGridArray[boardPosition.getRow()][boardPosition.getColumn()];
			node.setEffect(ds);
		}

	}
	
	@Override
	public void updateWorkersSelection() {
		
		PlayerView currentPlayerGui = playersGui.getCurrentPlayerGui();

		WorkerGui[] guiWorkers = currentPlayerGui.getWorkers();

		for (WorkerGui worker : guiWorkers) {
			markWorkerAsNotSelected(worker);
		}
		
		WorkerGui selectedWorkerGui = this.gameService.getCurrentPlayer().isFirstWorkerSelected() 
				? currentPlayerGui.getFirstWorker() 
				: currentPlayerGui.getSecondWorker();
		
		markWorkerAsSelected(selectedWorkerGui);
	}

	@Override
	public void disableBoardPositions(Set<BoardPosition> positions) {
		for (BoardPosition boardPosition : positions) {
			StackPane node = boardGridArray[boardPosition.getRow()][boardPosition.getColumn()];
			node.setEffect(null);
		}
	}

}
