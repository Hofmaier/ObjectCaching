package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;

public class TestAccountSkeleton {

	private AccountSkeleton skeleton;
	private Account testAccount;

	@Before
	public void setUp() throws Exception {
		skeleton = new AccountSkeleton();
		testAccount = new Account();
	}

	@Test
	public void testGetCalledObject() {
		int objectID = 23;
		skeleton.addObject(objectID, testAccount);
		
		MethodCall methodCall = new MethodCall();
		methodCall.setObjectID(objectID);
		Account account = skeleton.getCalledObject(methodCall);
		assertTrue(account == testAccount);
	}
	
	@Test
	public void testInvokeMethodOnObject() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		int balance = 200;
		testAccount.setBalance(balance);
		
		Class<Account> accountClass = Account.class;
		
		Method getBalanceMethod = null;
		Method[] allMethods = accountClass.getMethods();
		for(Method method:allMethods){
			if(method.getName().equals("getBalance")){
				getBalanceMethod = method;
				break;
			}
		}
		
		Class<?>[] parameterTypes = getBalanceMethod.getParameterTypes();
		
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(accountClass.getName());
		methodCall.setMethodName(getBalanceMethod.getName());
		methodCall.setParameterTypes(parameterTypes);
		
		int returnValue = (Integer) skeleton.invokeMethodOnObject(methodCall ,testAccount);
		assertEquals(balance, returnValue);
	}
}
