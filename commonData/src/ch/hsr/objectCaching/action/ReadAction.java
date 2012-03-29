package ch.hsr.objectCaching.action;

import ch.hsr.objectCaching.account.Account;

public class ReadAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int balanceResult;

	public ReadAction() {
	}

	public int getBalance() {
		return balanceResult;
	}

	@Override
	public void execute(Account account) {
		result.startMeasuring();
		balanceResult = account.getBalance();
		result.stopMeasuring();
	}

}
