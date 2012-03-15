package ch.hsr.objectCaching.interfaces;

public class ReadAction extends Action {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int result = 0;
	
	public ReadAction(){}
	
	
	@Override
	public void execute(ClientSystemUnderTest client) {
		result = client.read();
	}

}
