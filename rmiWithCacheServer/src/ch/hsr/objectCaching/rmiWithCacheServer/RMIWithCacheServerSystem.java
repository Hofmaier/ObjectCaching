package ch.hsr.objectCaching.rmiWithCacheServer;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.interfaces.ClientHandler;
import ch.hsr.objectCaching.rmiOnlyServer.ObjectIDGenerator;
import ch.hsr.objectCaching.rmiOnlyServer.RMIOnlyServerSystem;

public class RMIWithCacheServerSystem extends RMIOnlyServerSystem {

	private ObjectManager objectManager = new ObjectManager();

	@Override
	public ClientHandler getClientHandlerInstance() {
		RMIWithCacheClientHandler clientHandler = new RMIWithCacheClientHandler();
		clientHandler.setObjectManager(objectManager);
		clientHandler.setAccountServiceSkeleton(accountServiceSkeleton);
		AccountSkeleton accountSkeletonWithCache = new AccountSkeleton();
		accountSkeleton = accountSkeletonWithCache;
		accountSkeletonWithCache.setObjectManager(objectManager);
		clientHandler.setAccountSkeleton(accountSkeletonWithCache);
		
		return clientHandler;
	}
	
	@Override
	public void addAccount(Account testObject) {
		Integer objectID = ObjectIDGenerator.next();
		objectManager.addAccount(objectID, testObject);
		accountSkeleton.addObject(objectID, testObject);
	}
}
