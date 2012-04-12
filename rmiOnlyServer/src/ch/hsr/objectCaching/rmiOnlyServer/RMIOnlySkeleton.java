package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;

public interface RMIOnlySkeleton {

	ReturnValue invokeMethod(MethodCall methodCall);
	
}
