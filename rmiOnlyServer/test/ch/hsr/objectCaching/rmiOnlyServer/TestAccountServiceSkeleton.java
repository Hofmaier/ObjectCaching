package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.interfaces.AccountService;

public class TestAccountServiceSkeleton {
	
	private AccountServiceSkeleton accountServiceSkeleton;

	@Before
	public void setUp() throws Exception {
		accountServiceSkeleton = new AccountServiceSkeleton();
	}

	@Test
	public void testInvokeMethod() { 
		MethodCall methodCall = new MethodCall();
		
		methodCall.setMethodName(getGetAllAccounts().getName());
		AccountSkeleton accountSkeleton = new AccountSkeleton();
		accountServiceSkeleton.setAccountSkeleton(accountSkeleton);
		int expectedObjectID = 23;
		accountSkeleton.addObject(expectedObjectID, new AccountImpl());
		ReturnValue actualRetVal = accountServiceSkeleton.invokeMethod(methodCall);
		Collection<Integer> objectIDs = (Collection<Integer>) actualRetVal.getValue();
		for(Integer i:objectIDs){
			assertEquals(Integer.valueOf(expectedObjectID), i);
		}
	}
	
	private Method getGetAllAccounts() {
		Method getAllAccountsMethod = null;
		Class<AccountService> serviceClass = AccountService.class;
		Method[] allMethods = serviceClass.getMethods();
		for(Method method:allMethods){
			if(method.getName().equals("getAllAccounts")){
				getAllAccountsMethod = method;
				break;
			}
		}
		return getAllAccountsMethod;
	}

}
