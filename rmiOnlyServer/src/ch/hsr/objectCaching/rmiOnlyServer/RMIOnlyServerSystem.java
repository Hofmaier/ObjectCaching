package ch.hsr.objectCaching.rmiOnlyServer;

import java.util.ArrayList;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.interfaces.MethodCalledListener;
import ch.hsr.objectCaching.interfaces.ServerSystemUnderTest;


public class RMIOnlyServerSystem implements ServerSystemUnderTest {
	
	protected AccountSkeleton accountSkeleton = new AccountSkeleton();
	protected AccountServiceSkeleton accountServiceSkeleton = new AccountServiceSkeleton();
	protected AccountServiceImpl accountService = new AccountServiceImpl();
	private ArrayList<MethodCalledListener> listeners = new ArrayList<MethodCalledListener>();
	
	public RMIOnlyServerSystem(){
		accountServiceSkeleton.setAccountSkeleton(accountSkeleton);
	}

	@Override
	public ClientHandler getClientHandlerInstance() {
		RMIOnlyClientHandler clientHandler = new RMIOnlyClientHandler();
		clientHandler.setMethodCalledListeners(listeners);
		clientHandler.setAccountSkeleton(accountSkeleton);
		clientHandler.setAccountServiceSkeleton(accountServiceSkeleton);
		return clientHandler;
	}

	@Override
	public void addAccount(Account testObject) {
		accountSkeleton.addObject(ObjectIDGenerator.next(), testObject);
		accountService.addAccount(testObject);
	}

	@Override
	public void addMethodCalledListener(MethodCalledListener listener) {
		listeners.add(listener);
	}
}
