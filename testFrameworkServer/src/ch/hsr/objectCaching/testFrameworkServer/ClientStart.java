package ch.hsr.objectCaching.testFrameworkServer;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.hsr.objectCaching.factories.Client;

public class ClientStart implements Runnable{

	private Client client;
	private Logger logger;
	public ClientStart(Client client)
	{
		this.client = client;
		logger = Logger.getLogger("TestFrameWorkServer");
	}
	@Override
	public void run() {
		try {
			client.getClientStub().startTest();
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		}
	}

}
