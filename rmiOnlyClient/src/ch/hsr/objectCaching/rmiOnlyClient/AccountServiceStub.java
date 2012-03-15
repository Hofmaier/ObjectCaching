package ch.hsr.objectCaching.rmiOnlyClient;

import java.util.Collection;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;

public class AccountServiceStub implements AccountService {

	@Override
	public Collection<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

}
