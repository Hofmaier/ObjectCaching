package ch.hsr.objectCaching.testFrameworkServer;

import ch.hsr.objectCaching.interfaces.TestCase;

public class TestFactory 
{
	public TestCase generateTestCase(int id)
	{
		TestCase test = new TestCase();
		loadProperties(test);
		return test;
	}
	
	private void loadProperties(TestCase test)
	{
		test.setProperty("cache", "1500");
	}
}
