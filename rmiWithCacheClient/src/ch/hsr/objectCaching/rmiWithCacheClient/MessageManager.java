package ch.hsr.objectCaching.rmiWithCacheClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ch.hsr.objectCaching.dto.ObjectRequestResponse;
import ch.hsr.objectCaching.dto.ObjectUpdate;
import ch.hsr.objectCaching.dto.ReturnValue;
import ch.hsr.objectCaching.dto.TransferObject;
import ch.hsr.objectCaching.rmiOnlyClient.IStreamProvider;

public class MessageManager {
	
	private IStreamProvider streamProvider;
	private BlockingQueue<TransferObject> sendingQueue = new LinkedBlockingQueue<TransferObject>();
	private BlockingQueue<Object> objectFromServerQueue = new LinkedBlockingQueue<Object>();
	private BlockingQueue<ReturnValue> returnValueQueue = new LinkedBlockingQueue<ReturnValue>();
	private BlockingQueue<ObjectUpdate> objectUpdateQueue = new LinkedBlockingQueue<ObjectUpdate>();
	private boolean senderRunning = true;
	private boolean receiverRunning = true;

	

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
	
	public ObjectUpdate receiveUpdate() throws IOException, ClassNotFoundException 
	{
		try {
			return objectUpdateQueue.take();
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
			ObjectInputStream ois = streamProvider.getObjectInputStream();
			temp = ois.readObject();
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
		if(temp instanceof ObjectUpdate){
			ObjectUpdate update = (ObjectUpdate) temp;
			objectUpdateQueue.add(update);
		}
		if(temp == null)
		{
			receiverRunning = false;
		}
	}
	
	public void startSenderThread(){
		new Thread(new SenderThread(this)).start();
	}
	
	public void startReceiverThread(){
		new Thread(new ReceiverThread(this)).start();
	}
	
	public void shutDown()
	{
		objectUpdateQueue.add(null);
		sendMessageCall(null);
		senderRunning = false;
	}

	public boolean isSenderRunning() {
		return senderRunning;
	}
	public boolean isReceiverRunning() {
		return receiverRunning;
	}
}
