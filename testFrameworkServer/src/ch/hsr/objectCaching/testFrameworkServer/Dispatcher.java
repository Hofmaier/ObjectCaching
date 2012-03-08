package ch.hsr.objectCaching.testFrameworkServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Dispatcher implements Runnable
{
	private ServerSocket server;
	public Dispatcher(int port)
	{
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void accept()
	{
		while(true)
		{
			try {
				Socket socket = server.accept();
				System.out.println("Client connected");
				InputStream inputStream = socket.getInputStream();
				System.out.println(inputStream.read());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		accept();
		
	}
	
}
