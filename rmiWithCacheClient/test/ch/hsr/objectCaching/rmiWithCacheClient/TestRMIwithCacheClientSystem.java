package ch.hsr.objectCaching.rmiWithCacheClient;

import static org.junit.Assert.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.rmiOnlyClient.IStreamProvider;

public class TestRMIwithCacheClientSystem {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetAccountService() {
		RMIwithCacheClientSystem system = new RMIwithCacheClientSystem();
		system.setStreamProvider(new StreamProviderFake());
		AccountServiceStub serviceStub = (AccountServiceStub) system.getAccountService();
		assertNotNull(serviceStub.getMessageManager());
		MessageManager msnMng = serviceStub.getMessageManager();
		assertNotNull(msnMng.getStreamProvider());
	}
	
	class StreamProviderFake implements IStreamProvider{

		@Override
		public ObjectOutputStream getObjectOutputStream() {
			return null;
		}

		@Override
		public ObjectInputStream getObjectInputStream() {
			return null;
		}

		@Override
		public void setSocketAdress(InetSocketAddress socketAdress) {
			
		}
	}


}
