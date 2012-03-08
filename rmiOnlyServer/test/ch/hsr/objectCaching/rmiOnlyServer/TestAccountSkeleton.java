package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestAccountSkeleton {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetCalledObject() {
		AccountSkeleton skeleton = new AccountSkeleton();
		Account testAccount = new Account();
		skeleton.addObject(23, testAccount);
		MethodCall methodCall = new MethodCall();
		methodCall.setObjectID(23);
		Account account = skeleton.getCalledObject(methodCall);
		assertTrue(account == testAccount);
	}

}
