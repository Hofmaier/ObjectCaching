package ch.hsr.objectCaching.testFrameworkClient;

import java.util.ArrayList;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.action.Action;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;
import ch.hsr.objectCaching.scenario.Scenario;

public class TestClient {

	private Scenario scenario;
	private AccountService accountService;
	private ClientSystemUnderTest clientUnderTest;
	private ArrayList<Account> accounts;
	private int accountIndex = 0;

	
	public TestClient(ClientSystemUnderTest clientUnderTest){
		this.clientUnderTest = clientUnderTest;
		setAccountService(clientUnderTest.getAccountService());
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

	public void runScenario() {
		System.out.println("Started Scenario with id= " + scenario.getId());
		for (Action action : scenario.getActionList()) {
			Account acc = getNextAccount();
			action.execute(acc);
		}
	}

	private void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public Account getNextAccount() {
		if (accountIndex == accounts.size()){
			accountIndex = 0;
		}
		return accounts.get(accountIndex++);

	}

	public void shutdown() {
		clientUnderTest.shutdown();
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

}
