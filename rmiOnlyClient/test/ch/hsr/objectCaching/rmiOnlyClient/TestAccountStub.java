package ch.hsr.objectCaching.rmiOnlyClient;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.MethodCall;
import ch.hsr.objectCaching.interfaces.ReturnValue;

public class TestAccountStub {
	
	private AccountStub accountStub;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private ByteArrayOutputStream byteArrayOutputStream;

	@Before
	public void setUp() throws Exception {
		accountStub = new AccountStub();
		byteArrayOutputStream = new ByteArrayOutputStream();
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
	}

	@Test
	public void testGetBalance() throws IOException, ClassNotFoundException {
		IStreamProvider streamProvider = new StreamProviderFake();
		accountStub.setStreamProvider(streamProvider);
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(Account.class.getName());
		methodCall.setMethodName("getBalance");
		int objectID = 23;
		methodCall.setObjectID(objectID );
		
		ByteArrayOutputStream byteArrayStreamWithReturnValue = new ByteArrayOutputStream();
		ObjectOutputStream streamToReadbyCUT = new ObjectOutputStream(byteArrayStreamWithReturnValue);
		
		ReturnValue returnValue = new ReturnValue();
		returnValue.setValue(300);
		streamToReadbyCUT.writeObject(returnValue);
		
		objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayStreamWithReturnValue.toByteArray()));
		
		accountStub.getBalance();
		
		ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		MethodCall methodCallFromCUT = (MethodCall) objectInputStream.readObject();
		
		assertNotNull("methodCall was null", methodCallFromCUT);
	}

	@Test
	public void testSetBalance() {
		fail("Not yet implemented");
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
	}
}
