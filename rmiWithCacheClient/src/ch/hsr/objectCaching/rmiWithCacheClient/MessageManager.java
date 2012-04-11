package ch.hsr.objectCaching.rmiWithCacheClient;

import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;
import ch.hsr.objectCaching.rmiOnlyClient.IStreamProvider;

public class MessageManager {
	
	private IStreamProvider streamProvider;

	public IStreamProvider getStreamProvider() {
		return streamProvider;
	}

	public void setStreamProvider(IStreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}

	public void sendMessageCall(MethodCall methodCall) {
		
	}

	public ReturnValue receiveMethodCallResponse() {
		return null;
	}

}
