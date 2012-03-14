package ch.hsr.objectCaching.testFrameworkServer;

import ch.hsr.objectCaching.interfaces.ClientInterface;

public class Client 
{
	private String ip;
	private ClientInterface clientStub;
	enum Status{
		READY,
		NOTREADY;
	}
	Status clientStatus;
	public Client(String ip)
	{
		this.ip = ip;
		clientStatus = Status.NOTREADY; 
	}
	
	public void setStatus(Status status)
	{
		this.clientStatus = status;
	}
	
	public Status getStatus()
	{
		return this.clientStatus;
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
