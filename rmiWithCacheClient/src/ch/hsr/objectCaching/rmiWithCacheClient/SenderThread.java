 package ch.hsr.objectCaching.rmiWithCacheClient;

import java.io.IOException;


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
		while(messageManager.isSenderRunning())
		{
			messageManager.proccessOutgoingMessages();
		}
		try {
			messageManager.getStreamProvider().getObjectOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean close()
	{
		return true;
	}

}
