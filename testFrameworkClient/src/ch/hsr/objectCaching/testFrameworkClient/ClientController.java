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

import ch.hsr.objectCaching.interfaces.clientSystemUnderTest.ClientSystemUnderTest;
import ch.hsr.objectCaching.interfaces.testFrameworkClient.ClientInterface;
import ch.hsr.objectCaching.interfaces.testFrameworkServer.ServerInterface;
import ch.hsr.objectCaching.scenario.Scenario;
import ch.hsr.objectCaching.util.Configuration;

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
		clientSystemUnderTest.setServerSocketAdress(new InetSocketAddress(config.getServerIP(), config.getServerSocketPort()));
		
		testClient = new TestClient(clientSystemUnderTest);
		testClient.setScenario(scenario);
		testClient.init();

		loadServerStub(config.getServerIP(), config.getServerRMIPort(), config.getServerRegistryName());
		notifyServerInitDone();
	}
	
	@Override
	public void startTest() throws RemoteException {
		testClient.runScenario();
		sendResultToServer(testClient.getScenario());
	}
	
	@Override
	public void shutdown(){
		testClient.shutdown();
		shutdownClientController();
	}
	
	private void loadServerStub(String serverIP, int port, String registryName) {
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
		try {
			return CUTFactory.generateCUT(systemUnderTestName);
		} catch (Exception e) {
			System.out.println("Generating ClientSystemUnderTest failed: " + e.getMessage());
		}
		return null;
	}

	private void sendResultToServer(Scenario scenario) {
		try {
			server.setResults(scenario, InetAddress.getLocalHost().getHostAddress());
		} catch (RemoteException e) {
			System.out.println("Failed to send Results back to server: " + e.getMessage());
		} catch (UnknownHostException e) {
			System.out.println("Host not known: " + e.getMessage());
		}
	}
	
	private void notifyServerInitDone() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			server.setReady(addr.getHostAddress());
		} catch (UnknownHostException e) {
			System.out.println("Host not known: " + e.getMessage());
		} catch (RemoteException e) {
			System.out.println("setReady failed on Server");
		}
	}
	

	
	private void shutdownClientController() {
		try {
			Naming.unbind("Client");
	        UnicastRemoteObject.unexportObject(this, true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        new Thread() {
            @Override
            public void run() {
            	System.out.println("ClientController exiting. Bye.");
              try {
                sleep(2000);
              } catch (InterruptedException e) {
              }
              System.out.println("done");
              System.exit(0);
            }

          }.start();
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
