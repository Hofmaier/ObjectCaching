package ch.hsr.objectCaching.rmiWithCacheClient;

import java.util.HashMap;

public class ConcurrencyControl 
{

	HashMap<Integer, Boolean> invalidateMap;
	public ConcurrencyControl()
	{
		invalidateMap  = new HashMap<Integer, Boolean>();
	}
	
	public boolean isObjectInvalid(int objectId)
	{
		if(invalidateMap.containsKey(objectId))
		{
			return invalidateMap.get(objectId).booleanValue();
		}
		return false;
	}
	
	public void invalidateObject(int objectId)
	{
		invalidateMap.put(objectId, true);
	}
}
