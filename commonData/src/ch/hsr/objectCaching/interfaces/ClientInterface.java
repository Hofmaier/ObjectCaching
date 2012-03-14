package ch.hsr.objectCaching.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote
{
	public void initialize(String serverIP, Scenario scenario) throws RemoteException;
	public void start() throws RemoteException;
}
