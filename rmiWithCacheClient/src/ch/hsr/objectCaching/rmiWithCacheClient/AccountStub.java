package ch.hsr.objectCaching.rmiWithCacheClient;

import ch.hsr.objectCaching.account.Account;

public class AccountStub implements Account {

	private int objectID;
	private ObjectCache cache;
	
	@Override
	public double getBalance() {
		Account account = cache.getObject(objectID);
		return 0;
	}

	@Override
	public void setBalance(double balance) throws RuntimeException {
		// TODO Auto-generated method stub

	}

	public void setObjectID(Integer i) {
		objectID = i;
	}

	public int getObjectID() {
		return objectID;
	}


}
