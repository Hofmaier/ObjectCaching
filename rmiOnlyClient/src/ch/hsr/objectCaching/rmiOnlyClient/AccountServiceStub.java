package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;

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
			accountStub.setStreamProvider(streamProvider);
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
