package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class StreamProvider implements IStreamProvider {
	
	private Socket socket;
	private InetSocketAddress socketAddress;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream; 

	@Override
	public ObjectOutputStream getObjectOutputStream() {
		try {
			if(objectOutputStream == null){
				initStreams();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectOutputStream;
	}

	private void initStreams() throws UnknownHostException, IOException {
		socket = new Socket();
		socket.connect(socketAddress);
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectInputStream = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	@Override
	public void setSocketAdress(InetSocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}
}
