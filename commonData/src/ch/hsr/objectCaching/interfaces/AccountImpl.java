package ch.hsr.objectCaching.interfaces;


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
