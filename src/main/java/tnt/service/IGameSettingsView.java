package tnt.service;

public interface IGameSettingsView {
	
	void gameSettingsOnSubmit();

	void displayGameSettingsErrorMessage(String errorMessage);

	void startGame();
}
