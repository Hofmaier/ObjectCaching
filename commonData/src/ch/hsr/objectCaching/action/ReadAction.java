package ch.hsr.objectCaching.action;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.action.result.Result.BasicAction;

public class ReadAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double balance;
	private final int MINIMAL_TIME_RECORDS_FOR_SUCCESS = 1;

	public ReadAction() {
		super();
	}

	public void execute(Account account) {
		result.startTotalTimeMeasurement();
			result.startTimeMeasurement(BasicAction.READ);
				balance = account.getBalance();
			result.stopTimeMeasurement();
		result.stopTotalTimeMeasurement();
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
		return MINIMAL_TIME_RECORDS_FOR_SUCCESS;
	}
}
