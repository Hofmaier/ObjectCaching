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
	private int AccountIndex = 0;

	public TestClient(Scenario scenario) {
		this.scenario = scenario;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void init() {
		accounts = (ArrayList<Account>) accountService.getAllAccounts();
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void start() {
		for (Action action : scenario.getActionList()) {
			Account acc = getNextAccount();
			action.execute(acc);
		}
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public Account getNextAccount() {
		if (AccountIndex == accounts.size()){
			AccountIndex = 0;
		}
		return accounts.get(AccountIndex++);

	}

}
