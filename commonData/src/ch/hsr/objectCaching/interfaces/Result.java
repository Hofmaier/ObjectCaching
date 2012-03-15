package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;

public class Result implements Serializable{
	
	private long startTime;
	private long endTime;
	private int numberOfTry = 0;
	
	public Result(){
		startTime = 0;
		endTime = 0;
	}
	
	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public long getEndTime() {
		return endTime;
	}


	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
	public long getDuration(){
		return endTime - startTime;
	}

	public int getNumberOfTry() {
		return numberOfTry;
	}

	public void setNumberOfTry(int numberOfTry) {
		this.numberOfTry = numberOfTry;
	}

}
