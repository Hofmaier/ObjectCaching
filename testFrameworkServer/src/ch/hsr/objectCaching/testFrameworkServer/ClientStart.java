package ch.hsr.objectCaching.testFrameworkServer;

import java.rmi.RemoteException;

public class ClientStart implements Runnable{

	private Client client;
	public ClientStart(Client client)
	{
		this.client = client;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			client.getClientStub().startTest();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
