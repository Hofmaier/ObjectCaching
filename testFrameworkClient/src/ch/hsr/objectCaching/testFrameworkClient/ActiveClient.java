package ch.hsr.objectCaching.testFrameworkClient;

public class ActiveClient {

	private ClientUnderTest cut;
	private TestCase testCase;
	
	
	public ActiveClient(ClientUnderTest cut, TestCase testCase){
		this.cut = cut;
		this.testCase = testCase;
	}
	
	public void startRun(){
		testCase.start();
	}
	
	
	public void executeWriteCommand(int value){
		cut.setBalance(value);
	}
	
	public int executeReadCommand(){
		return cut.getBalcance();
	}
	
}
