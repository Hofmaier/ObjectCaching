package ch.hsr.objectCaching.rmiWithCacheClient;

import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;
import ch.hsr.objectCaching.dto.TransferObject;

public class MessageManagerFake extends MessageManager {
	TransferObject transferObject;
	ReturnValue returnValue;
	
	@Override
	public void sendMessageCall(TransferObject transferObj) {
		this.transferObject = transferObj;
	}

	@Override
	public ReturnValue receiveObject() {
		return returnValue;
	}
}
