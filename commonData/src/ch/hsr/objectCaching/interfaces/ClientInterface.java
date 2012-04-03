package ch.hsr.objectCaching.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.objectCaching.scenario.Scenario;
import ch.hsr.objectCaching.util.Configuration;


public interface ClientInterface extends Remote
{
	public void initialize(Scenario scenario, Configuration configuration) throws RemoteException;
	public void startTest() throws RemoteException;
	public void shutdown() throws RemoteException;
}
