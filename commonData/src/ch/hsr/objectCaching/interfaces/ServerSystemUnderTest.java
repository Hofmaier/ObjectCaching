package ch.hsr.objectCaching.interfaces;

public interface ServerSystemUnderTest {

	public ClientHandler getClientHandlerInstance();
	
	public void addAccount(Account testObject);
	
}
