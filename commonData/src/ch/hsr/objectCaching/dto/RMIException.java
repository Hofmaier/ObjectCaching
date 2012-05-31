package ch.hsr.objectCaching.dto;

public class RMIException extends RuntimeException{

	private static final long serialVersionUID = -6310894731627056467L;
	private String message = "The Account-Object has changed since the last getBalance-Call";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
