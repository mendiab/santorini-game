package tnt.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application {

	public void startGame(String[] args) {
		launch(args);
	}

    public GuiMain() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Santorini Game");
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });
        stage.setResizable(true);
        Parent settingsPane = FXMLLoader.load(getClass().getResource("/views/game-settings-view.fxml"));
		stage.setScene(new Scene(settingsPane));
        stage.show();
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
