package ch.hsr.objectCaching.rmiOnlyClient;

import java.io.ObjectOutputStream;

public interface IStreamProvider {
	public ObjectOutputStream getObjectOutputStream();
}
