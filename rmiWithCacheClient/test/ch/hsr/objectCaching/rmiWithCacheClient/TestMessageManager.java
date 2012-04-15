package ch.hsr.objectCaching.rmiWithCacheClient;

import static org.junit.Assert.assertEquals;

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
import ch.hsr.objectCaching.dto.ObjectRequest;
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
		SenderThread sender = new SenderThread(messageManager);
		new Thread(sender).start();
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
		messageManager.setStreamProvider(streamProvider);
		ObjectRequest request = new ObjectRequest();
		int objectID = 3;
		request.setObjectID(objectID);
		objectOutputStream.writeObject(request);
		objectOutputStream.close();
		objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		ReceiverThread receiver = new ReceiverThread(messageManager);
		new Thread(receiver).start();
		ObjectRequest actualRequest = (ObjectRequest) messageManager.receiveObject();
		
		assertEquals(objectID, actualRequest.getObjectID());
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
			
		}
	}
}
