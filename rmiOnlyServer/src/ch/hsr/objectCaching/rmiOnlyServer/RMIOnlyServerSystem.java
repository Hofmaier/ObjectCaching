package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.interfaces.ServerSystemUnderTest;

public class RMIOnlyServerSystem implements ServerSystemUnderTest {
	
	private AccountSkeleton accountSkeleton = new AccountSkeleton();

	@Override
	public ClientHandler getClientHandlerInstance() {
		RMIonlyClientHandler clientHandler = new RMIonlyClientHandler();
		clientHandler.setSkeleton(accountSkeleton);
		
		return clientHandler;
	}

	@Override
	public void addAccountObject(Account testObject) {
		// TODO Auto-generated method stub
		
	}

}
