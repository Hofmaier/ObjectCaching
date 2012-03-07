package ch.hsr.objectCaching.testFrameworkServer;

import java.rmi.Remote;

public interface ServerInterface extends Remote
{
	public void setReady(String ip);
}
