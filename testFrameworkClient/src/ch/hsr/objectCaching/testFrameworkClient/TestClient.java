package ch.hsr.objectCaching.testFrameworkClient;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.Scenario;
import ch.hsr.objectCaching.interfaces.ServerInterface;

public class TestClient implements ClientInterface{
	
	private static final int CLIENT_PORT = 1099;
	private static final int SERVER_PORT = 1999;
	private static final String CONFIG = "Configuration";
	private static final String SCENARIO = "Scenario";
	private Configuration configuration;
	private Client client;
	private TestCase testCase;
	
	public TestClient(){
	}
	
	
	@Override
	public void initialize(String serverIP) {
		Scenario s = loadScenario(serverIP);
		client = new Client(s);
		notifyServer(serverIP, SERVER_PORT);
		
//		loadConfig(serverIP);
//		loadCUT();
//		loadTestCase(serverIP);
	}


	private Scenario loadScenario(String serverIP) {
		Scenario scenario = null;
		try {
			String url = "rmi://" + serverIP + ":" + SERVER_PORT + "/" + SCENARIO;
			scenario = (Scenario) Naming.lookup(url);
		} catch (Exception e) {
			System.out.println("loading test case failed: " + e.getMessage());
		}
		return scenario;
	}


	private void loadTestCase(String serverIP) {
		try {
			String url = "rmi://" + serverIP + ":" + SERVER_PORT + "/" + CONFIG;
			ConfigurationProvider config = (ConfigurationProvider) Naming.lookup(url);
			testCase = config.getTestCase();
		} catch (Exception e) {
			System.out.println("loading test case failed: " + e.getMessage());
		}		
	}


	private void loadConfig(String serverIP) {
		try {
			String url = "rmi://" + serverIP + ":" + SERVER_PORT + "/" + CONFIG;
			ConfigurationProvider config = (ConfigurationProvider) Naming.lookup(url);
			configuration = config.getConfiguration();
		} catch (Exception e) {
			System.out.println("loading config failed: " + e.getMessage());
		}		
	}


	private void setValues() {
		notifyServer("152.96.193.9", 24526);
	}


	private static void notifyServer(String serverIP, int port) {
		try {
			String url = "rmi://" + serverIP + ":" + port + "/blupp";
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
		client.startTest();
	}
}
