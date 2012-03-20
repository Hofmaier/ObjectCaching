package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
		try {
		sendMethodCall();
		ReturnValue returnValue = receiveMethodCall();
		return composeCollection(returnValue);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Collection<Account> composeCollection(ReturnValue returnValue) {
		ArrayList<Account> retValCollection = new ArrayList<Account>();
		Collection<Integer> objectIDcollection = (Collection<Integer>) returnValue.getValue(); 
		
		for(Integer i:objectIDcollection){
			AccountStub accountStub = new AccountStub();
			accountStub.setObjectID(i);
			retValCollection.add(accountStub);
		}
		return retValCollection;
	}

	private ReturnValue receiveMethodCall() throws IOException,
			ClassNotFoundException {
		ObjectInputStream ois = streamProvider.getObjectInputStream();
		ReturnValue returnValue = (ReturnValue) ois.readObject();
		return returnValue;
	}

	private void sendMethodCall() throws IOException {
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(AccountService.class.getName());
		ObjectOutputStream oos = streamProvider.getObjectOutputStream();
		oos.writeObject(methodCall);
		oos.flush();
	}

	public void setStreamProvider(IStreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}

	public IStreamProvider getStreamProvider() {
		return streamProvider;
	}
}
