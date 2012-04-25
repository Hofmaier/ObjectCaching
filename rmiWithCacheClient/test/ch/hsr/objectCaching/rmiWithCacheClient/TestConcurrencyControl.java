package ch.hsr.objectCaching.rmiWithCacheClient;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.util.ConcurrencyControl;

public class TestConcurrencyControl 
{
	private ConcurrencyControl concurrencyControl;
	private ObjectCache objectCache;
	
	@Before
	public void setUp()
	{
		objectCache = new ObjectCache();
		concurrencyControl = new ConcurrencyControl();
	}
	
	@Test
	public void invalidateObject()
	{
		//int objectId = 1;
		//concurrencyControl.invalidateObject(objectId);
		//assertTrue(concurrencyControl.isObjectInvalid(objectId));
	}
	
	@Test
	public void addObjectAndInvalidate()
	{
		//int objectId = 2;
		//concurrencyControl.addObject(objectId);
		//assertFalse(concurrencyControl.isObjectInvalid(objectId));
		//concurrencyControl.invalidateObject(objectId);
		//assertTrue(concurrencyControl.isObjectInvalid(objectId));
	}
	
	@Test
	public void addObjectToCache()
	{
		int objectID = 3;
		Account account = new AccountStub();
		objectCache.addObject(objectID, account);
		//assertFalse(concurrencyControl.isObjectInvalid(objectID));
	}
}
