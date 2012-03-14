package ch.hsr.objectCaching.rmiOnlyClient;

import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;

public class RMIonlyClientSystem implements ClientSystemUnderTest {

	@Override
	public AccountService getAccountService() {
		return null;
	}
}
