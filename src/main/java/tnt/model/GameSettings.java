package tnt.model;

public class GameSettings {

	private String firstPlayerName;
	private String secondPlayerName;
	private EWorkerColor firstPlayerWorkerColor;
	private EWorkerColor secondPlayerWorkerColor;

	public void setFirstPlayerName(String name) {
		this.firstPlayerName = name;
	}

	public void setSecondPlayerName(String name) {
		this.secondPlayerName = name;
	}

	public void setFirstPlayerWorkerColor(EWorkerColor color) {
		this.firstPlayerWorkerColor = color;
	}

	public void setSecondPlayerWorkerColor(EWorkerColor color) {
		this.secondPlayerWorkerColor = color;
	}

	public String getFirstPlayerName() {
		return firstPlayerName;
	}

	public String getSecondPlayerName() {
		return secondPlayerName;
	}

	public EWorkerColor getFirstPlayerWorkerColor() {
		return firstPlayerWorkerColor;
	}

	public EWorkerColor getSecondPlayerWorkerColor() {
		return secondPlayerWorkerColor;
	}

}
