package ch.hsr.objectCaching.rmiOnlyServer;

import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountService;

public class AccountServiceImpl implements AccountService{
	
	private Collection<Account> accounts = new ArrayList<Account>();
	
	public Collection<Account> getAllAccounts(){
		return accounts;
	} 
	
	public void addAccount(Account account){
		accounts.add(account);
	}
}
