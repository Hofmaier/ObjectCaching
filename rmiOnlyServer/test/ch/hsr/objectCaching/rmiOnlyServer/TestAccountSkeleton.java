package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.MethodCall;
import ch.hsr.objectCaching.interfaces.ReturnValue;

public class TestAccountSkeleton {

	private AccountSkeleton skeleton;
	private AccountImpl testAccount;
	private MethodCall methodCall;
	private Class<AccountImpl> accountClass = AccountImpl.class;

	@Before
	public void setUp() throws Exception {
		skeleton = new AccountSkeleton();
		testAccount = new AccountImpl();
		methodCall = new MethodCall();
	}

	@Test
	public void testGetCalledObject() {
		int objectID = 23;
		skeleton.addObject(objectID, testAccount);
		
		methodCall.setObjectID(objectID);
		Account account = skeleton.getCalledObject(methodCall);
		assertTrue(account == testAccount);
	}
	
	@Test
	public void testInvokeMethodOnObject() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		int balance = 200;
		testAccount.setBalance(balance);
		
		accountClass = AccountImpl.class;
		
		Method getBalanceMethod = getGetBalalanceMethod();
		
		Class<?>[] parameterTypes = getBalanceMethod.getParameterTypes();
		
		methodCall.setClassName(accountClass.getName());
		methodCall.setMethodName(getBalanceMethod.getName());
		methodCall.setParameterTypes(parameterTypes);
		
		int returnValue = (Integer) skeleton.invokeMethodOnObject(skeleton.getMethod(methodCall) ,testAccount);
		assertEquals(balance, returnValue);
	}

	private Method getGetBalalanceMethod() {
		Method getBalanceMethod = null;
		Method[] allMethods = accountClass.getMethods();
		for(Method method:allMethods){
			if(method.getName().equals("getBalance")){
				getBalanceMethod = method;
				break;
			}
		}
		return getBalanceMethod;
	}
	
	@Test
	public void testInvokeMethod(){
		methodCall.setMethodName(getGetBalalanceMethod().getName());
		int objectID = 23;
		methodCall.setObjectID(objectID);
		skeleton.addObject(objectID, testAccount);
		
		ReturnValue returnValue = skeleton.invokeMethod(methodCall);
		
		assertEquals(testAccount.getBalance(), returnValue.getValue());
		assertEquals(int.class, returnValue.getType());
		
	}
}
