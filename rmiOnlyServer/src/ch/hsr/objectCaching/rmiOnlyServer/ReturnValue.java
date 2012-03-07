package ch.hsr.objectCaching.rmiOnlyServer;

import java.io.Serializable;

public class ReturnValue implements Serializable{
	
	private Object value;
	private int status;
	private String message;
	private String type;
	
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
