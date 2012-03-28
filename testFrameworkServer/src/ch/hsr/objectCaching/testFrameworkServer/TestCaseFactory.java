package ch.hsr.objectCaching.testFrameworkServer;

import java.io.File;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountImpl;
import ch.hsr.objectCaching.interfaces.Scenario;

public class TestCaseFactory 
{
	private ArrayList<TestCase> testCases;
	private ArrayList<Account> accounts;
	
	public TestCaseFactory()
	{
		testCases = new ArrayList<TestCase>();
		accounts = new ArrayList<Account>();
	}
	private TestCase generateTestCase(String systemUnderTest)
	{
		TestCase temp = new TestCase(systemUnderTest);
		testCases.add(temp);
		return temp;
	}
	
	public ArrayList<TestCase> getTestCases()
	{
		return testCases;
	}
	
	public ArrayList<Account> getAccounts()
	{
		return accounts;
	}
	
	public void convertXML()
	{
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	    XMLStreamReader stax;
	    TestCase newTestCase = null;
	    Scenario newScenario = null;
	    Account newAccount = null;
		try {
			stax = inputFactory.createXMLStreamReader(new StreamSource(new File("testCases.xml")));
		      while(stax.hasNext()) 
		      {
		    	  stax.next();
		    	  if(stax.hasName() && stax.getName().toString().equals("TestCase") && stax.isStartElement())
		    	  {
		    		  newTestCase = this.generateTestCase(stax.getAttributeValue(0));
		    	  }
		    	  
		    	  if(stax.hasName() && stax.getName().toString().equals("Account") && stax.isStartElement())
		    	  {
		    		  newAccount = new AccountImpl();
		    		  newAccount.setBalance(Integer.valueOf(stax.getAttributeValue(0)));
		    		  accounts.add(newAccount);
		    	  }
		    	  
		    	  if(stax.hasName() && stax.getName().toString().equals("Scenario") && stax.isStartElement())
		    	  {
		    		  newScenario = newTestCase.generateNewScenario(Integer.valueOf(stax.getAttributeValue(0)));
		    	  }
		    	  
		    	  if(stax.hasName() && stax.getName().toString().equals("Write") && stax.isStartElement())
		    	  {
		    		  newScenario.setWriteAction(Integer.valueOf(stax.getAttributeValue(0)), Integer.valueOf(stax.getAttributeValue(1)));
		    	  }
		    	  if(stax.hasName() && stax.getName().toString().equals("Read") && stax.isStartElement())
		    	  {
		    		  newScenario.setReadAction(Integer.valueOf(stax.getAttributeValue(0)));
		    	  }
		    	  if(stax.hasName() && stax.getName().toString().equals("Increment") && stax.isStartElement())
		    	  {
		    		  newScenario.setIncrementAction(Integer.valueOf(stax.getAttributeValue(0)), Long.valueOf(stax.getAttributeValue(1)), Float.valueOf(stax.getAttributeValue(2)));
		    	  }
		    	  
		      }
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
//	public void showTestCases()
//	{
//		for(int i = 0; i< testCases.size();i++)
//		{
//			System.out.println("TestCase");
//			System.out.println("-------------------");
//			System.out.println(testCases.get(i).getClientId());
//			System.out.println(testCases.get(i).getSystemUnderTest());
//			ArrayList<Action> temp = testCases.get(i).getActionList();
//			System.out.println(temp.size());
//			for(int z = 0; z < temp.size(); z++)
//			{
//				System.out.println(temp.get(z).getAction());
//				System.out.println(temp.get(z).getValue());
//			}
//		}
//	}
//	
//	public static void main(String[] args) 
//	{
//		TestCaseFactory test = new TestCaseFactory();
//		test.convertXML();
//		test.showTestCases();
//	}
}
