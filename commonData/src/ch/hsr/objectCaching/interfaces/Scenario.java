package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;
import java.util.ArrayList;


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

}
