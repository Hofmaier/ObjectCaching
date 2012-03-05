package ch.hsr.objectCaching.rmiOnlyClient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;



public class AccountStub {

	private int balance;
	
	String invokeMethodMessage = "<invokeMethod><objectid>23</objectid><methodname>getBalance()</methodname></invokeMethod>";
	
	public int getBalance(){
		Socket socket;
		String retValue = "";
		try {
			socket = new Socket("localhost", 12345);
			//PrintWriter out = new PrintWriter(socket.getOutputStream());
			//out.println(invokeMethodMessage);
			MethodCall methodCall = new MethodCall();
			methodCall.setMethodName("getbalance");
			methodCall.setObjectID("23");
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(methodCall);
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			retValue = (String) ois.readObject();
			oos.close();
			ois.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return Integer.parseInt(retValue);
	}
	
	public static void main(String[] args){
		AccountStub account = new AccountStub();
		System.out.println(account.getBalance()); 
	}
	
}
