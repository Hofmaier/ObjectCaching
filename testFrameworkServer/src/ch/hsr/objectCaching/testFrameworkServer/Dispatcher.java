package ch.hsr.objectCaching.testFrameworkServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;
import ch.hsr.objectCaching.interfaces.ServerSystemUnderTest;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyServerSystem;

public class Dispatcher implements Runnable
{
	private ServerSocket serverSocket;
	private ClientHandler clientHandler;
	private Logger logger;
	private ServerSystemUnderTest systemUnderTest;
	
	public Dispatcher(int port)
	{
		logger = Logger.getLogger("TestFrameWorkServer");
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		}
	}
	
	public void setSystemUnderTest(String system, Account account, MethodCallLogger listener) 
	{
		
		 Class<?> clazz;
		try {
			clazz = Class.forName(system);
			systemUnderTest = (ServerSystemUnderTest) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		} catch (InstantiationException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		} catch (IllegalAccessException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		}
		 
			
			//systemUnderTest = new RMIOnlyServerSystem();
			systemUnderTest.addAccount(account);
			systemUnderTest.addMethodCalledListener(listener);
			
		
	}
	
	public void addAccount(Account account)
	{
		systemUnderTest.addAccount(account);
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
				logger.log(Level.SEVERE, "Uncaught exception", e);
			}
		}
	}

	@Override
	public void run() {
		accept();
	}
	
}
