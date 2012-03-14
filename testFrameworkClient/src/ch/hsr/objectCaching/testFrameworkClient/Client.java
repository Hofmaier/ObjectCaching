package ch.hsr.objectCaching.testFrameworkClient;

import java.rmi.Naming;
import java.util.Collection;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;

public class Client implements ClientUnderTest{

	private static final int SERVER_PORT = 1999;
	private static final String ACCOUNT_SEVICE = "AccountService";

	private AccountService accountService;
	private Collection<Account> accountList;
	private Account account;

	public Client() {
		init();
	}

	private void init() {
		accountList = accountService.getAllAccountObjects();
		loadAccountService("152.96.193.9");
	}

	// TODO Accounts loopen einbauen
	private Account getNextAccount() {
		if (account == null) {
			account = accountList.iterator().next();
		}
		return account;
	}

	private void loadAccountService(String serverIP) {
		try {
			String url = "rmi://" + serverIP + ":" + SERVER_PORT + "/" + ACCOUNT_SEVICE;
			accountService = (AccountService) Naming.lookup(url);
		} catch (Exception e) {
			System.out.println("loading config failed: " + e.getMessage());
		}
	}

	public void write(int value) {
		Account acc = getNextAccount();
		acc.setBalance(value);
	}

	public int read() {
		Account account = getNextAccount();
		return account.getBalance();
		
	}

}
