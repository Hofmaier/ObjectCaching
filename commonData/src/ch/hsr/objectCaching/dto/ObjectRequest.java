package ch.hsr.objectCaching.dto;

import java.io.Serializable;

public class ObjectRequest implements TransferObject, Serializable {
	private int objectID;

	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}
}
