package ch.hsr.objectCaching.interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Measure> listOfAttempt;
	private Measure currentTry;
	
	public Result(){
	listOfAttempt = new ArrayList<Measure>();
	}

	public void startMeasuring() {
		currentTry = new Measure();
		currentTry.setStartTime(System.nanoTime());	
	}

	public void stopMeasuring() {
		currentTry.setStopTime(System.nanoTime());
		listOfAttempt.add(currentTry);
	}
	
	public List<Measure> getAttempt(){
		return listOfAttempt;
	}
	
	public int getNumberOfTry(){
		return listOfAttempt.size();
	}

}
