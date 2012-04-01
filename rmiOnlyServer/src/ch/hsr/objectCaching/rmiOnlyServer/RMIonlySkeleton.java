package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;

public interface RMIonlySkeleton {

	ReturnValue invokeMethod(MethodCall methodCall);
	
}
