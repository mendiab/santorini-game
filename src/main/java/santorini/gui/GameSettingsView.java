package santorini.gui;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import santorini.model.EWorkerColor;
import santorini.model.GameSettings;
import santorini.service.IGameService;
import santorini.service.IGameSettingsView;

public class GameSettingsView implements IGameSettingsView {
	
	private GuiMain parentMainGui;
	
	@FXML
    private ResourceBundle resources;

	@FXML
	private GridPane settingsPane;

	@FXML
	private Button gameSettingsSubmitButton;

	@FXML
	private TextField firstPlayerNameTxtField;

	@FXML
	private TextField secondPlayerNameTxtField;

	@FXML
	private RadioButton firstPlayerWorkerRedColorRdBtn;

	@FXML
	private RadioButton secondPlayerWorkerRedColorRdBtn;

	@FXML
	private RadioButton firstPlayerWorkerBlueColorRdBtn;

	@FXML
	private RadioButton secondPlayerWorkerBlueColorRdBtn;

	@FXML
	private Text validationErrorMessage;

	private IGameService gameService;

	@FXML
	private void initialize() {
		gameService = GuiMain.GAME_SERVICE;
		gameService.setSettingsView(this);
	}

	@Override
	@FXML
	public void gameSettingsOnSubmit() {
		GameSettings gameSettings = readGameSettings();
		gameService.handleGameSettingsOnSubmit(gameSettings);
	}

	private GameSettings readGameSettings() {
		GameSettings gameSettings = new GameSettings();
		gameSettings.setFirstPlayerName(firstPlayerNameTxtField.getText());
		gameSettings.setSecondPlayerName(secondPlayerNameTxtField.getText());
		String firstPlayerColorLabel = firstPlayerWorkerRedColorRdBtn.isSelected() ? firstPlayerWorkerRedColorRdBtn.getText().toUpperCase() : firstPlayerWorkerBlueColorRdBtn.getText().toUpperCase();
		String secondPlayerColorLabel = secondPlayerWorkerRedColorRdBtn.isSelected() ? secondPlayerWorkerRedColorRdBtn.getText().toUpperCase() : secondPlayerWorkerBlueColorRdBtn.getText().toUpperCase();
		EWorkerColor firstPlayerColor = EWorkerColor.valueOf(firstPlayerColorLabel);
		EWorkerColor secondPlayerColor = EWorkerColor.valueOf(secondPlayerColorLabel);
		gameSettings.setFirstPlayerWorkerColor(firstPlayerColor);
		gameSettings.setSecondPlayerWorkerColor(secondPlayerColor);
		return gameSettings;
	}
	
	@Override
	public void startGame() {
		try {
			parentMainGui.gotoGameView();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void displayGameSettingsErrorMessage(String errorMessage) {
		validationErrorMessage.setText(errorMessage);
	}

	public void setMainGui(GuiMain mainGui) {
		this.parentMainGui = mainGui;
	}

}
