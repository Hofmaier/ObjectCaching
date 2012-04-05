package ch.hsr.objectCaching.reporting;

import java.util.HashMap;

import ch.hsr.objectCaching.action.Action;
import ch.hsr.objectCaching.action.IncrementAction;
import ch.hsr.objectCaching.factories.TestCase;
import ch.hsr.objectCaching.scenario.Scenario;

public class ResultGenerator 
{
	private double result;
	private HashMap<String, Scenario> scenarioPerClient;
	
	public ResultGenerator(HashMap<String, Scenario> scenarioPerClient, double startValue)
	{
		this.scenarioPerClient = scenarioPerClient;
		result = startValue;
		calculateResult();
	}
	
	private void calculateResult()
	{
		for(String ip : scenarioPerClient.keySet())
		{
			for(int i = 0; i < scenarioPerClient.get(ip).getActionList().size(); i++)
			{
				Action action = scenarioPerClient.get(ip).getActionList().get(i);
				if(action instanceof IncrementAction)
				{
					result = result * ((IncrementAction)action).getFactor();
				}
			}
		}
	}
	
	public double getResult()
	{
		return result;
	}
}
