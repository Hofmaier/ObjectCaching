package ch.hsr.objectCaching.rmiOnlyServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class AccountSkeleton implements RMIonlySkeleton {
	
	private HashMap<Integer, AccountImpl> objectMap = new HashMap<Integer, AccountImpl>();
	
	public ReturnValue invokeMethod(MethodCall methodCall) {
		
		AccountImpl accountObject = objectMap.get(methodCall.getObjectID());
		
		try {
			Method method = getMethod(methodCall);
			Class<?> returnType = method.getReturnType();
			Object retVal = invokeMethodOnObject(method, accountObject);
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
			AccountImpl accountObject) throws ClassNotFoundException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		
		return method.invoke(accountObject, (Object[])null);
	}
	
	AccountImpl getCalledObject(MethodCall methodCall){
		return objectMap.get(methodCall.getObjectID());
	}
	
	void addObject(Integer objectID, AccountImpl account){
		objectMap.put(objectID, account);
	}
}
