package ch.hsr.objectCaching.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import ch.hsr.objectCaching.dto.Update;

public class ClientHandler implements Runnable {
	
	protected InputStream inputStream;
	protected OutputStream outputStream;
	protected ObjectOutputStream objectOutputStream;
	protected ObjectInputStream objectInputStream;
	

	@Override
	public void run() {
		
	}
	
	public void setClientIpAddress(String clientIpAddress){}

	public void send(Object object) {
		try {
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void setInputStream(InputStream inputStream) {
		try {
			objectInputStream = new ObjectInputStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		try {
			objectOutputStream = new ObjectOutputStream(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
