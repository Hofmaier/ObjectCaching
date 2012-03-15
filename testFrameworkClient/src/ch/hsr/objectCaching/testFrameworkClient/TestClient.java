package ch.hsr.objectCaching.testFrameworkClient;

import java.util.ArrayList;
import java.util.Iterator;

import ch.hsr.objectCaching.interfaces.Account;
import ch.hsr.objectCaching.interfaces.AccountService;
import ch.hsr.objectCaching.interfaces.Action;
import ch.hsr.objectCaching.interfaces.Scenario;

public class TestClient {
	
	private Scenario scenario;
	private AccountService accountService;
	private ArrayList<Account> accounts;
	
	public TestClient(Scenario scenario){
		this.scenario = scenario;
	}

	public void init(){
		accounts = (ArrayList<Account>) accountService.getAllAccounts();
	}
	

	public void start() {
		System.out.println("starting...");
		Iterator<Action> actionIter = scenario.getActionList().iterator();
		while (actionIter.hasNext()) {
			Account acc = getNextAccount();
			Action action = actionIter.next();		
			action.execute(acc);
		}
	}


	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;		
	}
	
	private Account getNextAccount() {
		if(accounts.size() == 1){
			return accounts.get(0);
		}
		return null;
	}

}
