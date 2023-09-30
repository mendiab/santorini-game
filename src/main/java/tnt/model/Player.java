package tnt.model;

import java.util.Arrays;
import java.util.Objects;

public class Player {

	private final String playerName;
	private final EWorkerColor workerColor;
	private Worker[] workers;

	public Player(String playerName, EWorkerColor workerColor) {
		this.playerName = playerName;
		this.workerColor = workerColor;
		
		//workers
		workers = new Worker[]{new Worker(workerColor), new Worker(workerColor)};
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public EWorkerColor getWorkerColor() {
		return workerColor;
	}

	public Worker[] getWorkers() {
		return workers;
	}

	public boolean owns(Worker worker) {
		return Arrays.stream(workers).anyMatch(w -> w == worker);
	}

	public Worker getSelectedWorker() {
		return Arrays.stream(workers).filter(worker -> worker.isSelected()).findFirst().orElse(null);
	}

	public boolean hasAllWorkersPlacedOnBoard() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(playerName, other.playerName);
	}

	public Worker getFirstWorker() {
		return workers[0];
	}

	public Worker getSecondWorker() {
		return workers[1];
	}

	public boolean isOnGameBoard() {
		boolean workerNotPlayedYet = Arrays.stream(workers).anyMatch(worker -> !worker.isPlacedOnBoard());
		return !workerNotPlayedYet;
	}

	public boolean hasWorker(Worker worker) {
		return Arrays.stream(workers).anyMatch(w -> w.equals(worker));
	}

	public boolean isFirstWorkerSelected() {
		return getFirstWorker().isSelected();
	}
	
	public boolean isSecondWorkerSelected() {
		return getSecondWorker().isSelected();
	}

	public Worker getOtherWorker() {
		return Arrays.stream(workers).filter(worker -> !worker.isSelected()).findFirst().orElse(null);
	}

}
