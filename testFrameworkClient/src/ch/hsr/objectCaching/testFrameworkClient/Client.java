package ch.hsr.objectCaching.testFrameworkClient;

import java.rmi.Naming;
import java.util.Collection;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;

public class Client implements ClientSystemUnderTest{

	private static final int SERVER_PORT = 24526;
	private static final String ACCOUNT_SEVICE = "AccountService";

	private AccountService accountService;
	private Collection<Account> accountList;
	private Account account;

	public Client() {
	}

	private void init() {
		loadAccountService("152.96.193.9");
		accountList = accountService.getAllAccounts();
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
//		long startNano = System.nanoTime();
//		long startMili	= System.currentTimeMillis();
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		long endNano = System.nanoTime();
//		long endMili	= System.currentTimeMillis();
//		System.out.println("Task done in nano: " + (endNano-startNano));
//		System.out.println("Task done in nano: " + (endMili-startMili));
		System.out.println("write " + value);
//		Account acc = getNextAccount();
//		acc.setBalance(value);
	}

	public int read() {
		System.out.println("Read done");
		return 1;
//		Account account = getNextAccount();
//		return account.getBalance();
		
	}

	@Override
	public AccountService getAccountService() {
		//init();
		return null;
	}
}
