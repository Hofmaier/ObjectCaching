package ch.hsr.objectCaching.testFrameworkServer;

import java.io.Serializable;
import java.util.ArrayList;

import ch.hsr.objectCaching.interfaces.Scenario;

public class TestCase implements Serializable
{
	
	private static final long serialVersionUID = 1;
	private String systemUnderTest;
	private ArrayList<Scenario> scenarios;
	
	public TestCase(String systemUnderTest)
	{
		scenarios = new ArrayList<Scenario>();
		this.systemUnderTest = systemUnderTest;
	}
	
	public Scenario generateNewScenario(int id)
	{
		Scenario temp = new Scenario(id);
		scenarios.add(temp);
		return temp;
	}
	
	public int getScenariosCount()
	{
		return scenarios.size();
	}

	public String getSystemUnderTest() {
		return systemUnderTest;
	}

	public void setSystemUnderTest(String systemUnderTest) {
		this.systemUnderTest = systemUnderTest;
	}
}
