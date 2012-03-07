package ch.hsr.objectCaching.testFrameworkServer;

public class Client 
{
	private String ip;
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
}
