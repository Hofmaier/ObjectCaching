package ch.hsr.objectCaching.rmiWithCacheServer;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TestRMIWithCacheServerSystem {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetClientHandlerInstance() {
		RMIWithCacheServerSystem system = new RMIWithCacheServerSystem();
		RMIWithCacheClientHandler clientHandler = (RMIWithCacheClientHandler) system.getClientHandlerInstance();
		assertNotNull(clientHandler.getObjectManager());
	}

}
