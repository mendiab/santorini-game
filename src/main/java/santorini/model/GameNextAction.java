package santorini.model;

public enum GameNextAction {
	GET_PLAYERS_SETTINGS, 
	SELECT_PLAYER_FIRST_WORKER_INITIAL_POSITION, 
	SELECT_PLAYER_SECOND_WORKER_INITIAL_POSITION,
	WORKER_TO_BE_MOVED_SELECTION, 
	CHECK_PLAYER_MOVE_POSITION_SELECTION, 
	CHECK_PLAYER_BUILDING_POSITION_SELECTION,
	SELECT_PLAYER_BUILDING_BLOC, RESTART_GAME,
}
