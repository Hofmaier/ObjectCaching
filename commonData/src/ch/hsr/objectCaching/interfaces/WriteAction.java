package ch.hsr.objectCaching.interfaces;

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
			account.setBalance(value);
			result.stopMeasuring();
			successfull = true;
		} while (!successfull);
	}
}
