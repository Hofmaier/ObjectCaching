package ch.hsr.objectCaching.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote
{
	public void initialize(Scenario scenario, Configuration configuration) throws RemoteException;
	public void start() throws RemoteException;
	public void exitClient() throws RemoteException;
}
