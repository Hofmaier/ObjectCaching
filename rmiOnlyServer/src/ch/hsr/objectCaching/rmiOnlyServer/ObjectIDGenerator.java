package ch.hsr.objectCaching.rmiOnlyServer;

public class ObjectIDGenerator {
	
	private static int previous = 2;

	public static Integer next() {
		return ++previous;
	}

}
