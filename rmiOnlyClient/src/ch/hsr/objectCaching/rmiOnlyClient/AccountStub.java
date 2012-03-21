package ch.hsr.objectCaching.rmiOnlyClient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.MethodCall;
import ch.hsr.objectCaching.interfaces.ReturnValue;



public class AccountStub implements Account{

	private int balance;
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
			MethodCall methodCall = new MethodCall();
			methodCall.setClassName(Account.class.getName());
			methodCall.setMethodName("getBalance");
			methodCall.setObjectID(objectID);
			ObjectOutputStream oos = streamProvider.getObjectOutputStream();
			oos.writeObject(methodCall);
			oos.flush();
			ObjectInputStream ois = streamProvider.getObjectInputStream();
			ReturnValue retValue = (ReturnValue) ois.readObject();
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
	
	public static void main(String[] args){
		AccountStub account = new AccountStub();
		System.out.println(account.getBalance()); 
	}

	@Override
	public void setBalance(int balance) {
		
	}

	public int getID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}
	
}
