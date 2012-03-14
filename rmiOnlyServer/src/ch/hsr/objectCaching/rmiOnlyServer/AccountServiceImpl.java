package ch.hsr.objectCaching.rmiOnlyServer;

import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;

public class AccountServiceImpl implements AccountService{
	
	private Collection<Account> accounts = new ArrayList<Account>();
	private AccountSkeleton accountSkeleton;
	
	public Collection<Account> getAllAccounts(){
		return accounts;
	} 
	
	public void addAccount(Account account){
		accounts.add(account);
	}

	public void setAccountSkeleton(AccountSkeleton accountSkeleton) {
		this.accountSkeleton = accountSkeleton;
	}

}
