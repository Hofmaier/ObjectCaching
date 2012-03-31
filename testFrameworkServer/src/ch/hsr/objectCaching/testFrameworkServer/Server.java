package ch.hsr.objectCaching.testFrameworkServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.interfaces.testFrameworkClient.ClientInterface;
import ch.hsr.objectCaching.interfaces.testFrameworkServer.ServerInterface;
import ch.hsr.objectCaching.reporting.ReportGenerator;
import ch.hsr.objectCaching.scenario.Scenario;
import ch.hsr.objectCaching.testFrameworkServer.Client.ShutedDown;
import ch.hsr.objectCaching.testFrameworkServer.Client.StartingState;
import ch.hsr.objectCaching.util.Configuration;

public class Server implements ServerInterface
{
	private ClientList clientList;
	private ArrayList<TestCase> testCases;
	private Dispatcher dispatcher;
	private TestCase activeTestCase;
	private TestCaseFactory testCaseFactory;
	private Configuration configuration;
	private ArrayList<Account> accounts;
	private ConfigurationFactory configFactory;
	
	public Server()
	{
		configFactory = new ConfigurationFactory();
		clientList = configFactory.getClientList();
		configuration = configFactory.getConfiguration();
		generateTestCases();
		establishClientConnection();
		createRmiRegistry();
		dispatcher = new Dispatcher(configuration.getServerSocketPort());
		accounts = testCaseFactory.getAccounts();
		new Thread(dispatcher).start();
	}
	
	private void generateTestCases()
	{
		testCaseFactory = new TestCaseFactory();
		testCaseFactory.convertXML();
		testCases = testCaseFactory.getTestCases();
		activeTestCase = testCases.get(0);
		configuration.setNameOfSystemUnderTest(activeTestCase.getSystemUnderTest());
	}
	
	private void startTestCase()
	{
		System.out.println("Starting TestCase");
		dispatcher.setSystemUnderTest(activeTestCase.getSystemUnderTest(), accounts.get(0));
		initializeClients();
	}
	
	private Scenario getScenarioForClient(int index)
	{
		if(activeTestCase.getScenarios().size() > index)
		{
			return activeTestCase.getScenarios().get(index);
		}
		else
		{
			return activeTestCase.getScenarios().get(0);
		}
	}
	
	private void initializeClients()
	{
		System.out.println("initializeClients");
		for(int i = 0; i < clientList.size(); i++)
		{
			try {
				clientList.getClient(i).getClientStub().initialize(getScenarioForClient(i), configuration);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private void establishClientConnection() 
	{
		System.out.println("establishClientConnection");
		try {
			for(int i = 0; i < clientList.size(); i++)
			{
				System.out.println(clientList.getClient(i).getIp());
				ClientInterface clientStub = (ClientInterface)Naming.lookup("rmi://" + clientList.getClient(i).getIp() + ":" + configuration.getClientRmiPort() + "/Client");
				clientList.getClient(i).setClientStub(clientStub);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void setReady(String ip) 
	{
		System.out.println("Setted ready with: " + ip);
		Client temp;
		if((temp = clientList.getClientByIp(ip)) != null)
		{
			temp.setStartingState(StartingState.READY);
		}
		if(checkAllReady())
		{
			start();
		}
	}
	
	public int getSocketPort()
	{
		return configuration.getServerSocketPort();
	}
	
	private void start()
	{
		System.out.println("start");
		for(int i = 0; i < clientList.size(); i++)
		{
			ClientStart clientStart = new ClientStart(clientList.getClient(i));
			new Thread(clientStart).start();
		}
	}
	
	private boolean checkAllReady()
	{
		for(int i = 0; i < clientList.size(); i++)
		{
			if(clientList.getClient(i).getStartingState() == StartingState.NOTREADY)
			{
				return false;
			}
		}
		return true;
	}
		
	private void createRmiRegistry()
	{
		try {
			LocateRegistry.createRegistry(configuration.getServerRMIPort());
			ServerInterface skeleton = (ServerInterface) UnicastRemoteObject.exportObject(this, configuration.getServerRMIPort());
			Registry reg = LocateRegistry.getRegistry(configuration.getServerRMIPort());
			reg.rebind(configuration.getServerRegistryName(), skeleton);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	

	
	@Override
	public void setResults(Scenario scenario, String clientIp) 
	{
		//TODO: Auswertung der ankommenden Resultate
		System.out.println("results setted");
		System.out.println(scenario.getId());
//		for(int i = 0; i < scenario.getActionList().size(); i++)
//		{
//			Action action = scenario.getActionList().get(i);
//			if(action instanceof WriteAction)
//			{
//				System.out.println("Action was a Write-Action with: " + ((WriteAction)action).getValue());
//			}
//			
//			if(action instanceof ReadAction)
//			{
//				System.out.println("Action was a Read-Action with: " + ((ReadAction)action).getBalance());
//			}
//			
//		}
		ReportGenerator report = new ReportGenerator();
		report.addScenario(scenario);
		report.makeSummary();
		
		System.out.println("Account should be: ");
		System.out.println("Account is actually: " + accounts.get(0).getBalance());
		
		for(int i = 0; i < testCases.size(); i++)
		{
			if(testCases.get(i).equals(activeTestCase) && testCases.size() > i+1)
			{
				activeTestCase = testCases.get(i + 1);
				startTestCase();
			}
			else
			{
				stopClient(clientIp);
			}
		
		}
	}
	
	private void stopClient(String clientIp)
	{

		Client temp;
		try {
			
			if((temp = clientList.getClientByIp(clientIp)) != null)
			{
				System.out.println("Stop Client with " + clientIp);
				temp.getClientStub().shutdown();
				temp.setClientRunning(ShutedDown.DOWN);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(checkAllShutedDown())
		{
			System.out.println("Alle Clients sind down!");
		}
	}
	
	private boolean checkAllShutedDown()
	{
		for(int i = 0; i < clientList.size(); i++)
		{
			if(clientList.getClient(i).getClientRunning() == ShutedDown.RUNNING)
			{
				return false;
			}
		}
		return true;

	}
	
	public static void main(String[] args) 
	{
		Server myServer = new Server();
		myServer.startTestCase();
	}
}
