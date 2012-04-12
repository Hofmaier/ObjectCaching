package ch.hsr.objectCaching.rmiWithCacheClient;

import java.io.IOException;

import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;
import ch.hsr.objectCaching.dto.TransferObject;
import ch.hsr.objectCaching.rmiOnlyClient.IStreamProvider;

public class MessageManager {
	
	private IStreamProvider streamProvider;
	
	public IStreamProvider getStreamProvider() {
		return streamProvider;
	}

	public void setStreamProvider(IStreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}

	public void sendMessageCall(TransferObject methodCall) {
		try {
			streamProvider.getObjectOutputStream().writeObject(methodCall);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ReturnValue receiveMethodCallResponse() throws IOException, ClassNotFoundException 
	{
		return (ReturnValue) streamProvider.getObjectInputStream().readObject();
	}

}
