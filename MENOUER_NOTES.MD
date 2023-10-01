27.August 23
------------

- we fixed the issue related to the display of the available positions after a building bloc has been selected:
  ---> issue due to scenebuilder. The imageview with coordinates (0,0) was placed after the cell(4,4) of the last row.
  ---> this has caused the method getChildren() of the grid to deliver the imageviews in a wrong order i.e. as configured in scenebuilder.
  
- place where to continue:
  ---> logic to move building-bloc to selected position in board.
  
- to avoid that an event would be consumed more than one time, use this trick 'e.consume()' eg.

	private EventHandler<MouseEvent> handleWorkerOnClick(Player player, PlayerView playerGui, Worker worker) {
		return (MouseEvent e) -> {
			if (worker.isPlacedOnBoard()) {
				WorkerGui selectedWorkerGui = (WorkerGui) e.getSource();
				
				// use consume to avoid that other components handle the event.
				e.consume();
				
				if (player.equals(gameService.getCurrentPlayer())) {
					...
				} else {
					...
				}
			}
		};
	}
	
Text fields can be configured with the wrap-text property in scene builder to allow multilines text

1.Octobre

ResourceBundle:
--------------

1. must be passed in the load method as parameter:

FXMLLoader.load(getClass().getResource("/views/game-settings-view.fxml"), ResourceBundle.getBundle("resourcebundle/resources"));

2. resourcebundle can also be used in scene builder. You've to select internationalization in text attribute og Gui-Element

3. to be used in controller, it should be injected like this:

@FXML
    private ResourceBundle resources; // MENOUER: VERY IMPORTANT: to be recognized the ResourceBundle field MUST BE "resources". Any other name will not work!!
