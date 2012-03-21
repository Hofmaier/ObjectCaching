package ch.hsr.objectCaching.testFrameworkServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import ch.hsr.objectCaching.interfaces.Action;
import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.Configuration;
import ch.hsr.objectCaching.interfaces.ReadAction;
import ch.hsr.objectCaching.interfaces.Scenario;
import ch.hsr.objectCaching.interfaces.ServerInterface;
import ch.hsr.objectCaching.interfaces.WriteAction;
import ch.hsr.objectCaching.testFrameworkServer.Client.Status;

public class Server implements ServerInterface
{
	private ArrayList<Client> clients;
	private ArrayList<TestCase> testCases;
	private static int clientRmiPort;
	private Properties initFile;
	private Dispatcher dispatcher;
	private TestCase activeTestCase;
	private TestCaseFactory factory;
	private Configuration configuration;
	
	public Server()
	{
		loadInitFile();
		prepareClientList();
		loadSettings();
		generateTestCases();
		establishClientConnection();
		createRmiRegistry();
		dispatcher = new Dispatcher(configuration.getServerSocketPort());
		new Thread(dispatcher).start();
	}
	
	private void generateTestCases()
	{
		factory = new TestCaseFactory();
		factory.convertXML();
		testCases = factory.getTestCases();
		activeTestCase = testCases.get(0);
		configuration.setNameOfSystemUnderTest(activeTestCase.getSystemUnderTest());
	}
	
	private void startTestCase()
	{
		System.out.println("Starting TestCase");
		dispatcher.setSystemUnderTest(activeTestCase.getSystemUnderTest());
		initializeClients();
	}
	
	private void initializeClients()
	{
		System.out.println("initializeClients");
		Scenario temp;
		for(int i = 0; i < clients.size(); i++)
		{
			if((temp = activeTestCase.getScenarios().get(i)) != null)
			{
				try {
					clients.get(i).getClientStub().initialize(temp, configuration);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					clients.get(i).getClientStub().initialize(activeTestCase.getScenario(0), configuration);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void establishClientConnection() 
	{
		System.out.println("establishClientConnection");
		try {
			for(int i = 0; i < clients.size(); i++)
			{
				ClientInterface clientStub = (ClientInterface)Naming.lookup("rmi://" + clients.get(i).getIp() + ":" + clientRmiPort + "/Client");
				clients.get(i).setClientStub(clientStub);
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
	
	private void loadInitFile()
	{
		initFile = new Properties();
		InputStream initFileStream;
		
		try {
			initFileStream = new FileInputStream("initFile.conf");
			initFile.load(initFileStream);
			initFileStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void loadSettings()
	{
		Iterator<Entry<Object, Object>> iter = initFile.entrySet().iterator();
		configuration = new Configuration();
		
		while(iter.hasNext())
		{
			Entry<Object, Object> temp = iter.next();
			if(temp.getKey().equals("Clientport"))
			{
				clientRmiPort = Integer.valueOf((String)temp.getValue());
			}
			if(temp.getKey().equals("ServerRmiPort"))
			{
				configuration.setServerRMIPort(Integer.valueOf((String)temp.getValue()));
			}
			if(temp.getKey().equals("ServerRegistryName"))
			{
				configuration.setServerRegistryName((String)temp.getValue());
			}
			if(temp.getKey().equals("ServerSocketPort"))
			{
				configuration.setServerSocketPort(Integer.valueOf((String)temp.getValue()));
			}
		}
		try {
			configuration.setServerIP(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void prepareClientList()
	{
		clients = new ArrayList<Client>();
		for(int i = 0; i < initFile.size();i++)
		{
			String clientName = "Client" + i;
			if(initFile.containsKey(clientName))
			{
				Client client = new Client((String)initFile.get(clientName));
				clients.add(client);
			}
		}
	}
	
	@Override
	public void setReady(String ip) 
	{
		
		System.out.println("Setted ready with: " + ip);
		for(int i = 0; i < clients.size(); i++)
		{
			if(clients.get(i).getIp().equals(ip))
			{
				clients.get(i).setStatus(Status.READY);
			}
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
		for(int i = 0; i < clients.size(); i++)
		{
			try {
				clients.get(i).getClientStub().start();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean checkAllReady()
	{
		for(int i = 0; i < clients.size(); i++)
		{
			if(clients.get(i).getStatus() == Status.NOTREADY)
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
	public void setResults(Scenario scenario) 
	{
		//TODO: Auswertung der ankommenden Resultate
		System.out.println("results setted");
		System.out.println(scenario.getId());
		for(int i = 0; i < scenario.getActionList().size(); i++)
		{
			Action action = scenario.getActionList().get(i);
			if(action instanceof WriteAction)
			{
				System.out.println("Action was a Write-Action with: ");
			}
			
			if(action instanceof ReadAction)
			{
				System.out.println("Action was a Read-Action with: ");
			}
		}
//		for(int i = 0; i < testCases.size(); i++)
//		{
//			if(testCases.get(i).equals(activeTestCase) && testCases.get(i + 1) != null)
//			{
//				activeTestCase = testCases.get(i + 1);
//				startTestCase();
//			}
//		}
	}
	
	public static void main(String[] args) 
	{
		Server myServer = new Server();
		myServer.startTestCase();
	}
}
