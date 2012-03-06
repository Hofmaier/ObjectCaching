package ch.hsr.objectCaching.rmiOnlyServer;
import java.util.HashMap;


public class AccountSkeleton implements RMIonlySkeleton {
	
	private HashMap<Integer, AccountImpl> objectMap;

	public ReturnValue invokeMethod(MethodCall methodCall) {
		AccountImpl accountObject = objectMap.get(methodCall.getObjectID());
		
		// hier muss Logik für implementiert werden um die richtige Methode auszuwählen
		if(methodCall.getMethodName().equalsIgnoreCase("getBalance")){
			int b = accountObject.getBalance();
			
		} 
		return null;
				
	}
	
	
	

}
