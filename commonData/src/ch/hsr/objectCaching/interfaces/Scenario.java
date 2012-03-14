package ch.hsr.objectCaching.interfaces;

import java.util.ArrayList;


public class Scenario 
{
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
	
	public void setWriteAction(int count, int value)
	{
		Action temp;
		for(int i = 0; i < count; i++)
		{
			temp = new Action();
			temp.setAction("setBalance");
			temp.setValue(value);
			actionList.add(temp);
		}
	}
	
	public void setReadAction(int count)
	{
		Action temp;
		for(int i = 0; i < count; i++)
		{
			temp = new Action();
			temp.setAction("getBalance");
			actionList.add(temp);
		}
	}

}
