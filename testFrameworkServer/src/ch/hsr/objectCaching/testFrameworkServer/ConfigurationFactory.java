package ch.hsr.objectCaching.testFrameworkServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import ch.hsr.objectCaching.util.Configuration;



public class ConfigurationFactory 
{
	Properties initFile;
	Configuration configuration;
	ClientList clientList;
	public ConfigurationFactory()
	{
		loadInitFile();
		createConfigurationObject();
		createClientList();
	}
	
	private void loadInitFile()
	{
		initFile = new Properties();
		InputStream initFileStream;
		try {
			initFileStream = new FileInputStream("initFile.conf");
			initFile.load(initFileStream);
			initFileStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void createConfigurationObject()
	{
		Iterator<Entry<Object, Object>> iter = initFile.entrySet().iterator();
		configuration = new Configuration();
		
		while(iter.hasNext())
		{
			Entry<Object, Object> temp = iter.next();
			if(temp.getKey().equals("Clientport"))
			{
				configuration.setClientRmiPort(Integer.valueOf((String)temp.getValue()));
			}
			if(temp.getKey().equals("ServerRmiPort"))
			{
				configuration.setServerRMIPort(Integer.valueOf((String)temp.getValue()));
			}
			if(temp.getKey().equals("ServerRegistryName"))
			{
				configuration.setServerRegistryName((String)temp.getValue());
			}
			if(temp.getKey().equals("ServerSocketPort"))
			{
				configuration.setServerSocketPort(Integer.valueOf((String)temp.getValue()));
			}
		}
		try {
			configuration.setServerIP(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createClientList()
	{
		clientList = new ClientList();
		for(int i = 0; i < initFile.size();i++)
		{
			String clientName = "Client" + i;
			if(initFile.containsKey(clientName))
			{
				Client client = new Client((String)initFile.get(clientName));
				clientList.addClient(client);
			}
		}
	}
	
	public Configuration getConfiguration()
	{
		return configuration;
	}
	
	public ClientList getClientList()
	{
		return clientList;
	}
}
