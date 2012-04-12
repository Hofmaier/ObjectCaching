package ch.hsr.objectCaching.rmiWithCacheClient;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ch.hsr.objectCaching.dto.ReturnValue;
import ch.hsr.objectCaching.dto.TransferObject;
import ch.hsr.objectCaching.rmiOnlyClient.IStreamProvider;

public class MessageManager {
	
	private IStreamProvider streamProvider;
	private BlockingQueue<TransferObject> sendingQueue = new LinkedBlockingQueue<TransferObject>();
	private BlockingQueue<ReturnValue> returnValueQueue = new LinkedBlockingQueue<ReturnValue>();
	

	public IStreamProvider getStreamProvider() {
		return streamProvider;
	}

	public void setStreamProvider(IStreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}
	
	public BlockingQueue<TransferObject> getSendingQueue() {
		return sendingQueue;
	}

	public void setSendingQueue(BlockingQueue<TransferObject> transferSendingQueue) {
		this.sendingQueue = transferSendingQueue;
	}

	public void sendMessageCall(TransferObject transferObject) 
	{
		sendingQueue.add(transferObject);
	}

	public ReturnValue receiveMethodCallResponse() throws IOException, ClassNotFoundException 
	{
		try {
			return returnValueQueue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void proccessOutgoingMessages()
	{
		try {
			TransferObject transferObject = sendingQueue.take();
			streamProvider.getObjectOutputStream().writeObject(transferObject);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void proccessIncomingMessages()
	{
		Object temp = null;
		try {
			temp = streamProvider.getObjectInputStream().readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(temp instanceof ReturnValue)
		{
			returnValueQueue.add((ReturnValue)temp);
		}
	}
}
