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
	}

	public void setObjectRead(int objectID) {
		// TODO Auto-generated method stub
		
	}
	
}
