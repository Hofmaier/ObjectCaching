package ch.hsr.objectCaching.dto;

import java.io.Serializable;

public class ObjectRequestResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Object requestedObject;
	private int objectVersion;

	public Object getRequestedObject() {
		return requestedObject;
	}

	public void setRequestedObject(Object requestedObject) {
		this.requestedObject = requestedObject;
	}

	public int getObjectVersion() {
		return objectVersion;
	}

	public void setObjectVersion(int objectVersion) {
		this.objectVersion = objectVersion;
	}
	
	

}
