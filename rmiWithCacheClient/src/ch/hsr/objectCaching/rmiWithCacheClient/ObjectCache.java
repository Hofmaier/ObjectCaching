package ch.hsr.objectCaching.rmiWithCacheClient;

import java.util.HashMap;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountImpl;

public class ObjectCache {
	
	private MessageManager messageManager;
	private HashMap<Integer, AccountImpl> objectCache;

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public Account getObject(int objectID) {
		return objectCache.get(objectID);
	}

}
