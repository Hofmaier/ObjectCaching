package ch.hsr.objectCaching.rmiWithCacheClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;


public class AccountServiceStub extends ch.hsr.objectCaching.rmiOnlyClient.AccountServiceStub {
	
	private ObjectCache objectCache;
	private MessageManager messageManager;
	
	@Override
	protected void sendMethodCall() throws IOException {
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(AccountService.class.getName());
		messageManager.sendMessageCall(methodCall);
	}

	protected Collection<Account> composeCollection(ReturnValue returnValue) {
		ArrayList<Account> retValCollection = new ArrayList<Account>();
		Collection<Integer> objectIDcollection = (Collection<Integer>) returnValue.getValue(); 
		
		for(Integer i:objectIDcollection){
			AccountStub accountStub = new AccountStub();
			accountStub.setObjectID(i); 
			retValCollection.add(accountStub);
			accountStub.setCache(objectCache);
		}
		return retValCollection;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	@Override
	protected ReturnValue receiveMethodCall() throws IOException,
			ClassNotFoundException {
		return messageManager.receiveReturnValue();
	}

	public ObjectCache getObjectCache() {
		return objectCache;
	}

	public void setObjectCache(ObjectCache objectCache) {
		this.objectCache = objectCache;
	}

}
