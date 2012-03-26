package ch.hsr.objectCaching.interfaces;

public class ReadAction extends Action {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int balanceResult;
	
	public ReadAction(){}
	
	public int getBalance(){
		return balanceResult;
	}
	
	@Override
	public void execute(Account account) {
		boolean successfull = false;
		do {
			result.startMeasuring();
			balanceResult = account.getBalance();
			result.stopMeasuring();
			successfull = true;
		} while (!successfull);
	}

}
