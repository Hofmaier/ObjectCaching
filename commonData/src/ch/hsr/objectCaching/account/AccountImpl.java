package ch.hsr.objectCaching.account;

import java.io.Serializable;



public class AccountImpl implements Account, Serializable {
	
	private static final long serialVersionUID = -6168566455017445307L;
	private double balance;

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance)
	{
		this.balance = balance;
	}

}
