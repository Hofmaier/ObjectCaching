package ch.hsr.objectCaching.rmiOnlyClient;

import java.net.InetSocketAddress;

import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;

public class RMIonlyClientSystem implements ClientSystemUnderTest {
	
	private IStreamProvider streamProvider = new StreamProvider();

	@Override
	public AccountService getAccountService() {
		AccountServiceStub accountServiceStub = new AccountServiceStub();
		accountServiceStub.setStreamProvider(streamProvider);
		return accountServiceStub;
	}

	@Override
	public void setServerSocketAdress(InetSocketAddress socketAdress) {
		streamProvider.setSocketAdress(socketAdress);
	}

	@Override
	public void shutdown() {
		
	}
}
