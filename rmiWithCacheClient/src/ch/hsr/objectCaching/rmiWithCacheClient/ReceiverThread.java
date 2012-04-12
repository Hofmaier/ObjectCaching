package ch.hsr.objectCaching.rmiWithCacheClient;

import java.io.IOException;

public class ReceiverThread implements Runnable 
{

	private MessageManager messageManager;
	public ReceiverThread(MessageManager messageManager)
	{
		this.messageManager = messageManager;
	}
	@Override
	public void run() 
	{
		while(true)
		{
			messageManager.proccessIncomingMessages();
		}
	}

}
