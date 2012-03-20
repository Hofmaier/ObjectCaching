package ch.hsr.objectCaching.interfaces;

public class ReadAction extends Action {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public ReadAction(){}
	
	
	@Override
	public void execute(Account account) {
		result.setStartNanoTime(System.nanoTime());		
		account.getBalance();
		result.setEndNanoTime(System.nanoTime());
		result.setNumberOfTry(1);
	}

}
