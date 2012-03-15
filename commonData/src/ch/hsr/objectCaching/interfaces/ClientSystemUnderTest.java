package ch.hsr.objectCaching.interfaces;

public interface ClientSystemUnderTest {
	
	public AccountService getAccountService();
	
	public void write(int value);
	public int read();

}
