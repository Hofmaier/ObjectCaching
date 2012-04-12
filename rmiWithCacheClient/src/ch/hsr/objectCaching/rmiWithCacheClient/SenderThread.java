 package ch.hsr.objectCaching.rmiWithCacheClient;


public class SenderThread implements Runnable
{
	private MessageManager messageManager;
	
	
	public SenderThread(MessageManager messageManager)
	{
		this.messageManager = messageManager;
	}
	@Override
	public void run() 
	{
		while(true)
		{
			messageManager.proccessOutgoingMessages();
		}
	}

}
