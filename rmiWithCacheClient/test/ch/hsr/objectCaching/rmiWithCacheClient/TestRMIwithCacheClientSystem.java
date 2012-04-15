package ch.hsr.objectCaching.rmiWithCacheClient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestRMIwithCacheClientSystem {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetAccountService() {
		RMIwithCacheClientSystem system = new RMIwithCacheClientSystem();
		AccountServiceStub serviceStub = (AccountServiceStub) system.getAccountService();
		assertNotNull(serviceStub.getMessageManager());
		MessageManager msnMng = serviceStub.getMessageManager();
		assertNotNull(msnMng.getStreamProvider());
	}

}
