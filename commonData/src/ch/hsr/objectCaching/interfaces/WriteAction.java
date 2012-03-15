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
		account.setBalance(value);
	}

}
