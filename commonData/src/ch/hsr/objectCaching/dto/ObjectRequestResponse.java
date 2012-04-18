package ch.hsr.objectCaching.dto;

import java.io.Serializable;

public class ObjectRequestResponse implements Serializable {
	
	private Object requestedObject;

	public Object getRequestedObject() {
		return requestedObject;
	}

	public void setRequestedObject(Object requestedObject) {
		this.requestedObject = requestedObject;
	}
	
	

}
