package ch.hsr.objectCaching.testFrameworkClient;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;

public class CUTFactoryTest {

	@Test
	public void testClientCreation() {
		try {
			ClientSystemUnderTest client = CUTFactory.generateCUT("ch.hsr.objectCaching.testFrameworkClient.ClientUnderTestFake");
			assertTrue(client instanceof ClientUnderTestFake);
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("Instance could not be created: " + e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
