package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class StreamProvider implements IStreamProvider {
	
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream; 

	@Override
	public ObjectOutputStream getObjectOutputStream() {
		try {
			socket = new Socket("localhost", 12345);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectOutputStream;
	}

	@Override
	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}
}
