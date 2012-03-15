package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;

public abstract class Action implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//TODO replace with object
	private String result;
	
	
	public abstract void execute(ClientSystemUnderTest client);	
	
}
