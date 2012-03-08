package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;
import java.util.HashMap;

public class TestCase implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private String className;
	private HashMap<String, String> properties;
	
	
	public TestCase()
	{
		properties =  new HashMap<String, String>();
	}
	
	public void setProperty(String key, String value)
	{
		properties.put(key, value);
	}
}
