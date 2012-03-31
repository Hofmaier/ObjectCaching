package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
	private int objectID = 23;
	private String clientIpAdressClient1 = "152.96.56.38";
	private String clientIpAddressClient2 = "152.96.56.39";
	private MethodCall getBalanceCall = new MethodCall();
	private MethodCall setBalanceCall = new MethodCall();

	@Before
	public void setUp() throws Exception {
		skeleton = new AccountSkeleton();
		testAccount = new AccountImpl();
		methodCall = new MethodCall();
		initMethods();
		getBalanceCall.setMethodName(getBalanceMethod.getName());
		getBalanceCall.setObjectID(objectID);
		getBalanceCall.setClientIp(clientIpAdressClient1);
		Object[] args = {};
		getBalanceCall.setArguments(args);
		
		setBalanceCall.setMethodName(setBalanceMethod.getName());
		setBalanceCall.setParameterTypes(setBalanceMethod.getParameterTypes());
		setBalanceCall.setObjectID(objectID);
		Object[] setBalanceArgs = {240};
		setBalanceCall.setArguments(setBalanceArgs);
		
		skeleton.addObject(objectID, testAccount);
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
		methodCall = getBalanceCall;
		
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
		methodCall.setObjectID(objectID);;
		skeleton.addObject(objectID, testAccount);
		skeleton.updateWriteSet(methodCall);
		HashMap<Account, Integer> writeMap = skeleton.getWriteMap();
		assertEquals(1, (int)writeMap.get(testAccount));
		skeleton.updateWriteSet(methodCall);
		assertEquals(2, (int)writeMap.get(testAccount));
		methodCall.setMethodName(getBalanceMethod.getName());
		skeleton.updateWriteSet(methodCall);
		assertEquals(2, (int)writeMap.get(testAccount));
	}
	
	@Test
	public void testUpdateReadSet(){ 
		HashMap<Account, Integer> writeMap = skeleton.getWriteMap();
		HashMap<String, Integer> readSetMap = skeleton.getReadSetMap();
		methodCall.setClientIp(clientIpAdressClient1);
		methodCall.setMethodName(getBalanceMethod.getName());
		methodCall.setObjectID(objectID);
		skeleton.addObject(objectID, testAccount);
		skeleton.updateReadSet(methodCall); 
		String readMapKey = clientIpAdressClient1.concat(String.valueOf(objectID));
		assertEquals(writeMap.get(testAccount), readSetMap.get(readMapKey));
		
		methodCall.setMethodName(setBalanceMethod.getName());
		skeleton.updateWriteSet(methodCall);
		methodCall.setMethodName(getBalanceMethod.getName());
		skeleton.updateReadSet(methodCall);
		assertEquals(writeMap.get(testAccount), readSetMap.get(readMapKey));
	}
	
	@Test
	public void testIsWriteConsistent(){
		getBalanceCall.setClientIp(clientIpAdressClient1);
		skeleton.invokeMethod(getBalanceCall);
		getBalanceCall.setClientIp(clientIpAddressClient2);
		skeleton.invokeMethod(getBalanceCall);
		setBalanceCall.setClientIp(clientIpAddressClient2);
		skeleton.invokeMethod(setBalanceCall);
		setBalanceCall.setClientIp(clientIpAdressClient1);
		ReturnValue returnValue = skeleton.invokeMethod(setBalanceCall);
		assertNotNull(returnValue.getException());
	}
	
	@Test
	public void testIsWriteConsistentWithCorrectOrder(){
		getBalanceCall.setClientIp(clientIpAdressClient1);
		setBalanceCall.setClientIp(clientIpAdressClient1);
		skeleton.invokeMethod(getBalanceCall);
		skeleton.invokeMethod(setBalanceCall);
		getBalanceCall.setClientIp(clientIpAddressClient2);
		setBalanceCall.setClientIp(clientIpAddressClient2);
		skeleton.invokeMethod(getBalanceCall);
		
		int expectedBalance = 266;
		Object[] setBalanceArgs = {expectedBalance};
		setBalanceCall.setArguments(setBalanceArgs);
		ReturnValue returnValue = skeleton.invokeMethod(setBalanceCall);
		assertEquals(expectedBalance, testAccount.getBalance());
	}
}
