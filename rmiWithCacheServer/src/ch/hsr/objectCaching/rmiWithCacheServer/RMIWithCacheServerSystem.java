package ch.hsr.objectCaching.rmiWithCacheServer;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.interfaces.MethodCalledListener;
import ch.hsr.objectCaching.interfaces.ServerSystemUnderTest;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyClientHandler;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyServerSystem;

public class RMIWithCacheServerSystem extends RMIOnlyServerSystem {

	@Override
	public ClientHandler getClientHandlerInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAccount(Account testObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMethodCalledListener(MethodCalledListener listener) {
		// TODO Auto-generated method stub

	}

}
