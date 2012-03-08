package ch.hsr.objectCaching.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote
{
	public void setReady(String ip) throws RemoteException;
	public void setResults() throws RemoteException;
	public int getSocketPort() throws RemoteException;
}