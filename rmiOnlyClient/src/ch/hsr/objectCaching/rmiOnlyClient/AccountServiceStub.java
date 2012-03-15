package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.MethodCall;
import ch.hsr.objectCaching.interfaces.ReturnValue;

public class AccountServiceStub implements AccountService {
	
	private IStreamProvider streamProvider;

	@Override
	public Collection<Account> getAllAccounts() {
		ArrayList<Account> retValCollection = new ArrayList<Account>();
		try {
		sendMethodCall();
			
		ObjectInputStream ois = streamProvider.getObjectInputStream();
		ReturnValue returnValue = (ReturnValue) ois.readObject();
		Collection<Integer> objectIDcollection = (Collection<Integer>) returnValue.getValue(); 
		
		for(Integer i:objectIDcollection){
			retValCollection.add(new AccountStub());
		}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retValCollection;
	}

	private void sendMethodCall() throws IOException {
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(AccountService.class.getName());
		ObjectOutputStream oos = streamProvider.getObjectOutputStream();
			oos.writeObject(methodCall);
			oos.close();
	}

	public void setStreamProvider(IStreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}

}
