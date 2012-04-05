package ch.hsr.objectCaching.testFrameworkServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.scenario.Scenario;

public class TestCaseFactoryTest 
{
	TestCaseFactory factory;
	ResultGenerator generator;
	@Before
	public void setUp()
	{
		factory = new TestCaseFactory("testCases.xml");
		factory.convertXML();
		generator = new ResultGenerator(factory.getTestCases().get(0), factory.getAccounts().get(0).getBalance());
	}
	
	@Test
	public void assertTestCasesNotNull()
	{
		assertNotNull(factory.getTestCases());
	}
	
	@Test
	public void assertScenariosNotNull()
	{
		assertNotNull(factory.getTestCases().get(0).getScenarios());
	}
	
	@Test
	public void assertExactResult()
	{
		assertEquals(10000000, generator.getResult());
	}
	
	@Test
	public void getSameById()
	{
		Scenario temp = factory.getTestCases().get(0).getScenarios().get(0);
		int id = temp.getId();
		assertEquals(temp, factory.getTestCases().get(0).getScenario(id));
	}
}
