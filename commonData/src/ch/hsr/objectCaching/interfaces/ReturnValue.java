package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;

public class ReturnValue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2163526171214692070L;
	private Object value;
	private Class<?> type;
	
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
}
