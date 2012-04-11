package ch.hsr.objectCaching.testFrameworkClient;

import java.util.logging.Logger;

import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;

public class CUTFactory {
	
	private static Logger logger = Logger.getLogger(CUTFactory.class.getName());

	public static ClientSystemUnderTest generateCUT(String targetName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		 Class<?> clazz = Class.forName(targetName);
		 ClientSystemUnderTest cut = (ClientSystemUnderTest) clazz.newInstance();
		 logger.info("build instance for " + targetName + " was successfull");
		return cut;
	}
}
