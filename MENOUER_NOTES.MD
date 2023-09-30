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
