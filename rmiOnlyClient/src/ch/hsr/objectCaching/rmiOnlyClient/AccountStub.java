package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.UnknownHostException;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;

public class AccountStub implements Account {

	private int objectID;
	private IStreamProvider streamProvider;

	public IStreamProvider getStreamProvider() {
		return streamProvider;
	}

	public void setStreamProvider(IStreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}

	String invokeMethodMessage = "<invokeMethod><objectid>23</objectid><methodname>getBalance()</methodname></invokeMethod>";
	private Object[] arguments;

	public double getBalance() {
		try {
			arguments = null;
			String methodName = "getBalance";
			sendMethodCall(methodName);
			ReturnValue retValue = receiveResponse();
			Double i = (Double) retValue.getValue();
			return i;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private ReturnValue receiveResponse() throws IOException,
			ClassNotFoundException {
		ObjectInputStream ois = streamProvider.getObjectInputStream();
		ReturnValue retValue = (ReturnValue) ois.readObject();
		return retValue;
	}

	private void sendMethodCall(String methodName) throws IOException {
		MethodCall methodCall = new MethodCall();
		Method method = getMethod(methodName);
		methodCall.setClassName(Account.class.getName());
		methodCall.setMethodName(methodName);
		methodCall.setParameterTypes(method.getParameterTypes());
		methodCall.setArguments(arguments);
		methodCall.setObjectID(objectID);
		ObjectOutputStream oos = streamProvider.getObjectOutputStream();
		oos.writeObject(methodCall);
		oos.flush();
	}

	public static void main(String[] args) {
		AccountStub account = new AccountStub();
		System.out.println(account.getBalance());
	}

	@Override
	public void setBalance(double balance) {
		arguments = new Object[] { balance };
		try {
			sendMethodCall("setBalance");
			ReturnValue returnValue = receiveResponse();
			if(returnValue.getException() != null){
				throw returnValue.getException();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}

	private Method getMethod(String methodName) {
		Method retVal = null;
		Method[] allMethods = Account.class.getMethods();
		for (Method method : allMethods) {
			if (method.getName().equals(methodName)) {
				retVal = method;
			}
		}
		return retVal;
	}
}
