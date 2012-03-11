package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.interfaces.Account;

public class AccountImpl implements Account {
	
	private int balance;

	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance)
	{
		this.balance = balance;
	}

}
