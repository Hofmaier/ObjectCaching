package ch.hsr.objectCaching.interfaces;

public class WriteAction extends Action{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value;

	public WriteAction(int value){
		this.value = value;
	}
	
	@Override
	public void execute(Account account) {
		result.setStartTime(System.currentTimeMillis());		
		//TODO loop until successful
		account.setBalance(value);
		result.setEndTime(System.currentTimeMillis());
		result.setNumberOfTry(1);
	}

}
