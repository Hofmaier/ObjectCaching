package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;

public class Configuration implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serverIp;
	private int serverRMIPort;
	private int serverSocketPort;
	private String nameOfSystemUnderTest;
	private String serverRegistryName;
	
	public Configuration(String serverIp, int serverRMIPort, int serverSocketPort, String nameOfSystemUnderTest, String serverRegistryName)
	{
		this.serverIp = serverIp;
		this.serverRMIPort = serverRMIPort;
		this.serverSocketPort = serverSocketPort;
		this.nameOfSystemUnderTest = nameOfSystemUnderTest;
		this.serverRegistryName = serverRegistryName;
	}
	
	public Configuration() {
		// TODO Auto-generated constructor stub
	}

	public int getServerRMIPort() {
		return serverRMIPort;
	}
	public void setServerRMIPort(int serverRMIPort) {
		this.serverRMIPort = serverRMIPort;
	}
	public int getServerSocketPort() {
		return serverSocketPort;
	}
	public void setServerSocketPort(int serverSocketPort) {
		this.serverSocketPort = serverSocketPort;
	}
	public String getNameOfSystemUnderTest() {
		return nameOfSystemUnderTest;
	}
	public void setNameOfSystemUnderTest(String nameOfSystemUnderTest) {
		this.nameOfSystemUnderTest = nameOfSystemUnderTest;
	}
	public String getServerRegistryName() {
		return serverRegistryName;
	}
	public void setServerRegistryName(String serverRegistryName) {
		this.serverRegistryName = serverRegistryName;
	}
	public String getServerIP() {
		return serverIp;
	}
	public void setServerIP(String serverIP) {
		this.serverIp = serverIP;
	}
}
