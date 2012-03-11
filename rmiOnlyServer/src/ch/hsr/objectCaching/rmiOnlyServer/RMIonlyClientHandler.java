package ch.hsr.objectCaching.rmiOnlyServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import ch.hsr.objectCaching.interfaces.ClientHandler;

public class RMIonlyClientHandler extends ClientHandler {

	private RMIonlySkeleton skeleton;
	
	public void setSkeleton(RMIonlySkeleton skeleton) {
		this.skeleton = skeleton;
	}

	public RMIonlySkeleton getSkeleton() {
		return skeleton;
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
			skeleton = new AccountSkeleton();
		}
	}

	void processMethodCall(MethodCall methodCall) {
		setSkeleton(methodCall);
		ReturnValue returnValue = skeleton.invokeMethod(methodCall);
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
