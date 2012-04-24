package ch.hsr.objectCaching.rmiWithCacheServer;

import java.util.ArrayList;
import java.util.HashMap;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.dto.ObjectRequest;
import ch.hsr.objectCaching.dto.ObjectRequestResponse;
import ch.hsr.objectCaching.dto.ObjectUpdate;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.util.ConcurrencyControl;

public class ObjectManager {

	private HashMap<Integer, Object> objectMap = new HashMap<Integer, Object>();
	private HashMap<Integer, ArrayList<ClientHandler>> clientListMap = new HashMap<Integer, ArrayList<ClientHandler>>();
	private ConcurrencyControl concurrencyControl = new ConcurrencyControl();

	public ObjectRequestResponse processObjectRequest(ObjectRequest objectRequest) {
		Integer objectID = objectRequest.getObjectID();
		registerClientHandler(objectRequest, objectID);
		concurrencyControl.updateReadVersionOfClient(objectRequest.getClientHandler().getClientIpAddress(), objectRequest.getObjectID());
		ObjectRequestResponse response = composeResponse(objectID);
		return response;
	}

	private ObjectRequestResponse composeResponse(Integer objectID) {
		Object requestedObject = objectMap.get(objectID);
		ObjectRequestResponse response = new ObjectRequestResponse();
		response.setRequestedObject(requestedObject);
		return response;
	}

	private void registerClientHandler(ObjectRequest objectRequest,
			Integer objectID) {
		ArrayList<ClientHandler> clients = clientListMap.get(objectID);
		
		if(clients == null){
			clients = new ArrayList<ClientHandler>();
			clientListMap.put(objectID, clients);
		}
		
		clients.add(objectRequest.getClientHandler());
	}

	public void addAccount(Integer objectID, Account account) {
		objectMap.put(objectID, account);
	}

	public void updateClients(int objectID) {
		ArrayList<ClientHandler> clients = clientListMap.get(objectID);
		concurrencyControl.updateWriteVersion(objectID);
		for(ClientHandler client:clients){
			ObjectUpdate update = new ObjectUpdate();
			update.setObject(objectMap.get(objectID));
			update.setObjectID(objectID);
			client.send(update);
		}
	}
	
	public boolean isWriteConsistent(Integer objectID, String clientIP){
		return concurrencyControl.isWriteConsistent(objectID, clientIP);
	}

}
