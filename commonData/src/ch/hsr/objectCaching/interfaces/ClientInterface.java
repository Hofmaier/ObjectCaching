package ch.hsr.objectCaching.interfaces;

import java.rmi.Remote;

public interface ClientInterface extends Remote
{
	public void initialize(TestCase test);
}
