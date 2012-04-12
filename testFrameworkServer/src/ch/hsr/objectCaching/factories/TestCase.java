package ch.hsr.objectCaching.factories;

import java.io.Serializable;
import java.util.ArrayList;

import ch.hsr.objectCaching.scenario.Scenario;

public class TestCase implements Serializable
{
	
	private static final long serialVersionUID = 1;
	private String clientSystemUnderTest;
	private String serverSystemUnderTest;
	private ArrayList<Scenario> scenarios;
	
	public TestCase(String clientSystemUnderTest, String serverSystemUnderTest)
	{
		scenarios = new ArrayList<Scenario>();
		this.clientSystemUnderTest = clientSystemUnderTest;
		this.serverSystemUnderTest = serverSystemUnderTest;
	}
	
	public Scenario generateNewScenario(int id)
	{
		Scenario temp = new Scenario(id);
		scenarios.add(temp);
		return temp;
	}
	
	public ArrayList<Scenario> getScenarios()
	{
		return scenarios;
	}
	
	public Scenario getScenario(int id)
	{
		for(int i = 0; i < scenarios.size(); i++)
		{
			if(scenarios.get(i).getId() == id)
			{
				return scenarios.get(i);
			}
		}
		return null;
	}
	
	public int getScenariosCount()
	{
		return scenarios.size();
	}

	public String getClientSystemUnderTest() {
		return clientSystemUnderTest;
	}

	public void setClientSystemUnderTest(String clientSystemUnderTest) {
		this.clientSystemUnderTest = clientSystemUnderTest;
	}
	
	public String getServerSystemUnderTest() {
		return serverSystemUnderTest;
	}

	public void setServerSystemUnderTest(String serverSystemUnderTest) {
		this.serverSystemUnderTest = serverSystemUnderTest;
	}
}
