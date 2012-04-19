package ch.hsr.objectCaching.rmiWithCacheClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ch.hsr.objectCaching.dto.ObjectRequestResponse;
import ch.hsr.objectCaching.dto.ReturnValue;
import ch.hsr.objectCaching.dto.TransferObject;
import ch.hsr.objectCaching.dto.Update;
import ch.hsr.objectCaching.rmiOnlyClient.IStreamProvider;

public class MessageManager {
	
	private IStreamProvider streamProvider;
	private BlockingQueue<TransferObject> sendingQueue = new LinkedBlockingQueue<TransferObject>();
	private BlockingQueue<Object> objectFromServerQueue = new LinkedBlockingQueue<Object>();
	private BlockingQueue<ReturnValue> returnValueQueue = new LinkedBlockingQueue<ReturnValue>();
	private BlockingQueue<Update> objectUpdateQueue = new LinkedBlockingQueue<Update>();

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

	public Object receiveObject() throws IOException, ClassNotFoundException 
	{
		try {
			return objectFromServerQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ReturnValue receiveReturnValue(){
		try {
			return returnValueQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void proccessOutgoingMessages()
	{
		try {
			TransferObject transferObject = sendingQueue.take();
			ObjectOutputStream oos = streamProvider.getObjectOutputStream();
			oos.writeObject(transferObject);
			oos.flush();
			
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
		if(temp instanceof ObjectRequestResponse){
			ObjectRequestResponse response = (ObjectRequestResponse) temp;
			objectFromServerQueue.add(response.getRequestedObject());
		}
		if(temp instanceof Update){
			Update update = (Update) temp;
			
		}
	}
	
	public void startSenderThread(){
		new Thread(new SenderThread(this)).start();
	}
	
	public void startReceiverThread(){
		new Thread(new ReceiverThread(this)).start();
	}
}
