package ch.hsr.objectCaching.rmiOnlyServer;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.interfaces.AccountService;

public class TestAccountServiceSkeleton {
	
	private AccountServiceSkeleton accountServiceSkeleton;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInvokeMethod() {
		MethodCall methodCall = new MethodCall();
		
		methodCall.setMethodName(getGetAllAccounts().getName());
		ReturnValue actualRetVal = accountServiceSkeleton.invokeMethod(methodCall);
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
