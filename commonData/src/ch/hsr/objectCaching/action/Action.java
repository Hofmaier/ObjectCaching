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
	
	public enum ActionTyp {
		READ_ACTION, WRITE_ACTION, INCREMENT_ACTION;
	}
	
	
	public Action(){
		result = new Result();
	}
	
	public Result getResult(){
		return result;
	}
	
	public abstract ActionTyp getActionTyp();
	public abstract int getMinimalNumberOfTimeRecords();
	public abstract void execute(Account account);	
	
}
