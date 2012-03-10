package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;
import java.util.ArrayList;

public class TestCase implements Serializable
{
	
	private static final long serialVersionUID = 1;
	private String systemUnderTest;
	private ArrayList<Action> actionList;
	private int clientId;
	
	public TestCase(int clientId)
	{
		this.clientId = clientId;
		actionList = new ArrayList<Action>();
	}
	
	public int getClientId()
	{
		return clientId;
	}

	public String getSystemUnderTest() {
		return systemUnderTest;
	}

	public void setSystemUnderTest(String systemUnderTest) {
		this.systemUnderTest = systemUnderTest;
	}
	
	public ArrayList<Action> getActionList()
	{
		return actionList;
	}
	
	public void setWriteAction(int count, int value)
	{
		Action temp;
		for(int i = 0; i < count; i++)
		{
			temp = new Action();
			temp.setAction("setBalance");
			temp.setValue(value);
			actionList.add(temp);
		}
	}
	
	public void setReadAction(int count)
	{
		Action temp;
		for(int i = 0; i < count; i++)
		{
			temp = new Action();
			temp.setAction("getBalance");
			actionList.add(temp);
		}
	}
}
