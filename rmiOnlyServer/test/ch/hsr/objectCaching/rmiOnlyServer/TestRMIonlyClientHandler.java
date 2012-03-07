package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class TestRMIonlyClientHandler {

	@Test
	public void testReadMethodCallFrom() throws IOException, ClassNotFoundException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		MethodCall methodCall = new MethodCall();
		methodCall.setMethodName("getBalance");
		methodCall.setClassName("Account");
		
		oos.writeObject(methodCall);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		
		RMIonlyClientHandler clientHandler = new RMIonlyClientHandler(bais);
		MethodCall testCall = clientHandler.readMethodCallfrom(bais);
		
		assertEquals(methodCall.getMethodName(), testCall.getMethodName());
	}
}
