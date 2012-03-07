package ch.hsr.objectCaching.rmiOnlyServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;


public class Dispatcher {
	
	private ServerSocket server;
	private Class clazz;
	
	public Dispatcher(int port){
		try{
			server = new ServerSocket(port);
			clazz = Class.forName("ch.hsr.objectCaching.rmiOnlyServer.RMIonlyClientHandler");
			
		}
		catch(IOException e){
			System.err.println("cannot listen at port " + port + " (" + e.getMessage() + ")");
		} catch (ClassNotFoundException e) {
			System.out.println("class not found");
			e.printStackTrace();
		}
	}
	
	
	public void accept(){
		Socket socket = null;
		while(true){
			try{
				socket = server.accept();
				InputStream is = socket.getInputStream();
				Class[] paramters = new Class[2];
				paramters[0] = InputStream.class;
				paramters[1] = OutputStream.class;
				Constructor ctor = clazz.getConstructor(paramters); 
				Object[] ctorArgs = new Object[2];
				ctorArgs[0] = socket.getInputStream();
				ctorArgs[1] = socket.getOutputStream();
				Runnable runnable = (Runnable) ctor.newInstance(ctorArgs);
				new Thread(runnable).start();
			}
			catch(IOException e){
				System.err.println("Error creating socket connection");
				System.exit(1);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				System.out.println("no ctor found in RMIonlyClass");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				System.out.println("falsche args an ctor Ÿbergeben");
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
