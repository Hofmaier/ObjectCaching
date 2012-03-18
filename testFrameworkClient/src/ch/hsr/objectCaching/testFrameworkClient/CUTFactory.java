package ch.hsr.objectCaching.testFrameworkClient;

import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;
import ch.hsr.objectCaching.rmiOnlyClient.RMIonlyClientSystem;

public class CUTFactory {

	public static ClientSystemUnderTest generateCUT(String targetName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//		Class<?> clazz = Class.forName(targetName);
//		ClientSystemUnderTest cut = (ClientSystemUnderTest) clazz.newInstance();
		ClientSystemUnderTest cut = new RMIonlyClientSystem();
		return cut;
	}
}
