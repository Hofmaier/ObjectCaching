package ch.hsr.objectCaching.rmiOnlyClient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestAccountStub {
	
	private AccountStub accountStub;

	@Before
	public void setUp() throws Exception {
		accountStub = new AccountStub();
	}

	@Test
	public void testGetBalance() {
		accountStub.getBalance();
	}

	@Test
	public void testSetBalance() {
		fail("Not yet implemented");
	}

}
