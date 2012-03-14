package ch.hsr.objectCaching.rmiOnlyServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ch.hsr.objectCaching.interfaces.ClientHandler;

public class RMIonlyClientHandler extends ClientHandler {

	private RMIonlySkeleton skeletonInUse;
	private AccountSkeleton accountSkeleton;
	private AccountServiceSkeleton accountServiceSkeleton;
	
	public void setAccountSkeleton(RMIonlySkeleton skeleton) {
		this.skeletonInUse = skeleton;
	}

	public RMIonlySkeleton getSkeleton() {
		return skeletonInUse;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	@Override
	public void run() {
		try {
			
			MethodCall methodCall = readMethodCallfrom(inputStream);
			
			processMethodCall(methodCall);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			//toDo: send Errormessage to Client if class is unknown
			e.printStackTrace();
		}
	}

	MethodCall readMethodCallfrom(InputStream inputStream) throws IOException,
			ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(inputStream);
		MethodCall methodCall = (MethodCall) ois.readObject();
		return methodCall;
	}

	 void setSkeleton(MethodCall methodCall) {
		if(methodCall.getClassName().equals("Account")){
			skeletonInUse = new AccountSkeleton();
		}
	}

	void processMethodCall(MethodCall methodCall) {
		setSkeleton(methodCall);
		ReturnValue returnValue = skeletonInUse.invokeMethod(methodCall);
		sendResponse(returnValue);
	}

	void sendResponse(ReturnValue returnValue) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(returnValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
