package ch.hsr.objectCaching.rmiWithCacheClient;

import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;

public class MessageManagerFake extends MessageManager {
	MethodCall methodCall;
	ReturnValue returnValue;
	
	@Override
	public void sendMessageCall(MethodCall methodCall) {
		this.methodCall = methodCall;
	}

	@Override
	public ReturnValue receiveMethodCallResponse() {
		return returnValue;
	}
}
