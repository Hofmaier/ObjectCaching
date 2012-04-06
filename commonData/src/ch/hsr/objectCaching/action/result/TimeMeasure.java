package ch.hsr.objectCaching.action.result;

import java.io.Serializable;

import ch.hsr.objectCaching.action.BasicAction;

public class TimeMeasure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long startTime;
	private long stopTime;
	private BasicAction ActionTyp;

	public TimeMeasure() {
		startTime = 0;
		stopTime = 0;
	}

	public TimeMeasure(BasicAction actionTyp) {
		this.ActionTyp = actionTyp;
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
		return ActionTyp;
	}
}
