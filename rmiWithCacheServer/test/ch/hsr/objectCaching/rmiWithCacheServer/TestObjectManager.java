package ch.hsr.objectCaching.rmiWithCacheServer;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountImpl;
import ch.hsr.objectCaching.dto.ObjectRequest;
import ch.hsr.objectCaching.dto.ObjectRequestResponse;

public class TestObjectManager {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProcessObjectRequest() {
		ObjectManager objectManager = new ObjectManager();
		Account account = new AccountImpl();
		objectManager.addAccount(account);
		
		ObjectRequest objectRequest = new ObjectRequest();
		int objectID = 23;
		objectRequest.setObjectID(objectID);
		ObjectRequestResponse response = objectManager.processObjectRequest(objectRequest);
		
		assertNotNull(response);
		assertNotNull(response.getRequestedObject());
	}

}
