package ch.hsr.objectCaching.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TestConcurrencyControl {

	private ConcurrencyControl concurrencyControl;
	private Integer objectID = 23;
	private String clientIP = "123";

	@Before
	public void setUp() throws Exception {
		concurrencyControl = new ConcurrencyControl();
	}

	@Test
	public void testUpdateReadVersionOfClient() {
		HashMap<Integer, Integer> writeMap = new HashMap<Integer, Integer>();
		Integer writeVersion = 11;
		writeMap.put(objectID, writeVersion);
		concurrencyControl.setWriteMap(writeMap);
		HashMap<String, Integer> readMap = new HashMap<String, Integer>();
		concurrencyControl.setReadMap(readMap);
		concurrencyControl.updateReadVersionOfClient(clientIP, objectID);
		String versionKey = clientIP.concat(String.valueOf(objectID));
		assertEquals(writeVersion, readMap.get(versionKey));
	}

	@Test
	public void testUpdateReadVersionOfClientWithObjectRequest() {
		HashMap<Integer, Integer> writeMap = new HashMap<Integer, Integer>();
		HashMap<String, Integer> readMap = new HashMap<String, Integer>();

		Integer objectID = 43;
		String clientIP = "1234";
		Integer resultVersion = 0;

		concurrencyControl.setWriteMap(writeMap);
		concurrencyControl.setReadMap(readMap);
		concurrencyControl.updateReadVersionOfClient(clientIP, objectID);
		String versionKey = clientIP.concat(String.valueOf(objectID));
		assertEquals(resultVersion, readMap.get(versionKey));

	}
	
	@Test
	public void testIsWriteConsistent(){
		concurrencyControl.updateReadVersionOfClient(clientIP, objectID);
		assertTrue(concurrencyControl.isWriteConsistent(objectID, clientIP));
		Integer version = concurrencyControl.getWriteMap().get(objectID);
		concurrencyControl.getWriteMap().put(objectID, ++version);
		assertTrue(!concurrencyControl.isWriteConsistent(objectID, clientIP));
		
	}
	
	@Test
	public void testUpdateWriteVersion(){
		concurrencyControl.updateReadVersionOfClient(clientIP, objectID);
		concurrencyControl.updateWriteVersion(objectID);
		assertTrue(!concurrencyControl.isWriteConsistent(objectID, clientIP));
	}

}
