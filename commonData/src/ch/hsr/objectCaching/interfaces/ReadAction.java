package ch.hsr.objectCaching.interfaces;

public class ReadAction extends Action {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public ReadAction(){}
	
	
	@Override
	public void execute(Account account) {
		result.setStartTime(System.currentTimeMillis());		
		account.getBalance();
		result.setEndTime(System.currentTimeMillis());
		result.setNumberOfTry(1);
	}

}
