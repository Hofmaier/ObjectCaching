package ch.hsr.objectCaching.testFrameworkClient;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.objectCaching.interfaces.ClientInterface;
import ch.hsr.objectCaching.interfaces.ServerInterface;
import ch.hsr.objectCaching.interfaces.TestCase;

public class Client implements ClientInterface {

	private static final int CLIENT_PORT = 1099;

	@Override
	public void initialize(TestCase test) {
		System.out.println("init done");
		notifyServer("152.96.193.9", 24526);
	}
	

	private void notifyServer(String ip, int port) {
		try {
			ServerInterface clientStub;
			clientStub = (ServerInterface) Naming.lookup("rmi://" + ip + ":" + port + "/blupp");
			InetAddress addr = InetAddress.getLocalHost();
			System.out.println(addr.getHostAddress());
			//clientStub.setReady(addr.getHostAddress());
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
			Registry r = LocateRegistry.getRegistry();
			r.rebind("Client", skeleton);
			System.out.println("Server ready");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
