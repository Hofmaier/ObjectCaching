package ch.hsr.objectCaching.rmiOnlyServer;

public class ObjectIDGenerator {
	
	private static int previous;

	public static Integer next() {
		return ++previous;
	}

}
