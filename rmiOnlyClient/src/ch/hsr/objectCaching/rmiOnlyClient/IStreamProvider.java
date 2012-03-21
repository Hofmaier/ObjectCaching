package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

public interface IStreamProvider {
	public ObjectOutputStream getObjectOutputStream();
	public ObjectInputStream getObjectInputStream();
	public void setSocketAdress(InetSocketAddress socketAdress);
}
