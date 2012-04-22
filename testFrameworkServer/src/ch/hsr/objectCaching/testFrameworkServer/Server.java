package ch.hsr.objectCaching.testFrameworkServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.factories.Client;
import ch.hsr.objectCaching.factories.Client.ShutedDown;
import ch.hsr.objectCaching.factories.Client.StartingState;
import ch.hsr.objectCaching.factories.ClientList;
import ch.hsr.objectCaching.factories.ConfigurationFactory;
import ch.hsr.objectCaching.factories.TestCase;
import ch.hsr.objectCaching.factories.TestCaseFactory;
import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.ServerInterface;
import ch.hsr.objectCaching.reporting.ReportGenerator;
import ch.hsr.objectCaching.reporting.ResultGenerator;
import ch.hsr.objectCaching.scenario.Scenario;
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
	private ArrayList<String> summaries;
	private HashMap<String, Scenario> scenarioPerClient;
	
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
		new Thread(dispatcher).start();
		summaries = new ArrayList<String>();
	}
	
	private void generateTestCases()
	{
		testCaseFactory = new TestCaseFactory(testCaseFileName);
		testCaseFactory.convertXML();
		accounts = testCaseFactory.getAccounts();
		activeTestCase = testCaseFactory.getTestCase();
		configuration.setNameOfSystemUnderTest(activeTestCase.getClientSystemUnderTest());
	}
	
	private void startTestCase()
	{
		logger.info("Starting TestCase");
		dispatcher.setSystemUnderTest(activeTestCase.getServerSystemUnderTest(), accounts.get(0), methodCallLogger);
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
		scenarioPerClient = new HashMap<String, Scenario>();
		logger.info("initializeClients");
		for(int i = 0; i < clientList.size(); i++)
		{
			try {
				Scenario temp = getScenarioForClient(i);
				scenarioPerClient.put(clientList.getClient(i).getIp(), temp);
				clientList.getClient(i).getClientStub().initialize(temp, configuration);
			} catch (RemoteException e) {
				logger.log(Level.SEVERE, "Uncaught exception", e);
			}
		}
		resultGenerator = new ResultGenerator(scenarioPerClient, accounts.get(0).getBalance());
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
		ReportGenerator report = new ReportGenerator(scenario, clientIp);
		summaries.add(report.getSummary());
		stopClient(clientIp);
	}
	
	private void stopClient(String clientIp)
	{
		Client temp;
		try {
			
			if((temp = clientList.getClientByIp(clientIp)) != null)
			{
				logger.info("stop client with ip: " + clientIp);
				temp.setClientRunning(ShutedDown.DOWN);
				temp.getClientStub().shutdown();
			}
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		}
		if(checkAllShutedDown())
		{
			printSummary();
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
	
	private void printSummary()
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
		
		for(int i = 0; i < summaries.size(); i++)
		{
			//logger.info(summaries.get(i));
		}
		
		shutDownTestFrameWorkServer();
	}
	
	private void shutDownTestFrameWorkServer() 
	{
		try {
			Naming.unbind("rmi://localhost:" + configuration.getServerRMIPort() + "/" + configuration.getServerRegistryName());
			UnicastRemoteObject.unexportObject(this, true);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		} catch (NotBoundException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		}
		
		closeServer(2000);
	}

	private void closeServer(final long delay) 
	{
		new Thread() {
			@Override
			public void run() {
				logger.info("TestFrameWorkServer is shutting down");
				try {
					sleep(delay);
				} catch (InterruptedException e) {
				}
				System.exit(0);
			}
		}.start();
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
