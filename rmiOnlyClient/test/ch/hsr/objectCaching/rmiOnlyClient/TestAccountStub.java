package ch.hsr.objectCaching.rmiOnlyClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

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
	private int objectID = 23;
	private Method setBalanceMethod;

	@Before
	public void setUp() throws Exception {
		accountStub = new AccountStub();
		byteArrayOutputStream = new ByteArrayOutputStream();
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		IStreamProvider streamProvider = new StreamProviderFake();
		accountStub.setStreamProvider(streamProvider);
		initMethods();
	}

	@Test
	public void testGetBalance() throws IOException, ClassNotFoundException {
		
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(Account.class.getName());
		String methodName = "getBalance";
		methodCall.setMethodName(methodName);
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
		assertEquals(Account.class.getName(), methodCallFromCUT.getClassName());
		assertEquals(methodName, methodCallFromCUT.getMethodName());
		
	}

	@Test
	public void testSetBalance() throws IOException, ClassNotFoundException {
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(Account.class.getName());
		String methodName = "setBalance";
		methodCall.setMethodName(methodName);
		methodCall.setObjectID(objectID );
		
		ByteArrayOutputStream byteArrayStreamWithReturnValue = new ByteArrayOutputStream();
		ObjectOutputStream streamToReadbyCUT = new ObjectOutputStream(byteArrayStreamWithReturnValue);
		
		ReturnValue returnValue = new ReturnValue();
		streamToReadbyCUT.writeObject(returnValue);
		
		objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayStreamWithReturnValue.toByteArray()));
		Class<?>[] parameterTypes = setBalanceMethod.getParameterTypes();
		accountStub.setBalance(220);
		
		ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		MethodCall methodCallFromCUT = (MethodCall) objectInputStream.readObject();
		
		assertNotNull("methocall setBalance was null", methodCallFromCUT);
		assertEquals(methodName, methodCallFromCUT.getMethodName());
		Class<?>[] paramterTypesFromCUT = methodCallFromCUT.getParameterTypes();
		assertNotNull(paramterTypesFromCUT);
		int i = 0;
		for(Class<?> paramter:parameterTypes){
			assertEquals(paramter, paramterTypesFromCUT[i++]);
		}
		
		assertNotNull(methodCallFromCUT.getArguments());
	}
	
	private void initMethods() {
		Method[] allMethods = Account.class.getMethods();
		for(Method method:allMethods){
			if(method.getName().equals("getBalance")){
			}
			if(method.getName().equals("setBalance")){
				setBalanceMethod = method;
			}
		}
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
