package ch.hsr.objectCaching.rmiOnlyClient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.interfaces.AccountService;

public class TestRMIonlyClientSystem {

	private RMIonlyClientSystem system;
	
	@Before
	public void setUp() throws Exception {
		system = new RMIonlyClientSystem();
	}

	@Test
	public void testGetAccountService() {
		AccountService accountService = system.getAccountService();
		assertNotNull("accountService is null", accountService);
	}

}
