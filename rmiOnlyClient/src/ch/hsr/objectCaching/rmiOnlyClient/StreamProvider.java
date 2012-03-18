package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class StreamProvider implements IStreamProvider {
	
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream; 

	@Override
	public ObjectOutputStream getObjectOutputStream() {
		
			if(socket == null){
			initSocket();
			}
			return objectOutputStream;
	}

	private void initSocket() {
		try {
			socket = new Socket("localhost", 12345);
		objectInputStream = new ObjectInputStream(socket.getInputStream());
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ObjectInputStream getObjectInputStream() {
	
		if(socket == null){
				initSocket();
		}
		return objectInputStream;
	}

}
