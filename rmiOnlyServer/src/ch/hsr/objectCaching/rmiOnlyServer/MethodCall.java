package ch.hsr.objectCaching.rmiOnlyServer;
import java.io.Serializable;
import java.util.List;


public class MethodCall implements Serializable {
	
	private String methodName;
	private int ObjectID;
	private String className;
	
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
	
	public void setObjectID(int objectID){
		ObjectID = objectID;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	private List<String> arguments;

}
