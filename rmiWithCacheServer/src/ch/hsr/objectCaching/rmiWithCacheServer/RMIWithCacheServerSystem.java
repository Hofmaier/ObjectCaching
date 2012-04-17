package ch.hsr.objectCaching.rmiWithCacheServer;

import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyClientHandler;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyServerSystem;

public class RMIWithCacheServerSystem extends RMIOnlyServerSystem {

	@Override
	public ClientHandler getClientHandlerInstance() {
		RMIWithCacheClientHandler clientHandler = new RMIWithCacheClientHandler();
		return clientHandler;
	}
	
}
