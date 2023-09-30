package tnt.gui.player;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class PlayersViews {
	
	private List<PlayerView> players;
	private int currentPlayerGuiIndex;
	private int otherPlayerGuiIndex;
	
	public PlayersViews() {
		players = new ArrayList<>(2);
		currentPlayerGuiIndex = 0;
		otherPlayerGuiIndex = 1;
	}
	
	public void initFirstPlayerGui(PlayerView firstPlayerViewController) {
		firstPlayerViewController.setTitle(String.format("Player-%d", 1));
		players.add(0, firstPlayerViewController);
	}
	
	public void initSecondPlayerGui(PlayerView secondPlayerViewController) {
		secondPlayerViewController.setTitle(String.format("Player-%d", 2));
		players.add(1, secondPlayerViewController);
	}

	public void initFirstPlayerGui(FlowPane playerFlowPane, ImageView playerTurnIndicator) {
		addPlayerGuiAtIndex(0, playerFlowPane, playerTurnIndicator);
	}

	public void initSecondPlayerGui(FlowPane playerFlowPane, ImageView playerTurnIndicator) {
		addPlayerGuiAtIndex(1, playerFlowPane, playerTurnIndicator);
	}
	
	private void addPlayerGuiAtIndex(int index, FlowPane playerFlowPane, ImageView playerTurnIndicator) {
		PlayerView player = new PlayerView();
		players.set(index, player);
	}
	
	public PlayerView getFirstPlayerGui() {
		return players.get(0);
	}
	
	public PlayerView getSecondPlayerGui() {
		return players.get(1);
	}
	
	public PlayerView getCurrentPlayerGui() {
		return players.get(currentPlayerGuiIndex);
	}
	
	public PlayerView getOtherPlayerGui() {
		return players.get(otherPlayerGuiIndex);
	}
	
	public void changePlayer() {
		currentPlayerGuiIndex = (currentPlayerGuiIndex + 1) % 2;
		otherPlayerGuiIndex = (otherPlayerGuiIndex + 1) % 2;
	}

}
