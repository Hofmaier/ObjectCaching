package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
		RMIonlyClientHandler rmiClientHandler = (RMIonlyClientHandler) clientHandler;
		
		assertNotNull("null object", clientHandler);
		assertNotNull("skeleton is null", rmiClientHandler.getSkeleton());
		
		RMIonlyClientHandler secondHandler = (RMIonlyClientHandler) system.getClientHandlerInstance();
		assertTrue(secondHandler.getSkeleton() == rmiClientHandler.getSkeleton());
	}

}
