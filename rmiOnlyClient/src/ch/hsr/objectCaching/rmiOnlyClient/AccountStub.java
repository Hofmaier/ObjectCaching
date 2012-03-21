package ch.hsr.objectCaching.rmiOnlyClient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.MethodCall;
import ch.hsr.objectCaching.interfaces.ReturnValue;



public class AccountStub implements Account{

	private int objectID;
	private IStreamProvider streamProvider;
	
	public IStreamProvider getStreamProvider() {
		return streamProvider;
	}

	public void setStreamProvider(IStreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}

	String invokeMethodMessage = "<invokeMethod><objectid>23</objectid><methodname>getBalance()</methodname></invokeMethod>";
	
	public int getBalance(){
		try {
			String methodName = "getBalance";
			sendMethodCall(methodName);
			ReturnValue retValue = receiveResponse();
			Integer i = (Integer) retValue.getValue();
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
		methodCall.setClassName(Account.class.getName());
		methodCall.setMethodName(methodName);
		methodCall.setObjectID(objectID);
		ObjectOutputStream oos = streamProvider.getObjectOutputStream();
		oos.writeObject(methodCall);
		oos.flush();
	}
	
	public static void main(String[] args){
		AccountStub account = new AccountStub();
		System.out.println(account.getBalance()); 
	}

	@Override
	public void setBalance(int balance) {
		try {
			sendMethodCall("setBalance");
			try {
				receiveResponse();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
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
	
}
