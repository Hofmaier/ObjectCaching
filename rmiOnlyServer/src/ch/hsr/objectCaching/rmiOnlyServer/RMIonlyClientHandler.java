package ch.hsr.objectCaching.rmiOnlyServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.interfaces.MethodCall;
import ch.hsr.objectCaching.interfaces.ReturnValue;

public class RMIonlyClientHandler extends ClientHandler {

	private RMIonlySkeleton skeletonInUse;
	private AccountSkeleton accountSkeleton;
	private AccountServiceSkeleton accountServiceSkeleton;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private String clientIpAdress;
	
	public void setAccountSkeleton(AccountSkeleton skeleton) {
		this.accountSkeleton = skeleton;
	}

	public RMIonlySkeleton getSkeleton() {
		return skeletonInUse;
	}
	
	@Override
	public void setInputStream(InputStream inputStream) {
		try {
			objectInputStream = new ObjectInputStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		try {
			objectOutputStream = new ObjectOutputStream(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			MethodCall methodCall;
			while(( methodCall = readMethodCallfrom(objectInputStream) )!= null){
			methodCall.setClientIp(clientIpAdress);
			processMethodCall(methodCall);
			}
			objectInputStream.close();
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	MethodCall readMethodCallfrom(ObjectInputStream inputStream) throws IOException,
			ClassNotFoundException {
		Object objectFromStream = null;
		if((objectFromStream = inputStream.readObject()) != null){
		 MethodCall methodCall = (MethodCall) objectFromStream;
		 return methodCall;
		}
		return null;
	}

	 void setSkeleton(MethodCall methodCall) {
		if(methodCall.getClassName().equals(Account.class.getName())){
			skeletonInUse = accountSkeleton;
		}
		if(methodCall.getClassName().equals(AccountService.class.getName())){
			skeletonInUse = accountServiceSkeleton;
		}
	}

	void processMethodCall(MethodCall methodCall) {
		setSkeleton(methodCall);
		ReturnValue returnValue = skeletonInUse.invokeMethod(methodCall);
		sendResponse(returnValue);
	}

	void sendResponse(ReturnValue returnValue) {
		try {
			objectOutputStream.writeObject(returnValue);
			objectOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAccountServiceSkeleton(
			AccountServiceSkeleton accountServiceSkeleton) {
		this.accountServiceSkeleton = accountServiceSkeleton;
	}

	@Override
	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAdress = clientIpAddress;
	}
}
