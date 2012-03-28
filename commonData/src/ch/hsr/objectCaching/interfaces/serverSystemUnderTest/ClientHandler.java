package ch.hsr.objectCaching.interfaces.serverSystemUnderTest;

import java.io.InputStream;
import java.io.OutputStream;

public class ClientHandler implements Runnable {
	
	protected InputStream inputStream;
	protected OutputStream outputStream;
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public void run() {
		
	}
	
	public void setClientIpAddress(String clientIpAddress){}
	
}
