package ch.hsr.objectCaching.rmiWithCacheServer;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.rmiOnlyServer.ObjectIDGenerator;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyServerSystem;

public class RMIWithCacheServerSystem extends RMIOnlyServerSystem {

	private ObjectManager objectManager = new ObjectManager();
	
	public RMIWithCacheServerSystem(){
		AccountSkeleton accountSkeletonWithCache = new AccountSkeleton();
		accountSkeletonWithCache.setObjectManager(objectManager);
		accountSkeleton = accountSkeletonWithCache;
		accountServiceSkeleton.setAccountSkeleton(accountSkeleton);
	}

	@Override
	public ClientHandler getClientHandlerInstance() {
		RMIWithCacheClientHandler clientHandler = new RMIWithCacheClientHandler();
		clientHandler.setObjectManager(objectManager);
		clientHandler.setAccountServiceSkeleton(accountServiceSkeleton);
		clientHandler.setAccountSkeleton(accountSkeleton);
		
		return clientHandler;
	}
	
	@Override
	public void addAccount(Account testObject) {
		Integer objectID = ObjectIDGenerator.next();
		objectManager.addAccount(objectID, testObject);
		accountSkeleton.addObject(objectID, testObject);
	}
}
