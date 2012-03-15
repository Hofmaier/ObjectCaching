package ch.hsr.objectCaching.rmiOnlyClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.MethodCall;

public class TestAccountServiceStub {
	
	private AccountServiceStub accountService;
	private ByteArrayOutputStream byteArrayOutputStream;
	private ObjectOutputStream objectOutputStream;

	@Before
	public void setUp() throws Exception {
		accountService = new AccountServiceStub();
		
		byteArrayOutputStream = new ByteArrayOutputStream();
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
	}

	@Test
	public void testGetAllAccounts() throws IOException, ClassNotFoundException {
		IStreamProvider streamProviderfake = new StreamProviderFake();
		accountService.setStreamProvider(streamProviderfake);
		
		
		
		Collection<Account> accounts = accountService.getAllAccounts();
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		MethodCall actualMethodCall = (MethodCall) objectInputStream.readObject();
		
		Account accountFromServer;
		for(Account account:accounts){
			accountFromServer = account;
		}
		
		
		assertNotNull("collection is null", accounts);
		assertEquals(AccountService.class.getName(), actualMethodCall.getClassName());
	}
	
	class StreamProviderFake implements IStreamProvider{

		@Override
		public ObjectOutputStream getObjectOutputStream() {
			return objectOutputStream;
		}
		
	}

}
