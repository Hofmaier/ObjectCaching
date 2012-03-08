package ch.hsr.objectCaching.testFrameworkServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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

import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.ServerInterface;
import ch.hsr.objectCaching.interfaces.TestCase;
import ch.hsr.objectCaching.testFrameworkServer.Client.Status;

public class Server implements ServerInterface
{
	private ArrayList<Client> clients;
	private static int clientPort;
	private Properties initFile;
	
	public Server()
	{
		loadInitFile();
		loadClientList();
		loadSettings();
		TestFactory factory = new TestFactory();
		initializeClient(clients.get(0), factory.generateTestCase(1));
	}
	
	private void initializeClient(Client client, TestCase generatedTestCase) 
	{
		try {
			ClientInterface clientStub = (ClientInterface)Naming.lookup("rmi://" + client.getIp() + ":" + clientPort + "/Client");
			clientStub.initialize(generatedTestCase);
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
		while(iter.hasNext())
		{
			Entry<Object, Object> temp = iter.next();
			if(temp.getKey().equals("Clientport"))
			{
				clientPort = Integer.valueOf((String)temp.getValue());
			}
		}
		System.out.println(clientPort);
	}
	
	private void loadClientList()
	{
		clients = new ArrayList<Client>();
		Iterator<Entry<Object, Object>> iter = initFile.entrySet().iterator();
		while(iter.hasNext())
		{
			Entry<Object, Object> temp = iter.next();
			if(temp.getKey().equals("Client"))
			{
				Client client = new Client((String)temp.getValue());
				clients.add(client);
			}
		}
	}
	
	@Override
	public void setReady(String ip) 
	{
		System.out.println(ip);
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
	
	private void start()
	{
		
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
	
	private void createTestFactory()
	{
		TestFactory factory = new TestFactory();
	}
	
	public static void main(String[] args) 
	{
		Server myServer = new Server();
		try {
			LocateRegistry.createRegistry(24526);
			ServerInterface skeleton = (ServerInterface) UnicastRemoteObject.exportObject(myServer, 24526);
			Registry reg = LocateRegistry.getRegistry();
			reg.rebind("blupp", skeleton);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
