package ch.hsr.objectCaching.interfaces;

import ch.hsr.objectCaching.account.Account;

public interface ServerSystemUnderTest {

	public ClientHandler getClientHandlerInstance();
	
	public void addAccount(Account testObject);
	public void addMethodCalledListener(MethodCalledListener listener);
	
}
