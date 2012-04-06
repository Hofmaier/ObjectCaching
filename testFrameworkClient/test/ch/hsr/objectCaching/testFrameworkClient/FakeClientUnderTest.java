package ch.hsr.objectCaching.testFrameworkClient;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;

public class FakeClientUnderTest implements ClientSystemUnderTest{

	private ArrayList<Account> accounts;
	
	public FakeClientUnderTest(){
		initAccounts();
	}
	
	private void initAccounts() {
		accounts = new ArrayList<Account>();
		accounts.add(new FakeAccount());	
	}

	@Override
	public AccountService getAccountService() {
		return new AccountService() {			
			@Override
			public Collection<Account> getAllAccounts() {
				return accounts;
			}
		};
	}
	
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void setServerSocketAdress(InetSocketAddress socketAdress) {
		// TODO Auto-generated method stub	
	}
		
	public class FakeAccount implements Account{
		private double balance = 0;	
		public FakeAccount(){}
		
		@Override
		public double getBalance() {
			return balance;
		}

		@Override
		public void setBalance(double balance) {
			this.balance = balance;
		}		
	}
}
