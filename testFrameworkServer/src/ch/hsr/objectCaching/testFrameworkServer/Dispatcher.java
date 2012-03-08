package ch.hsr.objectCaching.testFrameworkServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.interfaces.SystemUnderTest;

public class Dispatcher implements Runnable
{
	private ServerSocket server;
	ClientHandler clientHandler;
	private SystemUnderTest systemUnderTest;
	
	public void setSystemUnderTest(String system)
	{
		try {
			Class clazz = Class.forName(system);
			Constructor ctor = clazz.getConstructor(new Class[0]);
			systemUnderTest = (SystemUnderTest) ctor.newInstance(null);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
				clientHandler = systemUnderTest.getClientHandlerInstance();
				System.out.println("Client connected");
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				clientHandler.setInputStream(inputStream);
				clientHandler.setOutputStream(outputStream);
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