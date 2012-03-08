package ch.hsr.objectCaching.interfaces;

import java.io.InputStream;
import java.io.OutputStream;

public class ClientHandler implements Runnable {
	
	InputStream inputStream;
	OutputStream outputStream;
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
}
