package ch.hsr.objectCaching.testFrameworkClient;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;

public class ClientUnderTestFake implements ClientSystemUnderTest{

	private ArrayList<Account> accounts;
	
	public ClientUnderTestFake(){
		initAccounts();
	}
	
	
	private void initAccounts() {
		accounts = new ArrayList<Account>();
		accounts.add(new FakeAccount());
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
	public void setServerSocketAdress(InetSocketAddress socketAdress) {
		// TODO Auto-generated method stub
		
	}
	
	
	public class FakeAccount implements Account{

		private int balance = 0;
		
		public FakeAccount(){}
		
		@Override
		public int getBalance() {
			return balance;
		}

		@Override
		public void setBalance(int balance) {
			this.balance = balance;
		}
		
	}

}
