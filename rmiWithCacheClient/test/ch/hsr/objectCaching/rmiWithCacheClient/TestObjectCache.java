package ch.hsr.objectCaching.rmiWithCacheClient;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.dto.ObjectRequest;

public class TestObjectCache {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetObject() {
		int objectID = 3;
		ObjectCache objectCache = new ObjectCache();
		MessageManagerFake messageManager = new MessageManagerFake();
		Object expectedObj = new Object();
		messageManager.requestedObject = expectedObj;
		objectCache.setMessageManager(messageManager);
		Object actualObj = objectCache.getObject(objectID);
		ObjectRequest objectRequest = (ObjectRequest) messageManager.transferObject;
		assertEquals(objectID, objectRequest.getObjectID());
		assertEquals(1, messageManager.sendMessageCount);
		assertEquals(expectedObj, actualObj);
		
		actualObj = objectCache.getObject(objectID);
		
	}

}
