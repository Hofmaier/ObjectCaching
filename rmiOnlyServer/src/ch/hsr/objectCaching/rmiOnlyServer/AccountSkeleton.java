package ch.hsr.objectCaching.rmiOnlyServer;
import java.util.HashMap;


public class AccountSkeleton {
	
	private HashMap<Integer, AccountImpl> objectMap;

	public String invokeMethod(MethodCall methodCall) {
		AccountImpl accountObject = objectMap.get(methodCall.getObjectID());
		
		// hier muss Logik f�r implementiert werden um die richtige Methode auszuw�hlen
		if(methodCall.getMethodName().equalsIgnoreCase("getBalance")){
			int b = accountObject.getBalance();
			return String.valueOf(b);
		} 
		return null;
				
	}
	
	
	

}
