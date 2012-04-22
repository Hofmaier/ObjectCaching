package ch.hsr.objectCaching.util;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ObjectRequest;

public class TestConcurrencyControl {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUpdateReadVersionOfClient() {
		ConcurrencyControl concurrencyControl = new ConcurrencyControl();
		HashMap<Integer, Integer> writeMap = new HashMap<Integer, Integer>();
		Integer objectID = 23;
		Integer writeVersion = 11;
		String clientIP = "123";
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
		ConcurrencyControl concurrencyControl = new ConcurrencyControl();
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

}
