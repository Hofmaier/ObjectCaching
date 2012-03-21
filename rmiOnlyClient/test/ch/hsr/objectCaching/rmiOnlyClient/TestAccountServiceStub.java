package ch.hsr.objectCaching.rmiOnlyClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.MethodCall;
import ch.hsr.objectCaching.interfaces.ReturnValue;

public class TestAccountServiceStub {
	
	private AccountServiceStub accountService;
	private ByteArrayOutputStream byteArrayOutputStream;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

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
		
		ReturnValue returnValue = new ReturnValue();
		Collection<Integer> val = new ArrayList<Integer>();
		int objectID = 23;
		val.add(objectID);
		returnValue.setValue(val);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(output);
		oos.writeObject(returnValue);
		
		objectInputStream = new ObjectInputStream(new ByteArrayInputStream(output.toByteArray()));
		
		Collection<Account> accounts = accountService.getAllAccounts();
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		MethodCall actualMethodCall = (MethodCall) objectInputStream.readObject();
		
		Account accountFromServer = null;
		for(Account account:accounts){
			accountFromServer = account;
		}
		
		assertNotNull("collection is null", accounts);
		assertEquals(AccountService.class.getName(), actualMethodCall.getClassName());
		assertNotNull("account was null", accountFromServer);
		
		AccountStub accountStub = (AccountStub) accountFromServer;
		assertEquals(objectID, accountStub.getID());
		assertTrue(streamProviderfake == accountStub.getStreamProvider());
	}
	
	class StreamProviderFake implements IStreamProvider{

		@Override
		public ObjectOutputStream getObjectOutputStream() {
			return objectOutputStream;
		}

		@Override
		public ObjectInputStream getObjectInputStream() {
			return objectInputStream;
		}

		@Override
		public void setSocketAdress(InetSocketAddress socketAdress) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
