package ch.hsr.objectCaching.action;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.action.result.Result.ActionResult;
import ch.hsr.objectCaching.action.result.Result.BasicAction;

public class IncrementAction extends Action{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public void execute(Account account) {
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
			} catch (RuntimeException e) {
				successful = false;
				result.stopTimeMeasurement(ActionResult.FAILED);
			}
			numberOfTry++;
		}
	}

	private void sleep(int numberOfLoops) {
		if(numberOfLoops == 0)
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				System.out.println("sleep interupted");
			}
	}

	@Override
	public ActionTyp getActionTyp() {
		return ActionTyp.INCREMENT_ACTION;
	}

	@Override
	public int getMinimalNumberOfTimeRecords() {
		return 2;
	}

}
