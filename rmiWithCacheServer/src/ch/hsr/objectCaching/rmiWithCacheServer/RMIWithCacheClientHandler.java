package ch.hsr.objectCaching.rmiWithCacheServer;

import java.io.IOException;

import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ObjectRequest;
import ch.hsr.objectCaching.dto.ObjectRequestResponse;
import ch.hsr.objectCaching.dto.TransferObject;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyClientHandler;

public class RMIWithCacheClientHandler extends RMIOnlyClientHandler{
	
	private ObjectManager objectManager;

	@Override
	public void run() {
			try {
				Object objectFromStream = null;
				while ((objectFromStream = objectInputStream.readObject()) != null) {
					TransferObject transferObject = (TransferObject) objectFromStream;
					
					processTransferObject(transferObject);
					
				}
				objectOutputStream.write(null);
				objectInputStream.close();
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	private void processTransferObject(Object objectFromStream) {
		if(objectFromStream instanceof ObjectRequest){
			ObjectRequest objectRequest = (ObjectRequest) objectFromStream;
			objectRequest.setClientHandler(this);
			ObjectRequestResponse response = objectManager.processObjectRequest(objectRequest);
			send(response);
		}
		if(objectFromStream instanceof MethodCall){
			MethodCall methodCall = (MethodCall) objectFromStream;
			methodCall.setClientIp(clientIpAdress);
			processMethodCall(methodCall);
		}
	}


	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public void setObjectManager(ObjectManager objectManager) {
		this.objectManager = objectManager;
	}
	
}
