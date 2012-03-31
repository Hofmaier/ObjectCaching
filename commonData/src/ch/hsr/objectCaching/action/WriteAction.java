package ch.hsr.objectCaching.action;

import ch.hsr.objectCaching.account.Account;

public class WriteAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value;

	public WriteAction(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public void execute(Account account) {
		boolean successfull = false;
		do {
			result.startMeasuring();
			try {
				account.setBalance(value);
				successfull = true;
			} catch (RuntimeException exeption) {
				System.out.println("Writing values failed with the value " + value);
				successfull = false;
			}
			result.stopMeasuring();
		} while (!successfull);
	}
}
