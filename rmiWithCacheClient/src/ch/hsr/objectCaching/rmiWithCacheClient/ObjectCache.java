package ch.hsr.objectCaching.rmiWithCacheClient;

import java.io.IOException;
import java.util.HashMap;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ObjectRequest;

public class ObjectCache {
	
	private MessageManager messageManager;
	private HashMap<Integer, Object> objectCache;
	private HashMap<Integer, Boolean> isInvalidMap;
	
	public ObjectCache()
	{
		objectCache = new HashMap<Integer, Object>();
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public Object getObject(int objectID) 
	{
		if(objectCache.containsKey(objectID))
		{
			return objectCache.get(objectID);
		}
		else
		{
			sendObjectRequest(objectID);
			Object receivedObject = null;
			receivedObject = receiveObject(objectID, receivedObject);
			return receivedObject;
		}
		
	}

	private Object receiveObject(int objectID, Object receivedObject) {
		try {
			receivedObject =  messageManager.receiveObject();
			objectCache.put(objectID, receivedObject);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return receivedObject;
	}

	private void sendObjectRequest(int objectID) {
		ObjectRequest temp = new ObjectRequest();
		temp.setObjectID(objectID);
		messageManager.sendMessageCall(temp);
	}
	
	public void addObject(int objectID, Account account)
	{
		objectCache.put(objectID, account);
	}

	public void processMethodWithSideEffect(MethodCall methodCall) {
		messageManager.sendMessageCall(methodCall);
	}
	

}
