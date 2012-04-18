package ch.hsr.objectCaching.account;

import java.io.Serializable;



public class AccountImpl implements Account, Serializable {
	
	private double balance;

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance)
	{
		this.balance = balance;
	}

}
