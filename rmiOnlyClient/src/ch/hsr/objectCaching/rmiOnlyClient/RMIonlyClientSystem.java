package ch.hsr.objectCaching.rmiOnlyClient;

import java.net.InetSocketAddress;

import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;

public class RMIonlyClientSystem implements ClientSystemUnderTest {

	@Override
	public AccountService getAccountService() {
		return new AccountServiceStub();
	}

	@Override
	public void setServerSocketAdress(InetSocketAddress socketAdress) {
		// TODO Auto-generated method stub
		
	}
}
