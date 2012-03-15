package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface IStreamProvider {
	public ObjectOutputStream getObjectOutputStream();
	public ObjectInputStream getObjectInputStream();
}
