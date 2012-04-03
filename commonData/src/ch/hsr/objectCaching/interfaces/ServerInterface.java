package ch.hsr.objectCaching.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.objectCaching.scenario.Scenario;

public interface ServerInterface extends Remote
{
	public void setReady(String ip) throws RemoteException;
	public void setResults(Scenario scenario, String clientIp) throws RemoteException;
	public int getSocketPort() throws RemoteException;
}
