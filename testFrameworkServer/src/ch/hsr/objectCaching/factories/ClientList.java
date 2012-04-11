package ch.hsr.objectCaching.factories;

import java.util.ArrayList;

public class ClientList 
{
	private ArrayList<Client> clientList;
	
	public ClientList()
	{
		clientList = new ArrayList<Client>();
	}
	
	public void addClient(Client client)
	{
		clientList.add(client);
	}
	
	public Client getClientByIp(String ip)
	{
		for(int i = 0; i < clientList.size(); i++)
		{
			if(clientList.get(i).getIp().equals(ip))
			{
				return clientList.get(i);
			}
		}
		return null;
	}
	
	public Client getClient(int index)
	{
		return clientList.get(index);
	}
	
	public ArrayList<Client> getAllClients()
	{
		return clientList;
	}
	
	public int size()
	{
		return clientList.size();
	}
}
