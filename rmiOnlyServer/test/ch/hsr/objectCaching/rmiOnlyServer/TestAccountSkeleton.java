package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;

public class TestAccountSkeleton {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetCalledObject() {
		AccountSkeleton skeleton = new AccountSkeleton();
		Account testAccount = new Account();
		skeleton.addObject(23, testAccount);
		MethodCall methodCall = new MethodCall();
		methodCall.setObjectID(23);
		Account account = skeleton.getCalledObject(methodCall);
		assertTrue(account == testAccount);
	}
	
	@Test
	public void testInvokeMethodOnObject() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		AccountSkeleton skeleton = new AccountSkeleton();
		Account testAccount = new Account();
		int balance = 200;
		testAccount.setBalance(balance);
		
		MethodCall methodCall = new MethodCall();
		Class accountClass = Account.class;
		
		Method getBalanceMethod = null;
		Method[] allMethods = accountClass.getMethods();
		for(Method method:allMethods){
			if(method.getName().equals("getBalance")){
				getBalanceMethod = method;
				break;
			}
		}
		
		Class[] parameterTypes = getBalanceMethod.getParameterTypes();
		methodCall.setClassName(accountClass.getName());
		methodCall.setMethodName(getBalanceMethod.getName());
		methodCall.setParameterTypes(parameterTypes);
		Object returnValue = skeleton.invokeMethodOnObject(methodCall ,testAccount);
		Integer castedRetVal = (Integer) returnValue;
		assertEquals(balance, returnValue);
	}
}
