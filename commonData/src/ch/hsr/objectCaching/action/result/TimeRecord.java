package ch.hsr.objectCaching.action.result;

import java.io.Serializable;

import ch.hsr.objectCaching.action.result.Result.ActionResult;
import ch.hsr.objectCaching.action.result.Result.BasicAction;

public class TimeRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long startTime;
	private long stopTime;
	private BasicAction actionTyp;
	private ActionResult actionResult;

	public TimeRecord() {
		startTime = 0;
		stopTime = 0;
	}

	public TimeRecord(BasicAction actionTyp) {
		this.actionTyp = actionTyp;
		startTime = 0;
		stopTime = 0;
	}

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

	public BasicAction getActionTyp() {
		return actionTyp;
	}
	
	public void setActionTyp(BasicAction typ) {
		actionTyp = typ;
	}

	public ActionResult getActionResult() {
		return actionResult;
	}

	public void setActionResult(ActionResult actionResult) {
		this.actionResult = actionResult;
	}
}
