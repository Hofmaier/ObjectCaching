package ch.hsr.objectCaching.rmiWithCacheClient;

import java.io.IOException;
import java.util.HashMap;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountImpl;
import ch.hsr.objectCaching.dto.ObjectRequest;

public class ObjectCache {
	
	private MessageManager messageManager;
	private HashMap<Integer, Account> objectCache;
	
	public ObjectCache()
	{
		objectCache = new HashMap<Integer, Account>();
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public Account getObject(int objectID) 
	{
		if(objectCache.containsKey(objectID))
		{
			return objectCache.get(objectID);
		}
		else
		{
			sendObjectRequest(objectID);
			Account receivedObject = null;
			receivedObject = receiveObject(objectID, receivedObject);
			return receivedObject;
		}
		
	}

	private Account receiveObject(int objectID, Account receivedObject) {
		try {
			receivedObject = (Account) messageManager.receiveObject();
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
	

}
