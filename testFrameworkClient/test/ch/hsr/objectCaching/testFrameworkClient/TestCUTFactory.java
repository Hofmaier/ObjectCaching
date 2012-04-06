package ch.hsr.objectCaching.testFrameworkClient;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;

public class TestCUTFactory {

	@Test
	public void testClientCreation() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ClientSystemUnderTest client = CUTFactory.generateCUT("ch.hsr.objectCaching.testFrameworkClient.FakeClientUnderTest");
		assertTrue(client instanceof FakeClientUnderTest);
	}
}
