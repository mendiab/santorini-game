package santorini.model;

import java.util.ArrayList;
import java.util.List;

public class Players {
	
	private List<Player> players;
	private int currentPlayerIndex;
	private int otherPlayerIndex;
	
	public Players() {
		players = new ArrayList<>(2);
		currentPlayerIndex = 0;
		otherPlayerIndex = 1;
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public Player getOtherPlayer() {
		return players.get(otherPlayerIndex);
	}
	
	public Player getFirstPlayer() {
		return players.get(0);
	}

	public Player getSecondPlayer() {
		return players.get(1);
	}
	
	public void setFirstPlayer(Player firstPlayer) {
		players.add(0, firstPlayer);
	}
	
	public void setSecondPlayer(Player secondPlayer) {
		players.add(1, secondPlayer);
	}
	
	public void changePlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % 2;
		otherPlayerIndex = (otherPlayerIndex + 1) % 2;
	}
	
}
