package ch.hsr.objectCaching.testFrameworkClient;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.ServerInterface;

public class Client implements ClientInterface {

	private static final int CLIENT_PORT = 1099;
	private static final int SERVER_PORT = 1999;
	private static final String CONFIG = "Configuration";
	private Configuration configuration;
	private ClientUnderTest aktiveCUT;
	private TestCase testCase;
	
	public Client(){
	}
	
	
	@Override
	public void initialize(String serverIP) {
		loadConfig(serverIP);
		loadCUT();
		loadTestCase(serverIP);
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


	private void loadCUT() {
		try {
			aktiveCUT = CUTFactory.generateCUT(configuration.getCUTName());
		} catch (ClassNotFoundException e) {
			System.out.println("loading CUT Failed because of: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("loading CUT Failed because of: " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println("loading CUT Failed because of: " + e.getMessage());
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


	private static void notifyServer(String ip, int port) {
		try {
			String url = "rmi://" + ip + ":" + port + "/blupp";
			ServerInterface clientStub = (ServerInterface) Naming.lookup(url);
			InetAddress addr = InetAddress.getLocalHost();
			clientStub.setReady(addr.getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Client c = new Client();
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
		// TODO Auto-generated method stub
		
	}

}
