package ch.hsr.objectCaching.rmiWithCacheClient;

import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.dto.ReturnValue;


public class AccountServiceStub extends ch.hsr.objectCaching.rmiOnlyClient.AccountServiceStub {
	
	private ObjectCache objectCache;
	
	protected Collection<Account> composeCollection(ReturnValue returnValue) {
		ArrayList<Account> retValCollection = new ArrayList<Account>();
		Collection<Integer> objectIDcollection = (Collection<Integer>) returnValue.getValue(); 
		
		for(Integer i:objectIDcollection){
			AccountStub accountStub = new AccountStub();
			accountStub.setObjectID(i); 
			retValCollection.add(accountStub);
			
		}
		return retValCollection;
	}


	

}
