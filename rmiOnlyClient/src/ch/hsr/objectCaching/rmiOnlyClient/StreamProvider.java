package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class StreamProvider implements IStreamProvider {
	
	private Socket socket;

	@Override
	public ObjectOutputStream getObjectOutputStream() {
		try {
			if(socket == null){
			initSocket();
			}
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			return oos;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void initSocket() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 12345);
	}

	@Override
	public ObjectInputStream getObjectInputStream() {
		try {
		if(socket == null){
				initSocket();
		}
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				return ois;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}

}
