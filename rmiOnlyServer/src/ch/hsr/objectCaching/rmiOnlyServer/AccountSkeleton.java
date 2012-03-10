package ch.hsr.objectCaching.rmiOnlyServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class AccountSkeleton implements RMIonlySkeleton {
	
	private HashMap<Integer, Account> objectMap = new HashMap<Integer, Account>();
	
	public ReturnValue invokeMethod(MethodCall methodCall) {
		
		Account accountObject = objectMap.get(methodCall.getObjectID());
		
		try {
			Object retVal = invokeMethodOnObject(methodCall, accountObject);
			ReturnValue returnValue = composeReturnValue(retVal);
			return returnValue;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ReturnValue composeReturnValue(Object retVal) {
		ReturnValue returnValue = new ReturnValue();
		returnValue.setValue(retVal);
		return returnValue;
	}

	Object invokeMethodOnObject(MethodCall methodCall,
			Account accountObject) throws ClassNotFoundException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		
		Class<Account> clazz = Account.class;
		Method method = clazz.getDeclaredMethod(methodCall.getMethodName(), methodCall.getParameterTypes());
		return method.invoke(accountObject, (Object[])null);
	}
	
	Account getCalledObject(MethodCall methodCall){
		return objectMap.get(methodCall.getObjectID());
	}
	
	void addObject(Integer objectID, Account account){
		objectMap.put(objectID, account);
	}
}
