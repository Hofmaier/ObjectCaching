package ch.hsr.objectCaching.testFrameworkServer;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.hsr.objectCaching.interfaces.MethodCalledListener;

public class MethodCallLogger implements MethodCalledListener 
{
	private Logger logger;
	private FileHandler fileHandler;
	
	public MethodCallLogger(String fileName)
	{
		logger = Logger.getLogger("TestFrameWorkServer");
		try {
			fileHandler = new FileHandler("textLog.txt", false);
			logger.addHandler(fileHandler);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Uncaught exception", e);
		}
	}
	
	@Override
	public void methodCalled(String methodName, String clientIPAddress) 
	{
		logger.addHandler(fileHandler);
		logger.info(methodName + " got invoked by " + clientIPAddress);
	}
}
