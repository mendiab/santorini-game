package santorini.service.impl;

public enum EnumResourceBundleKey {
	GAME_VIEW_WRONG_POSITION_FOR_BUILDING_BLOC("game.view.wrong.position.for.building.bloc"),
	GAME_VIEW_WRONG_POSITION_FOR_WORKER("game.view.wrong.position.for.worker"),
	GAME_VIEW_BUILDING_BLOC_NOT_AVAILABLE("game.view.building.bloc.not.available"), 
	GAME_VIEW_NO_POSITION_AVAILABLE_FOR_SELECTED_BUILDING_BLOC("game.view.no.position.available.for.selected.building.bloc"), 
	GAME_VIEW_NO_POSITION_AVAILABLE_FOR_SELECTED_WORKER("game.view.no.position.available.for.selected.worker"), 
	GAME_VIEW_SELECTED_WORKER_NOT_OWNED_BY_CURRENT_PLAYER("game.view.selected.worker.not.owned.by.current.player"),;


	private String key;

	EnumResourceBundleKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	


}
