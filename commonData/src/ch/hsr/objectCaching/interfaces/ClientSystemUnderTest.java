package ch.hsr.objectCaching.interfaces;

import java.net.InetSocketAddress;

import ch.hsr.objectCaching.account.AccountService;

public interface ClientSystemUnderTest {
	
	public AccountService getAccountService();
	public void setServerSocketAdress(InetSocketAddress socketAdress);
	public void shutdown();

}
