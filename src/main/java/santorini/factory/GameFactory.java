package santorini.factory;

import santorini.service.IGameService;
import santorini.service.impl.GameServiceImpl;

public class GameFactory {
	public static IGameService getGameService() {
		return GameServiceImpl.getInstance();
	}
}
