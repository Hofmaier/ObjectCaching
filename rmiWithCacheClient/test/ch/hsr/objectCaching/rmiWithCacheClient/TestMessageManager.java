package ch.hsr.objectCaching.rmiWithCacheClient;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;
import ch.hsr.objectCaching.rmiOnlyClient.IStreamProvider;

public class TestMessageManager {
	
	private ByteArrayOutputStream byteArrayOutputStream;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private MessageManager messageManager;
	private IStreamProvider streamProvider;

	@Before
	public void setUp() throws Exception {
		byteArrayOutputStream = new ByteArrayOutputStream();
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		messageManager = new MessageManager();
		streamProvider = new StreamProviderFake();
	}

	@Test
	public void testSendMessageCall() throws InterruptedException, IOException, ClassNotFoundException {
		messageManager.setStreamProvider(streamProvider);
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(AccountService.class.getName());
		messageManager.sendMessageCall(methodCall);
		Thread.sleep(500);
		ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		MethodCall returnValue = (MethodCall) objectInputStream.readObject();
		assertEquals(AccountService.class.getName(), returnValue.getClassName());
	}
	
	

	@Test
	public void testReceiveMethodCallResponse() throws IOException, ClassNotFoundException {
		ReturnValue returnValue = new ReturnValue();
		Double balanceAmount = 200.0;
		returnValue.setValue(balanceAmount);
		objectOutputStream.writeObject(returnValue);
		objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		
		messageManager.setStreamProvider(streamProvider);
		ReturnValue actualReturnValue = messageManager.receiveMethodCallResponse();
		assertEquals(balanceAmount, actualReturnValue);
	}
	
	class StreamProviderFake implements IStreamProvider{

		@Override
		public ObjectOutputStream getObjectOutputStream() {
			return objectOutputStream;
		}

		@Override
		public ObjectInputStream getObjectInputStream() {
			return null;
		}

		@Override
		public void setSocketAdress(InetSocketAddress socketAdress) {
			// TODO Auto-generated method stub
			
		}
		
	}


}
