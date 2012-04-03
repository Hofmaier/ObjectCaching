package ch.hsr.objectCaching.rmiOnlyServer;

import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;

public class AccountServiceSkeleton implements RMIonlySkeleton {
	
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
