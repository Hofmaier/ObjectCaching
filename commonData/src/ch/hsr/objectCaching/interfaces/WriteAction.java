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
	
	public int getValue(){
		return value;
	}
	
	@Override
	public void execute(Account account) {
		result.setStartNanoTime(System.nanoTime());		
		//TODO loop until successful
		account.setBalance(value);
		result.setEndNanoTime(System.nanoTime());
		result.setNumberOfTry(1);
	}

}
