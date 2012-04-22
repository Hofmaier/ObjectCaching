package ch.hsr.objectCaching.rmiWithCacheServer;

import java.lang.reflect.Method;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.RMIException;
import ch.hsr.objectCaching.dto.ReturnValue;
import ch.hsr.objectCaching.util.ConcurrencyControl;

public class AccountSkeleton extends
		ch.hsr.objectCaching.rmiOnlyServer.AccountSkeleton {
	
	private ObjectManager objectManager;
	private ConcurrencyControl concorrencyControl;
	
	public ReturnValue invokeMethod(MethodCall methodCall) {
		
		Account accountObject = objectMap.get(methodCall.getObjectID());
		
		try {
			Method method = getMethod(methodCall);
			Class<?> returnType = method.getReturnType();
			updateReadSet(methodCall);
			if(!isWriteConsistent(methodCall)){
				ReturnValue returnValue = new ReturnValue();
				returnValue.setException(new RMIException());
				return returnValue;
			}
			Object retVal = invokeMethodOnObject(method, accountObject, methodCall.getArguments());
			updateWriteSet(methodCall);
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
