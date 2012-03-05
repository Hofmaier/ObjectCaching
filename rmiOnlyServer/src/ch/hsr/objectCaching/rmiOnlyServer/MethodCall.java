package ch.hsr.objectCaching.rmiOnlyServer;
import java.io.Serializable;
import java.util.List;


public class MethodCall implements Serializable {
	
	private String methodName;
	private int ObjectID;
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getObjectID() {
		return ObjectID;
	}

	public void setObjectID(String objectID) {
		ObjectID = Integer.parseInt(objectID);
	}

	private List<String> arguments;

}
