package ch.hsr.objectCaching.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote
{
	public void initialize(String serverIp) throws RemoteException;
	public void start() throws RemoteException;
}
