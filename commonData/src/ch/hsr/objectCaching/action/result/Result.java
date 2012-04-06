package ch.hsr.objectCaching.action.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TimeRecord> listOfAttempt;
	private TimeRecord currentTry;
	
	public enum BasicAction {
		READ, WRITE;
	}
	
	public enum ActionResult {
		SUCCESSFUL, FAILED;
	}

	public Result() {
		listOfAttempt = new ArrayList<TimeRecord>();
	}

	public void startTimeMeasurement() {
		currentTry = new TimeRecord();
		currentTry.setStartTime(System.nanoTime());
	}

	public void stopTimeMeasurement() {
		currentTry.setStopTime(System.nanoTime());
		currentTry.setActionResult(ActionResult.SUCCESSFUL);
		listOfAttempt.add(currentTry);
	}

	public List<TimeRecord> getAllIntermediateResult() {
		return listOfAttempt;
	}

	public int getNumberOfTry() {
		return listOfAttempt.size();
	}

	public void startTimeMeasurement(BasicAction type) {
		currentTry = new TimeRecord(type);
		currentTry.setStartTime(System.nanoTime());
	}

	public void stopTimeMeasurement(ActionResult result) {
		currentTry.setStopTime(System.nanoTime());
		currentTry.setActionResult(result);
		listOfAttempt.add(currentTry);
		
	}

}
