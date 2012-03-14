package ch.hsr.objectCaching.rmiOnlyServer;

public class AccountServiceSkeleton {
	
	private AccountSkeleton accountSkeleton;

	public ReturnValue invokeMethod(MethodCall methodCall) {
		ReturnValue returnValue = new ReturnValue();
		returnValue.setValue(accountSkeleton.getAllObjectIDs());
		return returnValue;
	}

	public void setAccountSkeleton(AccountSkeleton accountSkeleton) {
		this.accountSkeleton = accountSkeleton;
	}

}
