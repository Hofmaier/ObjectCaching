package ch.hsr.objectCaching.rmiWithCacheClient;

import java.lang.reflect.Method;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;

public class AccountStub extends ch.hsr.objectCaching.rmiOnlyClient.AccountStub {

	private int objectID;
	private ObjectCache cache;
	
	@Override
	public double getBalance() {
		Account account = (Account) cache.getObject(objectID);
		System.out.println("[CLIENT]read balance: " + account.getBalance());
		return account.getBalance();
	}

	@Override
	public void setBalance(double balance) throws RuntimeException {
		System.out.println("[CLIENT]set balance: " + balance);
		MethodCall methodCall = prepareMethodCall(balance);
		ReturnValue retVal = cache.processMethodWithSideEffect(methodCall);
		checkForExceptions(retVal);
	}

	private MethodCall prepareMethodCall(double balance) {
		MethodCall methodCall = new MethodCall();
		Method method = getMethod("setBalance");
		methodCall.setClassName(Account.class.getName());
		methodCall.setMethodName(method.getName());
		methodCall.setParameterTypes(method.getParameterTypes());
		Object[] arguments = new Object[]{balance};
		methodCall.setArguments(arguments);
		methodCall.setObjectID(objectID);
		return methodCall;
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
