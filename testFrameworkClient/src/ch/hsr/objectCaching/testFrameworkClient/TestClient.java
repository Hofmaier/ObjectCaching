package ch.hsr.objectCaching.testFrameworkClient;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;

import ch.hsr.objectCaching.interfaces.Action;
import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;
import ch.hsr.objectCaching.interfaces.Scenario;
import ch.hsr.objectCaching.interfaces.ServerInterface;

public class TestClient implements ClientInterface{
	
	private static final int CLIENT_PORT = 1099;
	private static final int SERVER_PORT = 24526;
	private static final String SERVER = "Server";
	private ClientSystemUnderTest client;
	private Scenario scenario;
	
	public TestClient(){}
	
	
	@Override
	public void initialize(String serverIP, Scenario scenario, String systemUnderTest) {		
		//TODO load right Client
		System.out.println("init...from Server");
		client = new Client();
		this.scenario = scenario;
		notifyServer(serverIP, SERVER_PORT);
	}

	private static void notifyServer(String serverIP, int port) {
		try {
			String url = "rmi://" + serverIP + ":" + port + "/" + SERVER;
			ServerInterface serverStub = (ServerInterface) Naming.lookup(url);
			InetAddress addr = InetAddress.getLocalHost();
			serverStub.setReady(addr.getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TestClient c = new TestClient();
		try {
			LocateRegistry.createRegistry(CLIENT_PORT);
			ClientInterface skeleton = (ClientInterface) UnicastRemoteObject.exportObject(c, CLIENT_PORT);
			Registry r = LocateRegistry.getRegistry(CLIENT_PORT);
			r.rebind("Client", skeleton);
			System.out.println("Client ready");		
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void start() {
		System.out.println("starting...");
		Iterator<Action> actionIter = scenario.getActionList().iterator();
		while (actionIter.hasNext()) {
			Action action = actionIter.next();		
			action.execute(client);
		}
	}
}
