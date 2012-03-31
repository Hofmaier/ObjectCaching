package ch.hsr.objectCaching.interfaces.serverSystemUnderTest;

public class RMIException {
	private String message = "The Account-Object has changed since the last getBalance-Call";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
