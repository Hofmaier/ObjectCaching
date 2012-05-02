package ch.hsr.objectCaching.rmiWithCacheClient;

import ch.hsr.objectCaching.dto.ObjectRequestResponse;
import ch.hsr.objectCaching.dto.ReturnValue;
import ch.hsr.objectCaching.dto.TransferObject;

public class MessageManagerFake extends MessageManager {
	TransferObject transferObject;
	ReturnValue returnValue;
	Object requestedObject;
	int sendMessageCount = 0;
	
	@Override
	public void sendMessageCall(TransferObject transferObj) {
		this.transferObject = transferObj;
		sendMessageCount++;
	}
	
	
	

	@Override
	public ObjectRequestResponse receiveObject() {
		return null;
	}

	@Override
	public ReturnValue receiveReturnValue() {
		return returnValue;
	}
	
	
}
