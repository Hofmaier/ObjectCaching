package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.interfaces.serverSystemUnderTest.MethodCall;
import ch.hsr.objectCaching.interfaces.serverSystemUnderTest.ReturnValue;

public interface RMIonlySkeleton {

	ReturnValue invokeMethod(MethodCall methodCall);
	
}
