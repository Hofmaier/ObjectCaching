package ch.hsr.objectCaching.testFrameworkServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.TestCase;
import ch.hsr.objectCaching.testFrameworkServer.Client.Status;

public class Server implements ServerInterface
{
	private ArrayList<Client> clients;
	private final static int RMI_PORT = 1099;
	
	public Server()
	{
		loadClientList();
		TestFactory factory = new TestFactory();
		initializeClient(clients.get(0), factory.generateTestCase(1));
	}
	
	private void initializeClient(Client client, TestCase generatedTestCase) 
	{
		try {
			ClientInterface clientStub = (ClientInterface)Naming.lookup("//" + client.getIp() + "/Client");
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

	private void loadClientList()
	{
		//TODO: Liest Clients aus File raus und füllt diese in Liste ab
		clients = new ArrayList<Client>();
		clients.add(new Client("xxx"));
	}
	
	@Override
	public void setReady(String ip) 
	{
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
			LocateRegistry.createRegistry(RMI_PORT);
			ServerInterface skeleton = (ServerInterface) UnicastRemoteObject.exportObject(myServer);
			Registry reg = LocateRegistry.getRegistry();
			reg.rebind("Server", skeleton);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
