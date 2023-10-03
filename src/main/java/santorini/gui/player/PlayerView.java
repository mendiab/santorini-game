package santorini.gui.player;

import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class PlayerView {
	
	@FXML
	private Text title;
	
	@FXML
	private FlowPane playerFlowPane;
	
	@FXML
	private ImageView playerTurnIndicator;
	
	private WorkerGui[] workers;
	
	
	public PlayerView() {
		workers = new WorkerGui[2];
	}

	public FlowPane getPlayerFlowPane() {
		return playerFlowPane;
	}

	public void setPlayerFlowPane(FlowPane playerFlowPane) {
		this.playerFlowPane = playerFlowPane;
	}

	public WorkerGui getFirstWorker() {
		return workers[0];
	}

	public void setFirstWorker(WorkerGui firstWorker) {
		workers[0] = firstWorker;
	}

	public WorkerGui getSecondWorker() {
		return workers[1];
	}

	public void setSecondWorker(WorkerGui secondWorker) {
		workers[1] = secondWorker;
	}

	public WorkerGui[] getWorkers() {
		return workers;
	}

	public WorkerGui getSelectedWorkerGui() {
		return Arrays.stream(workers).filter(worker -> worker.isSelected()).findFirst().get();
	}

	public void setTurnIndicator(ImageView playerTurnIndicator) {
		this.playerTurnIndicator = playerTurnIndicator;
	}

	public ImageView getPlayerTurnIndicator() {
		return playerTurnIndicator;
	}

	public void setPlayerTurnIndicator(ImageView playerTurnIndicator) {
		this.playerTurnIndicator = playerTurnIndicator;
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

}
