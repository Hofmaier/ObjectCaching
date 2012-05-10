package ch.hsr.objectCaching.testFrameworkClient;

import java.util.ArrayList;
import java.util.logging.Logger;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.action.Action;
import ch.hsr.objectCaching.interfaces.ClientSystemUnderTest;
import ch.hsr.objectCaching.scenario.Scenario;

public class TestClient {

	private static Logger logger = Logger.getLogger(TestClient.class.getName());
	private Scenario scenario;
	private AccountService accountService;
	private ClientSystemUnderTest clientUnderTest;
	private ArrayList<Account> accounts;
	private int accountIndex = 0;

	
	public TestClient(ClientSystemUnderTest clientUnderTest){
		this.clientUnderTest = clientUnderTest;
		accountService = clientUnderTest.getAccountService();
	}

	public void init() {
		accounts = (ArrayList<Account>) accountService.getAllAccounts();
	}

	public void runScenario() {
		logger.info("Starting the test with the scenario " + scenario.getId());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Action action : scenario.getActionList()) {
			Account acc = getNextAccount();
			action.execute(acc);
		}
	}

	private Account getNextAccount() {
		if (accountIndex == accounts.size()){
			accountIndex = 0;
		}
		return accounts.get(accountIndex++);
	}

	public void shutdown() {
		clientUnderTest.shutdown();
		logger.info("TestClient shutdown was successfull");
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
	
	public Scenario getScenario() {
		return scenario;
	}

}
