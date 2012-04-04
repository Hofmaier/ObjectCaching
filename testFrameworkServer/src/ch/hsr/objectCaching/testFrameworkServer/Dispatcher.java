package ch.hsr.objectCaching.testFrameworkServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.interfaces.ServerSystemUnderTest;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyServerSystem;

public class Dispatcher implements Runnable
{
	private ServerSocket serverSocket;
	ClientHandler clientHandler;
	private ServerSystemUnderTest systemUnderTest;
	
	public void setSystemUnderTest(String system, Account account, MethodCallLogger listener) 
	{
		try {
			//Class clazz = Class.forName(system);
			//Constructor ctor = clazz.getConstructor(new Class[0]);
			//systemUnderTest = (ServerSystemUnderTest) ctor.newInstance(null);
			systemUnderTest = new RMIOnlyServerSystem();
			systemUnderTest.addAccount(account);
			systemUnderTest.addMethodCalledListener(listener);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addAccount(Account account)
	{
		systemUnderTest.addAccount(account);
	}
	
	public Dispatcher(int port)
	{
		try {
			serverSocket = new ServerSocket(port);
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
				Socket socket = serverSocket.accept();
				clientHandler = systemUnderTest.getClientHandlerInstance();
				System.out.println("Client connected");
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				clientHandler.setInputStream(inputStream);
				clientHandler.setOutputStream(outputStream);
				
				clientHandler.setClientIpAddress(socket.getInetAddress().toString());
				new Thread(clientHandler).start();
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
