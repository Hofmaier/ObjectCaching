package ch.hsr.objectCaching.rmiOnlyServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RMIonlyClientHandler implements Runnable {

	private Socket socket;
	private RMIonlySkeleton skeleton;
	private InputStream inputStream;
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public RMIonlyClientHandler(InputStream inputStream){
		this.inputStream = inputStream;
	}

	@Override
	public void run() {
		try {
			inputStream = socket.getInputStream();
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

	private void processMethodCall(MethodCall methodCall) {
		//hier soll der richtige Skeleton ausgewählt werden
		setSkeleton(methodCall);
		ReturnValue returnValue = skeleton.invokeMethod(methodCall);
		sendResponse(returnValue);
	}

	private void sendResponse(ReturnValue returnValue) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(returnValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
