package ch.hsr.objectCaching.util;

import java.util.HashMap;

import ch.hsr.objectCaching.dto.MethodCall;

public class ConcurrencyControl {

	private HashMap<Integer, Boolean> invalidateMap = new HashMap<Integer, Boolean>();
	private HashMap<Integer, Integer> writeMap = new HashMap<Integer, Integer>();
	private HashMap<String, Integer> readMap = new HashMap<String, Integer>();

	public ConcurrencyControl() {
	}

	public void processMethod(MethodCall methodCall) {
		if (methodCall.getMethodName().equals("setBalance")) {
		}
	}

	public boolean isObjectInvalid(int objectID) {
		if (invalidateMap.containsKey(objectID)) {
			return invalidateMap.get(objectID).booleanValue();
		}
		return false;
	}

	public void invalidateObject(int objectID) {
		invalidateMap.put(objectID, true);
	}

	// TODO read the current version of the object from the server. Readmap key
	// is a String instead of a Integer
	public void addObject(int objectID) {
		invalidateMap.put(objectID, false);
		// readMap.put(objectID, 0);
		writeMap.put(objectID, 0);
	}

	public void setObjectRead(int objectID) {
		int currentVersion = writeMap.get(objectID).intValue();
		if (readMap.get(objectID).intValue() != currentVersion) {
			//readMap.put(objectID, currentVersion);
		}
	}

	public void updateReadVersionOfClient(String ip, Integer objectID) {
		String readKey = generateReadMapKey(ip, objectID);
		Integer version = writeMap.get(objectID);
		if(version == null){
			version = 0;
			writeMap.put(objectID, version);
		}
		readMap.put(readKey, version);
	}

	private String generateReadMapKey(String ip, Integer objectID) {
		return ip.concat(String.valueOf(objectID));
	}

	public HashMap<String, Integer> getReadMap() {
		return readMap;
	}

	public void setReadMap(HashMap<String, Integer> readMap) {
		this.readMap = readMap;
	}

	public HashMap<Integer, Integer> getWriteMap() {
		return writeMap;
	}

	public void setWriteMap(HashMap<Integer, Integer> writeMap) {
		this.writeMap = writeMap;
	}

	public boolean isWriteConsistent(Integer objectID, String clientIP) {
		Integer actualClientVersion = readMap.get(generateReadMapKey(clientIP, objectID));
		Integer actualServerVersion = writeMap.get(objectID);
		return !(actualClientVersion < actualServerVersion);
	}

	public void updateWriteVersion(Integer objectID) {
		Integer globalWriteVersion = writeMap.get(objectID);
		globalWriteVersion++;
		writeMap.put(objectID, globalWriteVersion);
	}
}
