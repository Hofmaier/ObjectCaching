package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;

public class Result implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long startTimeNano;
	private long endTimeNano;
	private int numberOfTry = 0;
	
	public Result(){
		startTimeNano = 0;
		endTimeNano = 0;
	}
	
	public long getStartNanoTime() {
		return startTimeNano;
	}


	public void setStartNanoTime(long startTime) {
		this.startTimeNano = startTime;
	}


	public long getEndNanoTime() {
		return endTimeNano;
	}


	public void setEndNanoTime(long endTime) {
		this.endTimeNano = endTime;
	}
	
	
	public long getDuration(){
		return endTimeNano - startTimeNano;
	}

	public int getNumberOfTry() {
		return numberOfTry;
	}

	public void setNumberOfTry(int numberOfTry) {
		this.numberOfTry = numberOfTry;
	}

}
