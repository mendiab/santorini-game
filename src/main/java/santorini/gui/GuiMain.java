package santorini.gui;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import santorini.factory.GameFactory;
import santorini.service.IGameService;

/**
 * 
 * @author Menouer Diab
 * 
 * GuiMain is responsible for routing to the multiple pages of the application
 *
 */
public class GuiMain extends Application {

	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("resourcebundle/resources");
	private Stage primaryStage;
	public static IGameService GAME_SERVICE = GameFactory.getGameService();

	public GuiMain() {}

	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		primaryStage.setTitle("Santorini Game");
		primaryStage.setOnCloseRequest(x -> {
			Platform.exit();
		});
		primaryStage.setResizable(true);
		gotoGameSettingsView();
		primaryStage.show();
	}
	
	/**
	 * Displays the Game-Settings-View
	 * @throws IOException
	 */
	public void gotoGameSettingsView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GuiMain.class.getResource("/views/game-settings-view.fxml"));
        loader.setResources(BUNDLE);
        Parent settingsPane = loader.load();
        primaryStage.setScene(new Scene(settingsPane));
		GameSettingsView gameSettingsView = loader.getController();
		gameSettingsView.setMainGui(this);
	}
	
	/**
	 * Displays the Game-View
	 * @throws IOException
	 */
	public void gotoGameView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GuiMain.class.getResource("/views/game-view.fxml"));
        loader.setResources(BUNDLE);
        Parent gamePane = loader.load();
        primaryStage.setScene(new Scene(gamePane));
        GameView gameView = loader.getController();
        gameView.setMainGui(this);
	}

	/**
	 * The entry point of the GUI application.
	 *
	 * @param args The command line arguments passed to the application
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
