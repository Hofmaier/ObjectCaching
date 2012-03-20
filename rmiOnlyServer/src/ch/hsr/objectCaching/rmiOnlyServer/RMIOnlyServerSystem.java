package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.interfaces.ServerSystemUnderTest;

public class RMIOnlyServerSystem implements ServerSystemUnderTest {
	
	private AccountSkeleton accountSkeleton = new AccountSkeleton();
	private AccountServiceSkeleton accountServiceSkeleton = new AccountServiceSkeleton();
	private AccountServiceImpl accountService = new AccountServiceImpl();
	
	public RMIOnlyServerSystem(){
		accountServiceSkeleton.setAccountSkeleton(accountSkeleton);
	}

	@Override
	public ClientHandler getClientHandlerInstance() {
		RMIonlyClientHandler clientHandler = new RMIonlyClientHandler();
		clientHandler.setAccountSkeleton(accountSkeleton);
		clientHandler.setAccountServiceSkeleton(accountServiceSkeleton);
		return clientHandler;
	}

	@Override
	public void addAccountObject(Account testObject) {
		accountSkeleton.addObject(ObjectIDGenerator.next(), testObject);
		accountService.addAccount(testObject);
	}
}
