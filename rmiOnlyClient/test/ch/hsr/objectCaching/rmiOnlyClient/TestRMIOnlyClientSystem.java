package ch.hsr.objectCaching.rmiOnlyClient;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TestRMIOnlyClientSystem {

	private RMIOnlyClientSystem system;
	
	@Before
	public void setUp() throws Exception {
		system = new RMIOnlyClientSystem();
	}

	@Test
	public void testGetAccountService() {
		AccountServiceStub accountService = (AccountServiceStub) system.getAccountService();
		assertNotNull("accountService is null", accountService);
		assertNotNull("streamprovider is null", accountService.getStreamProvider());
	}

}
