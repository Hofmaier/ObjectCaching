package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.interfaces.MethodCall;
import ch.hsr.objectCaching.interfaces.ReturnValue;

public interface RMIonlySkeleton {

	ReturnValue invokeMethod(MethodCall methodCall);
	
}
