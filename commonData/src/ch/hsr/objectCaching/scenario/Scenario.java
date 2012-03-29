package ch.hsr.objectCaching.scenario;

import java.io.Serializable;
import java.util.ArrayList;

import ch.hsr.objectCaching.action.Action;
import ch.hsr.objectCaching.action.IncrementAction;
import ch.hsr.objectCaching.action.ReadAction;
import ch.hsr.objectCaching.action.WriteAction;


public class Scenario implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Action> actionList;
	private int scenarioId;
	
	public Scenario(int scenarioId)
	{
		actionList = new ArrayList<Action>();
		this.scenarioId = scenarioId;
	}
	
	public ArrayList<Action> getActionList()
	{
		return actionList;
	}
	
	public int getId()
	{
		return scenarioId;
	}
	public void setWriteAction(int count, int value)
	{
		WriteAction temp;
		for(int i = 0; i < count; i++)
		{
			temp = new WriteAction(value);
			actionList.add(temp);
		}
	}
	
	public void setReadAction(int count)
	{
		ReadAction temp;
		for(int i = 0; i < count; i++)
		{
			temp = new ReadAction();
			actionList.add(temp);
		}
	}
	public void setIncrementAction(int count, long delay, float factor)
	{
		IncrementAction temp;
		for(int i = 0; i < count; i++)
		{
			temp = new IncrementAction(delay, factor);
			actionList.add(temp);
		}
	}

}
