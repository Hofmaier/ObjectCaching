package ch.hsr.objectCaching.testFrameworkClient;

public class FakeClient implements ClientUnderTest{

	private int balance = 0;
	
	public FakeClient(){
	}
	
	@Override
	public void setBalance(int accountBalance) {
		balance = accountBalance;
	}

	@Override
	public int getBalcance() {
		return balance;
	}

}
