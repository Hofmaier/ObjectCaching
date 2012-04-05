package ch.hsr.objectCaching.testFrameworkServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.ServerInterface;
import ch.hsr.objectCaching.reporting.ReportGenerator;
import ch.hsr.objectCaching.scenario.Scenario;
import ch.hsr.objectCaching.testFrameworkServer.Client.ShutedDown;
import ch.hsr.objectCaching.testFrameworkServer.Client.StartingState;
import ch.hsr.objectCaching.util.Configuration;

public class Server implements ServerInterface
{
	private ClientList clientList;
	private Dispatcher dispatcher;
	private TestCase activeTestCase;
	private TestCaseFactory testCaseFactory;
	private Configuration configuration;
	private ArrayList<Account> accounts;
	private ConfigurationFactory configFactory;
	private Logger logger;
	private MethodCallLogger methodCallLogger;
	private String testCaseFileName;
	private ResultGenerator resultGenerator;
	
	public Server(String testCaseFileName)
	{
		this.testCaseFileName = testCaseFileName;
		methodCallLogger = new MethodCallLogger("textLog.txt");
		logger = Logger.getLogger("TestFrameWorkServer");
		configFactory = new ConfigurationFactory();
		clientList = configFactory.getClientList();
		configuration = configFactory.getConfiguration();
		generateTestCases();
		establishClientConnection();
		createRmiRegistry();
		dispatcher = new Dispatcher(configuration.getServerSocketPort());
		accounts = testCaseFactory.getAccounts();
		resultGenerator = new ResultGenerator(activeTestCase, accounts.get(0).getBalance());
		new Thread(dispatcher).start();
	}
	
	private void generateTestCases()
	{
		testCaseFactory = new TestCaseFactory(testCaseFileName);
		testCaseFactory.convertXML();
		activeTestCase = testCaseFactory.getTestCase();
		configuration.setNameOfSystemUnderTest(activeTestCase.getSystemUnderTest());
	}
	
	private void startTestCase()
	{
		logger.info("Starting TestCase");
		dispatcher.setSystemUnderTest(activeTestCase.getSystemUnderTest(), accounts.get(0), methodCallLogger);
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
		logger.info("initializeClients");
		for(int i = 0; i < clientList.size(); i++)
		{
			try {
				clientList.getClient(i).getClientStub().initialize(getScenarioForClient(i), configuration);
			} catch (RemoteException e) {
				logger.log(Level.SEVERE, "Uncaught exception", e);
			}
		}
	}
	
	private void establishClientConnection() 
	{
		logger.info("establishClientConnection");
		try {
			for(int i = 0; i < clientList.size(); i++)
			{
				ClientInterface clientStub = (ClientInterface)Naming.lookup("rmi://" + clientList.getClient(i).getIp() + ":" + configuration.getClientRmiPort() + "/Client");
				clientList.getClient(i).setClientStub(clientStub);
			}
			
		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		} catch (NotBoundException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		}
	}
	
	@Override
	public void setReady(String ip) 
	{
		logger.info("Setted ready with: " + ip);
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
		logger.info("Method start() invoked");
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
			logger.log(Level.SEVERE, "Uncaught exception", e);
		}
	}
	
	@Override
	public void setResults(Scenario scenario, String clientIp) 
	{
		logger.info("Results from scenario " + scenario.getId() + " setted by " + clientIp);
		ReportGenerator report = new ReportGenerator();
		report.addScenario(scenario);
		report.makeSummary();
		
		stopClient(clientIp);
	}
	
	private void stopClient(String clientIp)
	{
		Client temp;
		try {
			
			if((temp = clientList.getClientByIp(clientIp)) != null)
			{
				logger.info("stop client with ip: " + clientIp);
				temp.getClientStub().shutdown();
				temp.setClientRunning(ShutedDown.DOWN);
			}
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		}
		if(checkAllShutedDown())
		{
			logger.info("All clients are down!");
			logger.info("AccountBalance is: " + accounts.get(0).getBalance());
			logger.info("AccountBalance should be: " + resultGenerator.getResult());
			if(resultGenerator.getResult() == accounts.get(0).getBalance())
			{
				logger.info("No Lost-Updates!");
			}
			else
			{
				logger.info("Lost-Update occured!");
			}
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
		if(args.length == 0)
		{
			Server myServer = new Server("testCases.xml");
			myServer.startTestCase();
		}
		else
		{
			System.out.println("customized testCase");
			Server myServer = new Server(args[0]);
			myServer.startTestCase();
		}	
	}
}
