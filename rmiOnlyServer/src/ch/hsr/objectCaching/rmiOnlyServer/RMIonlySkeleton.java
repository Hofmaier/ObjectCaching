package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.interfaces.MethodCall;

public interface RMIonlySkeleton {

	ReturnValue invokeMethod(MethodCall methodCall);
	
}
