package ch.hsr.objectCaching.action.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TimeMeasure> listOfAttempt;
	private TimeMeasure currentTry;

	public Result() {
		listOfAttempt = new ArrayList<TimeMeasure>();
	}

	public void startMeasuring() {
		currentTry = new TimeMeasure();
		currentTry.setStartTime(System.nanoTime());
	}

	public void stopMeasuring() {
		currentTry.setStopTime(System.nanoTime());
		listOfAttempt.add(currentTry);
	}

	public List<TimeMeasure> getAttempt() {
		return listOfAttempt;
	}

	public int getNumberOfTry() {
		return listOfAttempt.size();
	}

}
