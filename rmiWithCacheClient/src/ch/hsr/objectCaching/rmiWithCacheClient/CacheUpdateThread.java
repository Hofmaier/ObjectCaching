package ch.hsr.objectCaching.rmiWithCacheClient;

public class CacheUpdateThread implements Runnable {

	private ObjectCache objectCache;
		
	@Override
	public void run() {
		objectCache.listenForUpdates();
	}

}
