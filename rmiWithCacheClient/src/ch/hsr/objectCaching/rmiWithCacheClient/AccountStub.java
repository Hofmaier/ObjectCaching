package ch.hsr.objectCaching.rmiWithCacheClient;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.dto.MethodCall;

public class AccountStub implements Account {

	private int objectID;
	private ObjectCache cache;
	
	@Override
	public double getBalance() {
		Account account = (Account) cache.getObject(objectID);
		return account.getBalance();
	}

	@Override
	public void setBalance(double balance) throws RuntimeException {
		MethodCall methodCall = new MethodCall();
		
	}

	public void setObjectID(Integer i) {
		objectID = i;
	}

	public int getObjectID() {
		return objectID;
	}

	public ObjectCache getCache() {
		return cache;
	}

	public void setCache(ObjectCache cache) {
		this.cache = cache;
	}


}
