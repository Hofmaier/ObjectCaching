package ch.hsr.objectCaching.rmiOnlyClient;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TestRMIonlyClientSystem {

	private RMIonlyClientSystem system;
	
	@Before
	public void setUp() throws Exception {
		system = new RMIonlyClientSystem();
	}

	@Test
	public void testGetAccountService() {
		AccountServiceStub accountService = (AccountServiceStub) system.getAccountService();
		assertNotNull("accountService is null", accountService);
		assertNotNull("streamprovider is null", accountService.getStreamProvider());
	}

}
