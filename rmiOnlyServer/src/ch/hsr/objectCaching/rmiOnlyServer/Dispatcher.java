package ch.hsr.objectCaching.rmiOnlyServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Dispatcher {
	
	private ServerSocket server;
	
	public Dispatcher(int port){
		try{
			server = new ServerSocket(port);
		}
		catch(IOException e){
			System.err.println("cannot listen at port " + port + " (" + e.getMessage() + ")");
		}
	}
	
	
	public void accept(){
		Socket socket = null;
		while(true){
			try{
				socket = server.accept();
				new Thread(new ClientHandler(socket)).start();
				
			}
			catch(IOException e){
				System.err.println("Error creating socket connection");
				System.exit(1);
			}
		}
	}

}
