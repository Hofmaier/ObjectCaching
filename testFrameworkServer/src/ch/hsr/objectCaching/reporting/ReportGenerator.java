package ch.hsr.objectCaching.reporting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import ch.hsr.objectCaching.action.Action;
import ch.hsr.objectCaching.action.IncrementAction;
import ch.hsr.objectCaching.action.result.TimeMeasure;
import ch.hsr.objectCaching.scenario.Scenario;

public class ReportGenerator {

	private final int NANOSEC_TO_MILISEC_FACTOR = 1000000;
	private double totalExecutionTime = 0;
	private int totalConflicts = 0;
	private Scenario scenario;
	private String clientIp;
	private String summary;
	private int actionNumber;
	private BufferedWriter out;

	public ReportGenerator(Scenario scenario, String clientIp) {
		this.scenario = scenario;
		this.clientIp = clientIp;
		try {
			out = new BufferedWriter(new FileWriter("Client_" + clientIp + ".txt"));
		} catch (IOException e) {
			System.out.println("IOException in ReportGenerator");
		}
		generateReport();
	}

	public String getSummary() {
		return summary;
	}

	private void generateReport() {
		actionNumber = 0;

		try {
			writeHeader();
			for (Action action : scenario.getActionList()) {

				if (action.getResult().getNumberOfTry() > 1) {
					totalConflicts += action.getResult().getNumberOfTry() - 1;
				}

				int numberOfConflictsPerAction = 0;
				for (TimeMeasure m : action.getResult().getAllIntermediateResult()) {
					double executionTime = getDeltaInMilisec(m);
					switch (action.getActionTyp()) {
					case READ_ACTION:
						writeActionToFile(numberOfConflictsPerAction, executionTime, getReadString());
						break;
					case WRITE_ACTION:
						writeActionToFile(numberOfConflictsPerAction, executionTime, getWriteString());
						break;
					case INCREMENT_ACTION:
						writeActionToFile(numberOfConflictsPerAction, executionTime, getIncrementString(action));
					}
					numberOfConflictsPerAction++;
					totalExecutionTime += executionTime;
				}
				actionNumber++;
			}
			finalizeReport();
		} catch (IOException e) {
			System.out.println("IO Error in Reportgenerator for scenario " + scenario.getId());
		}
	}

	private void finalizeReport() throws IOException {
		summary = ("Total Konflict: " + totalConflicts + " / Gesamt Dauer: " + totalExecutionTime + " ms" + " / durch. Dauer pro Operation: " + totalExecutionTime / (scenario.getActionList().size() + totalConflicts));
		out.write("Total Konflict: " + totalConflicts + " / Gesamt Dauer: " + totalExecutionTime + " ms" + " / durch. Dauer pro Operation: " + totalExecutionTime / (scenario.getActionList().size() + totalConflicts));
		out.flush();
		out.close();
	}

	private void writeHeader() throws IOException {
		out.write("************************************************************" + "\n");
		out.write("Result for Client: " + clientIp + " with ScenarioID: " + scenario.getId() + "\n");
		out.write("************************************************************" + "\n");
		out.write("ActionNr;#ofTries;Time[ms];ACTION\n");
	}

	private void writeActionToFile(int conflict, double time, String readString) throws IOException {
		out.write(actionNumber + ";" + conflict + ";" + time + ";" + readString + System.getProperty("line.separator"));
	}

	private String getReadString() {
		return "READ";
	}

	private String getWriteString() {
		return "WRITE";
	}

	private String getIncrementString(Action action) {
		IncrementAction a = (IncrementAction) action;
		if (a.getDelay() < 1) {
			return "INCREMENT WITHOUT DELAY";
		} else {
			return "INCREMENT WITH DELAY: " + a.getDelay();
		}
	}

	private double getDeltaInMilisec(TimeMeasure m) {
		long nanoSek = m.getStopTime() - m.getStartTime();
		return (double) nanoSek / NANOSEC_TO_MILISEC_FACTOR;
	}

}
