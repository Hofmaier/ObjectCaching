package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;

public class Action implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String action;
	private int value;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
	
	
}
