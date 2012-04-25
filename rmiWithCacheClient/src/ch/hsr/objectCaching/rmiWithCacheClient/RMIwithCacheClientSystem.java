package ch.hsr.objectCaching.rmiWithCacheClient;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;
import ch.hsr.objectCaching.rmiOnlyClient.StreamProvider;


public class RMIwithCacheClientSystem implements ClientSystemUnderTest {
	
	private StreamProvider streamProvider = new StreamProvider();
	private MessageManager messageManager;

	@Override
	public AccountService getAccountService() {
		AccountServiceStub serviceStub = new AccountServiceStub();
		messageManager = new MessageManager();
		ObjectCache objectCache = new ObjectCache();
		objectCache.setMessageManager(messageManager);
		objectCache.startUpdateObjectThread();
		streamProvider.getObjectInputStream();
		messageManager.setStreamProvider(streamProvider);
		
		messageManager.startSenderThread();
		messageManager.startReceiverThread();
		
		serviceStub.setMessageManager(messageManager);
		serviceStub.setObjectCache(objectCache);
		return serviceStub;
	}

	@Override
	public void setServerSocketAdress(InetSocketAddress socketAdress) {
		streamProvider.setSocketAdress(socketAdress);
	}

	@Override
	public void shutdown() {
		messageManager.shutDown();
	}

}
