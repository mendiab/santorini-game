package tnt.factory;

import tnt.service.IGameService;
import tnt.service.impl.GameServiceImpl;

public class GameFactory {
	public static IGameService getGameService() {
		return GameServiceImpl.getInstance();
	}
}
