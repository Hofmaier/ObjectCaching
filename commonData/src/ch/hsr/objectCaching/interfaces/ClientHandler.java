package ch.hsr.objectCaching.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ClientHandler implements Runnable {
	
	protected InputStream inputStream;
	protected OutputStream outputStream;
	protected ObjectOutputStream objectOutputStream;
	protected ObjectInputStream objectInputStream;
	protected boolean isActiv;
	

	@Override
	public void run() {
		
	}
	
	public void setClientIpAddress(String clientIpAddress){}
	public String getClientIpAddress() {return null;}

	public synchronized void send(Object object) {
		if(isActiv){
		try {
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			objectOutputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}	
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
