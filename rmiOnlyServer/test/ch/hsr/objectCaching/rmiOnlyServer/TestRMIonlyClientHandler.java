package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

public class TestRMIonlyClientHandler {
	
	private RMIonlyClientHandler clientHandler;
	private ByteArrayOutputStream byteArrayOutputStream;
	private ByteArrayInputStream byteArrayInputStream;

	@Before
	public void setUp(){
		
		byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		clientHandler = new RMIonlyClientHandler();
		clientHandler.setInputStream(byteArrayInputStream);
		clientHandler.setOutputStream(byteArrayOutputStream);
	}

	@Test
	public void testReadMethodCallFrom() throws IOException, ClassNotFoundException {
		
		MethodCall methodCall = new MethodCall();
		methodCall.setMethodName("getBalance");
		methodCall.setClassName("Account");
		
		ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
		oos.writeObject(methodCall);
		byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		MethodCall testCall = clientHandler.readMethodCallfrom(byteArrayInputStream);
		
		assertEquals(methodCall.getMethodName(), testCall.getMethodName());
	}
	
	
	@Test
	public void testSetSkeleton(){
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName("Account");
		clientHandler.setSkeleton(methodCall);
		assertTrue(clientHandler.getSkeleton() instanceof AccountSkeleton);
	}
	
	@Test
	public void testSendResponse() throws IOException, ClassNotFoundException{
		ReturnValue returnValue = new ReturnValue();
		returnValue.setValue(new Integer(200));
		clientHandler.sendResponse(returnValue);
		byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
		ReturnValue retValFromStream = (ReturnValue) ois.readObject();
		assertEquals(returnValue.getValue(), retValFromStream.getValue());
		
	}
	
	@Test
	public void testProcessMethodCall() throws IOException, ClassNotFoundException{
		AccountSkeletonFake fakeSkeleton = new AccountSkeletonFake();
		Integer expectedValue = 200;
		fakeSkeleton.setIntValue(expectedValue);
		MethodCall dummyMethodCall = new MethodCall();
		dummyMethodCall.setClassName("Account");
		clientHandler.setAccountSkeleton(fakeSkeleton);
		clientHandler.processMethodCall(dummyMethodCall);
		
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		ReturnValue retVal =  (ReturnValue) ois.readObject();
		assertEquals(expectedValue, retVal.getValue());
	}
	
	public class AccountSkeletonFake extends AccountSkeleton{

		private Integer intValue;

		public void setIntValue(Integer intValue) {
			this.intValue = intValue;
		}

		@Override
		public ReturnValue invokeMethod(MethodCall methodCall) {
			ReturnValue returnValue = new ReturnValue();
			returnValue.setValue(intValue);
			return returnValue;
		}
		
	}

}
