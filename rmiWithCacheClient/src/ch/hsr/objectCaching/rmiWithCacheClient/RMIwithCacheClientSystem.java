package ch.hsr.objectCaching.rmiWithCacheClient;
import java.net.InetSocketAddress;

import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;


public class RMIwithCacheClientSystem implements ClientSystemUnderTest {

	@Override
	public AccountService getAccountService() {
		AccountServiceStub serviceStub = new AccountServiceStub();
		serviceStub.setMessageManager(new MessageManager());
		 return serviceStub;
	}

	@Override
	public void setServerSocketAdress(InetSocketAddress socketAdress) {
		
	}

	@Override
	public void shutdown() {
		
	}

}
