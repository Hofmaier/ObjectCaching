package ch.hsr.objectCaching.rmiOnlyServer;

import java.io.Serializable;

public class ReturnValue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2163526171214692070L;
	private Object value;
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
