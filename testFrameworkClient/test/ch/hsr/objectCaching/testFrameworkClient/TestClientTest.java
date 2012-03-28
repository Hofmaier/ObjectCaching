package ch.hsr.objectCaching.testFrameworkClient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.Scenario;

public class TestClientTest {

	private TestClient client;
	
	@Before
	public void setUp(){
		ClientUnderTestFake fakeClient = new ClientUnderTestFake();
		Scenario s = new Scenario(1);
		client = new TestClient(s);
		client.setAccountService(fakeClient.getAccountService());
	}
	
	@Test
	public void testScenarioId(){
		assertEquals("Scenario id is wrong", 1, client.getScenario().getId());
	}
	
	@Test
	public void testNumberOfAccounts(){
		client.init();
		assertEquals("More accounts than expected", 2, client.getAccounts().size());
	}
	
	@Test
	public void testGetNextAccount(){
		client.init();
		Account a1 = client.getNextAccount();
		Account a2 = client.getNextAccount();
		Account a3 = client.getNextAccount();
		assertTrue(a1 == a3);
	}

}
