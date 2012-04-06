package ch.hsr.objectCaching.action;

import java.io.Serializable;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.action.result.Result;

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
	
	public abstract ActionTyp getActionTyp();
	public abstract void execute(Account account);	
	
}
