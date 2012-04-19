package ch.hsr.objectCaching.rmiWithCacheClient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestConcurrencyControl 
{
	private ConcurrencyControl concurrencyControl;
	
	@Before
	public void setUp()
	{
		concurrencyControl = new ConcurrencyControl();
	}
	
	@Test
	public void invalidateObject()
	{
		int objectId = 3;
		concurrencyControl.invalidateObject(objectId);
		assertTrue(concurrencyControl.isObjectInvalid(objectId));
	}
	
	@Test
	public void addObjectAndInvalidate()
	{
		int objectId = 4;
		concurrencyControl.addObject(objectId);
		assertFalse(concurrencyControl.isObjectInvalid(objectId));
		concurrencyControl.invalidateObject(objectId);
		assertTrue(concurrencyControl.isObjectInvalid(objectId));
	}
}
