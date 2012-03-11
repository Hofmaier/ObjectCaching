package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.interfaces.ServerSystemUnderTest;

public class RMIOnlyServerSystem implements ServerSystemUnderTest {

	@Override
	public ClientHandler getClientHandlerInstance() {
		return null;
	}

	@Override
	public void addAccountObject(Account testObject) {
		// TODO Auto-generated method stub
		
	}

}
