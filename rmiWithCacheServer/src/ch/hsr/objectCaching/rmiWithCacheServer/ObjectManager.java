package ch.hsr.objectCaching.rmiWithCacheServer;

import java.util.ArrayList;
import java.util.HashMap;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.dto.ObjectRequest;
import ch.hsr.objectCaching.dto.ObjectRequestResponse;
import ch.hsr.objectCaching.dto.Update;
import ch.hsr.objectCaching.interfaces.ClientHandler;

public class ObjectManager {

	private HashMap<Integer, Object> objectMap = new HashMap<Integer, Object>();
	private HashMap<Integer, ArrayList<ClientHandler>> clientListMap = new HashMap<Integer, ArrayList<ClientHandler>>();

	public ObjectRequestResponse processObjectRequest(ObjectRequest objectRequest) {
		Integer objectID = objectRequest.getObjectID();
		registerClientHandler(objectRequest, objectID);
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
		}
		
		clients.add(objectRequest.getClientHandler());
	}

	public void addAccount(Integer objectID, Account account) {
		objectMap.put(objectID, account);
	}

	public void updateClients(int objectID) {
		ArrayList<ClientHandler> clients = clientListMap.get(objectID);
		for(ClientHandler client:clients){
			Update update = new Update();
			update.setObject(objectMap.get(objectID));
			update.setObjectID(objectID);
			client.send(update);
		}
	}

}
