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
			ObjectRequestResponse response = objectManager.processObjectRequest(objectRequest);
			sendResponse(response);
		}
		if(objectFromStream instanceof MethodCall){
			MethodCall methodCall = (MethodCall) objectFromStream;
			processMethodCall(methodCall);
		}
	}

	private void sendResponse(ObjectRequestResponse response) {
			try {
				objectOutputStream.writeObject(response);
				objectOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public void setObjectManager(ObjectManager objectManager) {
		this.objectManager = objectManager;
	}
}
