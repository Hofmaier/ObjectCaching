package ch.hsr.objectCaching.testFrameworkClient;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;
import ch.hsr.objectCaching.interfaces.Scenario;
import ch.hsr.objectCaching.interfaces.ServerInterface;

public class ClientController implements ClientInterface {

	private static final int CLIENT_PORT = 1099;
	private static final int SERVER_PORT = 24526;
	private static final String SERVER = "Server";
	private ClientSystemUnderTest clientSystemUnderTest;
	private TestClient testClient;

	public ClientController(){}
	
	@Override
	public void initialize(String serverIP, Scenario scenario, String systemUnderTest) throws RemoteException {
		testClient = new TestClient(scenario);
		clientSystemUnderTest = createClientSystemUnderTest(systemUnderTest);
		testClient.setAccountService(clientSystemUnderTest.getAccountService());
		testClient.init();
		notifyServer(serverIP, SERVER_PORT);
	}

	private ClientSystemUnderTest createClientSystemUnderTest(String systemUnderTestName) {

		ClientSystemUnderTest client = null;
		try {
			client = CUTFactory.generateCUT(systemUnderTestName);
		} catch (Exception e) {
			System.out.println("Generating Client System Under Test failed: " + e.getMessage());
		}
		return client;

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

	@Override
	public void start() throws RemoteException {
		testClient.start();
		sendResults();
	}

	private void sendResults() {
		try {
			String url = "rmi://" + "152.69.193.3" + ":" + SERVER_PORT + "/" + SERVER;
			ServerInterface serverStub = (ServerInterface) Naming.lookup(url);
			serverStub.setResults(testClient.getScenario());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
