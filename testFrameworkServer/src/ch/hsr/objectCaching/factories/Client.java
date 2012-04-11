package ch.hsr.objectCaching.factories;

import ch.hsr.objectCaching.interfaces.ClientInterface;

public class Client 
{
	private String ip;
	private ClientInterface clientStub;
	public enum StartingState{
		READY,
		NOTREADY;
	}
	public enum ShutedDown{
		RUNNING,
		DOWN;
	}
	StartingState clientReady;
	ShutedDown clientRunning;
	public ShutedDown getClientRunning() {
		return clientRunning;
	}

	public void setClientRunning(ShutedDown clientUp) {
		this.clientRunning = clientUp;
	}

	public Client(String ip)
	{
		this.ip = ip;
		clientReady = StartingState.NOTREADY; 
		clientRunning = ShutedDown.RUNNING;
	}
	
	public void setStartingState(StartingState status)
	{
		this.clientReady = status;
	}
	
	public StartingState getStartingState()
	{
		return this.clientReady;
	}
	
	public String getIp()
	{
		return this.ip;
	}

	public ClientInterface getClientStub() {
		return clientStub;
	}

	public void setClientStub(ClientInterface clientStub) {
		this.clientStub = clientStub;
	}
}
