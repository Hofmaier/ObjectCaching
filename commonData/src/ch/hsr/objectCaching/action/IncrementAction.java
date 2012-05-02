package ch.hsr.objectCaching.action;

import java.util.logging.Logger;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.action.result.Result.ActionResult;
import ch.hsr.objectCaching.action.result.Result.BasicAction;
import ch.hsr.objectCaching.dto.RMIException;

public class IncrementAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(IncrementAction.class.getName());
	private final int MINIMAL_TIME_RECORDS_FOR_SUCCESS = 2;
	private long delay;
	private double factor;

	public IncrementAction() {
		super();
	}

	public IncrementAction(long delay, double fator) {
		super();
		this.delay = delay;
		this.factor = fator;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getDelay() {
		return delay;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public double getFactor() {
		return factor;
	}

	@Override
	public void execute(Account account) {
		result.startTotalTimeMeasurement();
		boolean successful = false;
		double balance = 0;
		int numberOfTry = 0;
		while (!successful) {
			result.startTimeMeasurement(BasicAction.READ);
			balance = account.getBalance();
			result.stopTimeMeasurement();
			sleep(numberOfTry);
			try {
				result.startTimeMeasurement(BasicAction.WRITE);
				account.setBalance(balance * factor);
				result.stopTimeMeasurement();
				successful = true;
			} catch (RMIException e) {
				logger.info(e.getMessage());
				successful = false;
				result.stopTimeMeasurement(ActionResult.FAILED);
			}
			numberOfTry++;
		}
		result.stopTotalTimeMeasurement();
	}

	@Override
	public ActionTyp getActionTyp() {
		return ActionTyp.INCREMENT_ACTION;
	}

	@Override
	public int getMinimalNumberOfTimeRecords() {
		return MINIMAL_TIME_RECORDS_FOR_SUCCESS;
	}

	private void sleep(int numberOfLoops) {
		if (numberOfLoops == 0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				System.out.println("sleep interupted");
			}
		}
	}

}
