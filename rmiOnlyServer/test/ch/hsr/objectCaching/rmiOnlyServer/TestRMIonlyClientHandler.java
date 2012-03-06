package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class TestRMIonlyClientHandler {

	@Test
	public void testReadMethodCallFrom() throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		
		
		fail("Not yet implemented");
	}

}
