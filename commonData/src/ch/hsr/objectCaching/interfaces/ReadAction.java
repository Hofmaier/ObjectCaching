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
		result.setStartNanoTime(System.nanoTime());		
		balanceResult = account.getBalance();
		result.setEndNanoTime(System.nanoTime());
		result.setNumberOfTry(1);
	}

}
