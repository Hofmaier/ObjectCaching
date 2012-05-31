package ch.hsr.objectCaching.dto;

import java.io.Serializable;

import ch.hsr.objectCaching.interfaces.ClientHandler;

public class ObjectRequest implements TransferObject, Serializable {
	
	private static final long serialVersionUID = -4753239346104136442L;
	private int objectID;
	private ClientHandler clientHandler;
	
	public ClientHandler getClientHandler() {
		return clientHandler;
	}

	public void setClientHandler(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
	}

	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}
}
