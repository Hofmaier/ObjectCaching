package ch.hsr.objectCaching.interfaces.serverSystemUnderTest;

import java.io.Serializable;

public class ReturnValue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2163526171214692070L;
	private Object value;
	private Class<?> type;
	private RMIException exception;
	
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
	
	public void setException(RMIException exception) {
		this.exception = exception;
	}
	
	public RMIException getException() {
		return exception;
	}
}
