package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.interfaces.serverSystemUnderTest.MethodCall;
import ch.hsr.objectCaching.interfaces.serverSystemUnderTest.ReturnValue;

public class TestRMIonlyClientHandler {
	
	private RMIonlyClientHandler clientHandler;
	private ByteArrayOutputStream byteArrayOutputStream;
	private ByteArrayInputStream byteArrayInputStream;
	private AccountSkeletonFake fakeAccountSkeleton;
	private MethodCall methodCall;

	@Before
	public void setUp(){
		
		byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		clientHandler = new RMIonlyClientHandler();
		clientHandler.setInputStream(byteArrayInputStream);
		clientHandler.setOutputStream(byteArrayOutputStream);
		methodCall = new MethodCall();
	}

	@Test
	public void testReadMethodCallFrom() throws IOException, ClassNotFoundException {
		
		methodCall.setMethodName("getBalance");
		methodCall.setClassName("Account");
		byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
		oos.writeObject(methodCall);
		oos.close();
		byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		clientHandler.setInputStream(byteArrayInputStream);
		MethodCall testCall = clientHandler.readMethodCallfrom(null);
		
		assertEquals(methodCall.getMethodName(), testCall.getMethodName());
	}
	
	
	@Test
	public void testSetSkeleton(){
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(Account.class.getName());
		clientHandler.setAccountSkeleton(new AccountSkeleton());
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
		fakeAccountSkeleton = new AccountSkeletonFake();
		Integer valueForAccount = 200;
		fakeAccountSkeleton.setIntValue(valueForAccount);
		clientHandler.setAccountSkeleton(fakeAccountSkeleton);
		methodCall.setClassName(Account.class.getName());
		clientHandler.processMethodCall(methodCall);
		
		AccountServiceSkeletonFake fakeService = new AccountServiceSkeletonFake();
		int valueForService = 230;
		fakeService.setIntValue(valueForService);
		clientHandler.setAccountServiceSkeleton(fakeService);
		methodCall.setClassName(AccountService.class.getName());
		clientHandler.processMethodCall(methodCall);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		ReturnValue retValForAccount =  (ReturnValue) ois.readObject();
		ReturnValue retValForService = (ReturnValue) ois.readObject();
		assertEquals(valueForAccount, retValForAccount.getValue());
		assertEquals(valueForService, retValForService.getValue());
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
	
	public class AccountServiceSkeletonFake extends AccountServiceSkeleton{
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
