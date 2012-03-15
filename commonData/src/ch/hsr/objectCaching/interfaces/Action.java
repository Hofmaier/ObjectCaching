package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;

public abstract class Action implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Result result;
	
	public Action(){
		result = new Result();
	}
	
	public Result getResult(){
		return result;
	}
	
	
	public abstract void execute(Account account);	
	
}
