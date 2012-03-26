package ch.hsr.objectCaching.testFrameworkClient;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;
import ch.hsr.objectCaching.interfaces.Configuration;
import ch.hsr.objectCaching.interfaces.Scenario;
import ch.hsr.objectCaching.interfaces.ServerInterface;

public class ClientController implements ClientInterface {

	private static final int CLIENT_PORT = 1099;
	private ServerInterface server;
	private TestClient testClient;
	private Configuration config;
	
	public ClientController() {
	}

	@Override
	public void initialize(Scenario scenario, Configuration configuration) throws RemoteException {		
		config = configuration;		
		ClientSystemUnderTest clientSystemUnderTest = createClientSystemUnderTest(configuration.getNameOfSystemUnderTest());		
		InetSocketAddress socket = new InetSocketAddress(config.getServerIP(), config.getServerSocketPort());
		clientSystemUnderTest.setServerSocketAdress(socket);
		
		testClient = new TestClient(scenario);	
		testClient.setAccountService(clientSystemUnderTest.getAccountService());
		testClient.init();

		loadServer(config.getServerIP(), config.getServerRMIPort(), config.getServerRegistryName());
		notifyServerInitDone();
	}

	private void loadServer(String serverIP, int port, String registryName) {
		try {
			String url = "rmi://" + serverIP + ":" + port + "/" + registryName;
			server = (ServerInterface) Naming.lookup(url);
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

	private ClientSystemUnderTest createClientSystemUnderTest(String systemUnderTestName) {
		ClientSystemUnderTest client = null;
		try {
			client = CUTFactory.generateCUT(systemUnderTestName);
		} catch (Exception e) {
			System.out.println("Generating ClientSystemUnderTest failed: " + e.getMessage());
		}
		return client;
	}


	@Override
	public void start() throws RemoteException {
		testClient.start();
		sendResults(testClient.getScenario());
	}

	private void sendResults(Scenario scenario) {
		try {
			server.setResults(scenario, InetAddress.getLocalHost().getHostAddress());
		} catch (RemoteException e) {
			System.out.println("Failed to send Results back to server: " + e.getMessage());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void notifyServerInitDone() {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			server.setReady(addr.getHostAddress());
		} catch (UnknownHostException e) {
			System.out.println("Host not known: " + e.getMessage());
		} catch (RemoteException e) {
			System.out.println("setReady failed on Server");
		}
	}
	
	@Override
	public void exitClient(){
		System.exit(0);
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientController c = new ClientController();
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

}
