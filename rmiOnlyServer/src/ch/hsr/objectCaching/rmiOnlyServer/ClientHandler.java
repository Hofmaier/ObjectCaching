package ch.hsr.objectCaching.rmiOnlyServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ClientHandler implements Runnable {

	private Socket socket;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			//SAXParserFactory factory = SAXParserFactory.newInstance();
			//SAXParser parser = factory.newSAXParser();
			//MethodCallHandler handler = new MethodCallHandler();
			//parser.parse(is, handler);
			ObjectInputStream ois = new ObjectInputStream(is);
			
			MethodCall methodCall = (MethodCall) ois.readObject();
			processMethodCall(methodCall);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void processMethodCall(MethodCall methodCall) {
		//hier soll der richtige Skeleton ausgewählt werden
		AccountSkeleton skeleton = new AccountSkeleton();
		String returnValue = skeleton.invokeMethod(methodCall);
		sendResponse(returnValue);
	}

	private void sendResponse(String returnValue) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(returnValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
