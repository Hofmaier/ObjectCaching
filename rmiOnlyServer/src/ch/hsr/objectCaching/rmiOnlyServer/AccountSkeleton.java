package ch.hsr.objectCaching.rmiOnlyServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountImpl;
import ch.hsr.objectCaching.interfaces.MethodCall;
import ch.hsr.objectCaching.interfaces.ReturnValue;

public class AccountSkeleton implements RMIonlySkeleton {
	
	private HashMap<Integer, Account> objectMap = new HashMap<Integer, Account>();
	
	public ReturnValue invokeMethod(MethodCall methodCall) {
		
		Account accountObject = objectMap.get(methodCall.getObjectID());
		
		try {
			Method method = getMethod(methodCall);
			Class<?> returnType = method.getReturnType();
			Object retVal = invokeMethodOnObject(method, accountObject, methodCall.getArguments());
			ReturnValue returnValue = composeReturnValue(retVal, returnType);
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

	Method getMethod(MethodCall methodCall) throws SecurityException, NoSuchMethodException {
		Class<AccountImpl> clazz = AccountImpl.class;
		return clazz.getDeclaredMethod(methodCall.getMethodName(), methodCall.getParameterTypes());
	}

	private ReturnValue composeReturnValue(Object retVal, Class<?> returnType) {
		ReturnValue returnValue = new ReturnValue();
		returnValue.setValue(retVal);
		returnValue.setType(returnType);
		return returnValue;
	}

	Object invokeMethodOnObject(Method method,
			Account accountObject, Object[] args) throws ClassNotFoundException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		
		return method.invoke(accountObject, args);
	}
	
	Account getCalledObject(MethodCall methodCall){
		return objectMap.get(methodCall.getObjectID());
	}
	
	void addObject(Integer objectID, Account account){
		objectMap.put(objectID, account);
	}
	
	public List<Integer> getAllObjectIDs(){
		List<Integer> retVal = new ArrayList<Integer>();
		for(Integer i:objectMap.keySet()){
			retVal.add(i);
		}
		return retVal;
	}
}
