package ch.hsr.objectCaching.dto;

import java.io.Serializable;

public class ObjectUpdate implements Serializable {
	private Object object;
	private int objectID;
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public int getObjectID() {
		return objectID;
	}
	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}
}
