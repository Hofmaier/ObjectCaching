package ch.hsr.objectCaching.testFrameworkServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.factories.TestCaseFactory;
import ch.hsr.objectCaching.reporting.ResultGenerator;
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
	}
	
	@Test
	public void assertTestCasesNotNull()
	{
		assertNotNull(factory.getTestCase());
	}
	
	@Test
	public void assertScenariosNotNull()
	{
		assertNotNull(factory.getTestCase().getScenarios());
	}
	
	@Test
	public void getSameById()
	{
		Scenario temp = factory.getTestCase().getScenarios().get(0);
		int id = temp.getId();
		assertEquals(temp, factory.getTestCase().getScenario(id));
	}
}
