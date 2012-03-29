package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountImpl;
import ch.hsr.objectCaching.interfaces.serverSystemUnderTest.MethodCall;
import ch.hsr.objectCaching.interfaces.serverSystemUnderTest.ReturnValue;

public class TestAccountSkeleton {

	private AccountSkeleton skeleton;
	private AccountImpl testAccount;
	private MethodCall methodCall;
	private Class<AccountImpl> accountClass = AccountImpl.class;
	private Method getBalanceMethod;
	private Method setBalanceMethod;

	@Before
	public void setUp() throws Exception {
		skeleton = new AccountSkeleton();
		testAccount = new AccountImpl();
		methodCall = new MethodCall();
		initMethods();
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
		
		Class<?>[] parameterTypes = getBalanceMethod.getParameterTypes();
		
		methodCall.setClassName(accountClass.getName());
		methodCall.setMethodName(getBalanceMethod.getName());
		methodCall.setParameterTypes(parameterTypes);
		
		int returnValue = (Integer) skeleton.invokeMethodOnObject(skeleton.getMethod(methodCall) ,testAccount, null);
		assertEquals(balance, returnValue);
		
		methodCall.setMethodName(setBalanceMethod.getName());
		methodCall.setParameterTypes(setBalanceMethod.getParameterTypes());
		int newBalanceValue = 220;
		Object[] args = {newBalanceValue};
		skeleton.invokeMethodOnObject(skeleton.getMethod(methodCall), testAccount, args);
		
		assertEquals(newBalanceValue, testAccount.getBalance());
	}

	private void initMethods() {
		Method[] allMethods = accountClass.getMethods();
		for(Method method:allMethods){
			if(method.getName().equals("getBalance")){
				getBalanceMethod = method;
			}
			if(method.getName().equals("setBalance")){
				setBalanceMethod = method;
			}
		}
	}
	
	@Test
	public void testInvokeMethod(){
		methodCall.setMethodName(getBalanceMethod.getName());
		int objectID = 23;
		methodCall.setObjectID(objectID);
		skeleton.addObject(objectID, testAccount);
		
		ReturnValue returnValue = skeleton.invokeMethod(methodCall);
		
		assertEquals(testAccount.getBalance(), returnValue.getValue());
		assertEquals(int.class, returnValue.getType());
		
		methodCall.setMethodName(setBalanceMethod.getName());
		methodCall.setParameterTypes(setBalanceMethod.getParameterTypes());
		int excpectedValue = 240;
		Object[] args = {excpectedValue};
		methodCall.setArguments(args);
		skeleton.invokeMethod(methodCall);
		
		assertEquals(excpectedValue, testAccount.getBalance());
	}
	
	@Test
	public void testUpdateWriteSet(){
		methodCall.setMethodName(setBalanceMethod.getName());
		int objectID = 23;
		methodCall.setObjectID(objectID);;
		skeleton.addObject(objectID, testAccount);
		skeleton.updateWriteSet(methodCall);
		HashMap<Account, Integer> writeMap = skeleton.getWriteMap();
		int version = writeMap.get(testAccount);
		assertEquals(1, version);
		skeleton.updateWriteSet(methodCall);
		int version2 = writeMap.get(testAccount);
		assertEquals(2, version2);
		methodCall.setMethodName(getBalanceMethod.getName());
		skeleton.updateWriteSet(methodCall);
		assertEquals(2, (int)writeMap.get(testAccount));
	}
}
