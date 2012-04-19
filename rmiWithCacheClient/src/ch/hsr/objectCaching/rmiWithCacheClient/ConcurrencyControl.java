package ch.hsr.objectCaching.rmiWithCacheClient;

import java.util.HashMap;

import ch.hsr.objectCaching.dto.MethodCall;

public class ConcurrencyControl 
{

	private HashMap<Integer, Boolean> invalidateMap;
	private HashMap<Integer, Integer> writeMap;
	private HashMap<Integer, Integer> readMap;
	public ConcurrencyControl()
	{
		invalidateMap  = new HashMap<Integer, Boolean>();
		readMap = new HashMap<Integer, Integer>();
		writeMap = new HashMap<Integer, Integer>();
	}
	
	public void processMethod(MethodCall methodCall)
	{
		if(methodCall.getMethodName().equals("setBalance"))
		{
		}
	}
	
	public boolean isObjectInvalid(int objectID)
	{
		if(invalidateMap.containsKey(objectID))
		{
			return invalidateMap.get(objectID).booleanValue();
		}
		return false;
	}
	
	public void invalidateObject(int objectID)
	{
		invalidateMap.put(objectID, true);
	}
	
	public void addObject(int objectID)
	{
		invalidateMap.put(objectID, false);
		readMap.put(objectID, 0);
		writeMap.put(objectID, 0);
	}

	public void setObjectRead(int objectID) 
	{
		int currentVersion = writeMap.get(objectID).intValue();
		if(readMap.get(objectID).intValue() != currentVersion)
		{
			readMap.put(objectID, currentVersion);
		}
	}
}
