package ch.hsr.objectCaching.testFrameworkClient;
import ch.hsr.objectCaching.rmiOnlyClient.AccountStub;


public class TestCase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	public void start(){
		AccountStub accStub = new AccountStub();
		accStub.getBalance();
	}

}
