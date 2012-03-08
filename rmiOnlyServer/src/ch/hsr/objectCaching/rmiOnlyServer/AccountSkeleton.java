package ch.hsr.objectCaching.rmiOnlyServer;
import java.util.HashMap;


public class AccountSkeleton implements RMIonlySkeleton {
	
	private HashMap<Integer, Account> objectMap = new HashMap<Integer, Account>();
	
	
	public ReturnValue invokeMethod(MethodCall methodCall) {
		
		
		Account accountObject = objectMap.get(methodCall.getObjectID());
		
		if(methodCall.getMethodName().equalsIgnoreCase("getBalance")){
			int b = accountObject.getBalance();
			
		} 
		return null;
				
	}
	
	Account getCalledObject(MethodCall methodCall){
		return objectMap.get(methodCall.getObjectID());
	}
	
	void addObject(Integer objectID, Account account){
		objectMap.put(objectID, account);
	}

}
