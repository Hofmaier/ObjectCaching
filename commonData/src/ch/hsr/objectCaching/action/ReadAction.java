package ch.hsr.objectCaching.action;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.action.result.Result.BasicAction;

public class ReadAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double balance;

	public ReadAction() {
		super();
	}

	public void execute(Account account) {
		result.startTimeMeasurement(BasicAction.READ);
		balance = account.getBalance();
		result.stopTimeMeasurement();
	}

	public double getBalance() {
		return balance;
	}

	@Override
	public ActionTyp getActionTyp() {
		return ActionTyp.READ_ACTION;
	}

	@Override
	public int getMinimalNumberOfTimeRecords() {
		return 1;
	}
}
