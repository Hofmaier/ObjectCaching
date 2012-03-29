package ch.hsr.objectCaching.rmiOnlyServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountImpl;
import ch.hsr.objectCaching.interfaces.serverSystemUnderTest.MethodCall;
import ch.hsr.objectCaching.interfaces.serverSystemUnderTest.ReturnValue;

public class AccountSkeleton implements RMIonlySkeleton {
	
	private HashMap<Integer, Account> objectMap = new HashMap<Integer, Account>();
	private HashMap<Account, Integer> writeMap = new HashMap<Account, Integer>();
	private HashMap<String, Integer> readSetMap = new HashMap<String, Integer>();
	
	HashMap<Account, Integer> getWriteMap() {
		return writeMap;
	}

	public ReturnValue invokeMethod(MethodCall methodCall) {
		
		Account accountObject = objectMap.get(methodCall.getObjectID());
		
		try {
			Method method = getMethod(methodCall);
			Class<?> returnType = method.getReturnType();
			updateReadSet(methodCall);
			
			Object retVal = invokeMethodOnObject(method, accountObject, methodCall.getArguments());
			updateWriteSet(methodCall);
			ReturnValue returnValue = composeReturnValue(retVal, returnType);
			return returnValue;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	void updateWriteSet(MethodCall method) {
		if(method.getMethodName().equals("setBalance")){
			Account account = objectMap.get(method.getObjectID());
			Integer version = writeMap.get(account);
			version++;
			writeMap.put(account, version);
		}
	}

	Method getMethod(MethodCall methodCall) {
		Class<AccountImpl> clazz = AccountImpl.class;
		try {
			return clazz.getDeclaredMethod(methodCall.getMethodName(), methodCall.getParameterTypes());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ReturnValue composeReturnValue(Object retVal, Class<?> returnType) {
		ReturnValue returnValue = new ReturnValue();
		returnValue.setValue(retVal);
		returnValue.setType(returnType);
		return returnValue;
	}

	Object invokeMethodOnObject(Method method,
			Account accountObject, Object[] args) {
		try {
			return method.invoke(accountObject, args);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	Account getCalledObject(MethodCall methodCall){
		return objectMap.get(methodCall.getObjectID());
	}
	
	void addObject(Integer objectID, Account account){
		objectMap.put(objectID, account);
		writeMap.put(account, 0);
	}
	
	public List<Integer> getAllObjectIDs(){
		List<Integer> retVal = new ArrayList<Integer>();
		for(Integer i:objectMap.keySet()){
			retVal.add(i);
		}
		return retVal;
	}

	void updateReadSet(MethodCall methodCall) {
		if(methodCall.getMethodName().equals("getBalance")){
			String readSetKey = methodCall.getClientIp().concat(String.valueOf(methodCall.getObjectID()));
			Integer version = writeMap.get(objectMap.get(methodCall.getObjectID()));
			readSetMap.put(readSetKey, version);
		}
	}

	public HashMap<String, Integer> getReadSetMap() {
		return readSetMap;
	}
}
