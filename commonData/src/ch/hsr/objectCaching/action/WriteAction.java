package ch.hsr.objectCaching.action;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.action.result.Result.BasicAction;



public class WriteAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int MINIMAL_TIME_RECORDS_FOR_SUCCESS = 1;
	private double newBalance;
	
	public WriteAction(double balance) {
		super();
		newBalance = balance;
	}
	
	@Override
	public void execute(Account account) {
		result.startTimeMeasurement(BasicAction.WRITE);
		account.setBalance(newBalance);
		result.stopTimeMeasurement();
	}

	@Override
	public ActionTyp getActionTyp() {
		return ActionTyp.WRITE_ACTION;
	}

	@Override
	public int getMinimalNumberOfTimeRecords() {
		return MINIMAL_TIME_RECORDS_FOR_SUCCESS;
	}
}
