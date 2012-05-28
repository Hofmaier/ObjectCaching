package ch.hsr.objectCaching.rmiWithCacheServer;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountImpl;
import ch.hsr.objectCaching.dto.ObjectRequest;
import ch.hsr.objectCaching.dto.ObjectRequestResponse;
import ch.hsr.objectCaching.interfaces.ClientHandler;

public class TestObjectManager {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProcessObjectRequest() {

		int objectID = 23;
		ObjectManager objectManager = new ObjectManager();
		Account account = new AccountImpl();
		objectManager.addAccount(objectID, account);

		ObjectRequest objectRequest = new ObjectRequest();
		objectRequest.setObjectID(objectID);
		ClientHandler clientHandler = new ClientHandlerFake();
		clientHandler.setClientIpAddress("1");
		objectRequest.setClientHandler(clientHandler);
		ObjectRequestResponse response = objectManager.processObjectRequest(objectRequest);

		assertNotNull(response);
		assertNotNull(response.getRequestedObject());
	}
	
	class ClientHandlerFake extends ClientHandler{

		@Override
		public String getClientIpAddress() {
			return "1";
		}
		
	}

}
