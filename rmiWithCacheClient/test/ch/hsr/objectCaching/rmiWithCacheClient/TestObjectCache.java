package ch.hsr.objectCaching.rmiWithCacheClient;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountImpl;
import ch.hsr.objectCaching.dto.ObjectRequest;
import ch.hsr.objectCaching.dto.ObjectRequestResponse;

public class TestObjectCache {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetObject() {
		int objectID = 3;
		ObjectCache objectCache = new ObjectCache();
		MessageManagerFake messageManager = new MessageManagerFake();
		ObjectRequestResponse response = new ObjectRequestResponse();
		Account acc = new AccountImpl();
		response.setRequestedObject(acc);
		messageManager.setRequestedObject(response);
		messageManager.requestedObject = response;
		objectCache.setMessageManager(messageManager);
		Object actualObj = objectCache.getObject(objectID);
		ObjectRequest objectRequest = (ObjectRequest) messageManager.transferObject;
		assertEquals(objectID, objectRequest.getObjectID());
		assertEquals(1, messageManager.sendMessageCount);
		assertEquals(acc, actualObj);
		
		actualObj = objectCache.getObject(objectID);
		assertEquals(1, messageManager.sendMessageCount);
	}

}
