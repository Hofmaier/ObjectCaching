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
	
	public enum BasicAction {
		READ, WRITE, ABORT;
	}
	
	public enum ActionResult {
		SUCCESSFUL, FAILED;
	}

	public Result() {
		listOfAttempt = new ArrayList<TimeMeasure>();
	}

	public void startTimeMeasurement() {
		currentTry = new TimeMeasure();
		currentTry.setStartTime(System.nanoTime());
	}

	public void stopTimeMeasurement() {
		currentTry.setStopTime(System.nanoTime());
		currentTry.setActionResult(ActionResult.SUCCESSFUL);
		listOfAttempt.add(currentTry);
	}

	public List<TimeMeasure> getAllIntermediateResult() {
		return listOfAttempt;
	}

	public int getNumberOfTry() {
		return listOfAttempt.size();
	}

	public void startTimeMeasurement(BasicAction type) {
		currentTry = new TimeMeasure(type);
		currentTry.setStartTime(System.nanoTime());
	}

	public void stopTimeMeasurement(ActionResult result) {
		currentTry.setStopTime(System.nanoTime());
		currentTry.setActionResult(result);
		listOfAttempt.add(currentTry);
		
	}

}
