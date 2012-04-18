package ch.hsr.objectCaching.rmiWithCacheServer;

import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyServerSystem;

public class RMIWithCacheServerSystem extends RMIOnlyServerSystem {
	
	private ObjectManager objectManager = new ObjectManager();

	@Override
	public ClientHandler getClientHandlerInstance() {
		RMIWithCacheClientHandler clientHandler = new RMIWithCacheClientHandler();
		clientHandler.setObjectManager(objectManager);
		return clientHandler;
	}
	
}
