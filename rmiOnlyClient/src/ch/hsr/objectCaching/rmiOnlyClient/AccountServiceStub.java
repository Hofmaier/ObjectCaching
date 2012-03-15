package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.MethodCall;

public class AccountServiceStub implements AccountService {
	
	private IStreamProvider streamProvider;

	@Override
	public Collection<Account> getAllAccounts() {
		MethodCall methodCall = new MethodCall();
		methodCall.setClassName(AccountService.class.getName());
		ObjectOutputStream oos = streamProvider.getObjectOutputStream();
		try {
			oos.writeObject(methodCall);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<Account>();
	}

	public void setStreamProvider(IStreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}

}
