package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
		clientHandler = new RMIonlyClientHandler(byteArrayInputStream, byteArrayOutputStream);
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
		
	}
}
