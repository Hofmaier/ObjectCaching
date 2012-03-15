package ch.hsr.objectCaching.interfaces;

public class WriteAction extends Action{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value;
	
	public WriteAction(int balance){
		value = balance;
	}
	
	
	@Override
	public void execute(ClientSystemUnderTest client) {
		client.write(value);
	}

}
