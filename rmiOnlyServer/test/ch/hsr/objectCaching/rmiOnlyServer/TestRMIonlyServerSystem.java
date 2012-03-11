package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import ch.hsr.objectCaching.interfaces.ClientHandler;

public class TestRMIonlyServerSystem {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetClientHandlerInstance() {
		
		RMIOnlyServerSystem system = new RMIOnlyServerSystem();
		
		ClientHandler clientHandler = system.getClientHandlerInstance();
		
		assertNotNull("null object", clientHandler);
		
		
	}

}
