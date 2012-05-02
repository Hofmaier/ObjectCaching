package ch.hsr.objectCaching.rmiWithCacheServer;

import java.lang.reflect.Method;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.RMIException;
import ch.hsr.objectCaching.dto.ReturnValue;

public class AccountSkeleton extends
		ch.hsr.objectCaching.rmiOnlyServer.AccountSkeleton {
	
	private ObjectManager objectManager;
	
	public synchronized ReturnValue invokeMethod(MethodCall methodCall) {
		
		Account accountObject = objectMap.get(methodCall.getObjectID());
		
		try {
			Method method = getMethod(methodCall);
			Class<?> returnType = method.getReturnType();
			if(!objectManager.isWriteConsistent(methodCall)){
				ReturnValue returnValue = new ReturnValue();
				returnValue.setException(new RMIException());
				return returnValue;
			}
			Object retVal = invokeMethodOnObject(method, accountObject, methodCall.getArguments());
			objectManager.updateClients(methodCall.getObjectID());
			ReturnValue returnValue = composeReturnValue(retVal, returnType);
			return returnValue;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public void setObjectManager(ObjectManager objectManager) {
		this.objectManager = objectManager;
	}
}
