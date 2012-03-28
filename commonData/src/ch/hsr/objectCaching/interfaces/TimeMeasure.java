package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;

public class TimeMeasure implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long startTime = 0;
	private long stopTime = 0;
	
	public TimeMeasure(){}
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getStopTime() {
		return stopTime;
	}

	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}
	
	
}
