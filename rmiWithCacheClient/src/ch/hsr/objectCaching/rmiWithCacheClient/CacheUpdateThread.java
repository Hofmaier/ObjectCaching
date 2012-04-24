package ch.hsr.objectCaching.rmiWithCacheClient;

public class CacheUpdateThread implements Runnable {

	private ObjectCache objectCache;
	
	public CacheUpdateThread(ObjectCache objectCache){
		this.objectCache = objectCache;
	}
		
	@Override
	public void run() {
		objectCache.listenForUpdates();
	}

}
