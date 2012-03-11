package ch.hsr.objectCaching.testFrameworkClient;

public class CUTFactory {

	public static ClientUnderTest generateCUT(String targetName) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> clazz = Class.forName(targetName);
		ClientUnderTest cut = (ClientUnderTest) clazz.newInstance();
		return cut; 
	}
	
}
