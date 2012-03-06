package ch.hsr.objectCaching.rmiOnlyServer;

public interface RMIonlySkeleton {

	ReturnValue invokeMethod(MethodCall methodCall);
	
}
