package ch.hsr.objectCaching.rmiOnlyServer;


public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dispatcher dispatcher = new Dispatcher(12345);
		dispatcher.accept();

	}

}
