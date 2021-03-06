package ch.hsr.objectCaching.util;

import java.util.HashMap;

import ch.hsr.objectCaching.dto.MethodCall;

public class ConcurrencyControl {

	private HashMap<Integer, Integer> writeMap = new HashMap<Integer, Integer>();
	private HashMap<String, Integer> readMap = new HashMap<String, Integer>();

	public ConcurrencyControl() {
	}

	public void processMethod(MethodCall methodCall) {
		if (methodCall.getMethodName().equals("setBalance")) {
		}
	}
	
	public void updateReadVersionOfClient(Integer objectID){
		updateReadVersionOfClient("", objectID);
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
	
	public boolean isWriteConsistent(Integer objectID){
		return isWriteConsistent(objectID, "");
	}
	
	public boolean isWriteConsistent(Integer objectID, String clientIP) {
		Integer actualClientVersion = readMap.get(generateReadMapKey(clientIP, objectID));
		Integer actualServerVersion = writeMap.get(objectID);
		System.out.println("[CLIENT] isConsistentCheck: clientVersion: " + actualClientVersion + " serverVersion: " + actualServerVersion);
		return !(actualClientVersion < actualServerVersion);
	}

	public void updateWriteVersion(Integer objectID) {
		Integer globalWriteVersion = writeMap.get(objectID);
		globalWriteVersion++;
		writeMap.put(objectID, globalWriteVersion);
	}

	public void updateWriteVersion(int objectID, int objectVersion) {
		writeMap.put(objectID, objectVersion);
	}

	public boolean isWriteConsistent(MethodCall methodCall) {
		Integer actualServerVersion = writeMap.get(methodCall.getObjectID());
		return !(actualServerVersion > methodCall.getObjectVersion());
	}

}
