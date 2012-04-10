package ch.hsr.objectCaching.rmiWithCacheClient;

import ch.hsr.objectCaching.account.Account;

public class AccountStub implements Account {

	private int objectID;
	
	@Override
	public double getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBalance(double balance) throws RuntimeException {
		// TODO Auto-generated method stub

	}

	public void setObjectID(Integer i) {
		objectID = i;
	}


}
