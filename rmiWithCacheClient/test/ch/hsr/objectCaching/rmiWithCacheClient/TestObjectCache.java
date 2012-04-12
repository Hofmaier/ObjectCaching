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
		objectCache.setMessageManager(messageManager);
		objectCache.getObject(objectID);
		ObjectRequest objectRequest = (ObjectRequest) messageManager.transferObject;
		assertEquals(objectID, objectRequest.getObjectID());
		
		
	}

}
