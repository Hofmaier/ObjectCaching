package ch.hsr.objectCaching.testFrameworkClient;

import java.io.Serializable;

public class Configuration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Configuration(){}
	
	public String getCUTName(){
		return "FakeClient";
	}

}
