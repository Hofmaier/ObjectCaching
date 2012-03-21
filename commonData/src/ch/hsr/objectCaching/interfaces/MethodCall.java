package ch.hsr.objectCaching.interfaces;
import java.io.Serializable;


public class MethodCall implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9029224749086063746L;
	private String methodName;
	private int ObjectID;
	private String className;
	private Class<?>[] parameterTypes;
	private Object[] arguments;
	
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

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

	public void setArguments(Object[] args) {
		arguments = args;
	}
	
	public Object[] getArguments(){
		return arguments;
	}
}