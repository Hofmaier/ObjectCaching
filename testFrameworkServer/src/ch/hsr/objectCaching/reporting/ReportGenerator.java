package ch.hsr.objectCaching.reporting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.hsr.objectCaching.interfaces.Action;
import ch.hsr.objectCaching.interfaces.TimeMeasure;
import ch.hsr.objectCaching.interfaces.ReadAction;
import ch.hsr.objectCaching.interfaces.Scenario;
import ch.hsr.objectCaching.interfaces.WriteAction;

public class ReportGenerator {

	private List<Scenario> scenarios;
	private double totalTime = 0;
	private int totalConflicts = 0;

	public ReportGenerator() {
		scenarios = new ArrayList<Scenario>();
	}

	public void addScenario(Scenario s) {
		scenarios.add(s);
	}

	public void makeSummary() {
		for (Scenario s : scenarios) {
			generateReport(s);
		}
	}

	private void generateReport(Scenario s) {
		int actionNumber = 0;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("Scenario_" + s.getId() + ".txt"));
			out.write("******************************" + "\n");
			out.write("Result for ScenarioID: " + s.getId() + "\n");
			out.write("******************************" + "\n");
			out.write("ActionNr;Time[ms]\n");
			for (Action action : s.getActionList()) {
				if (action instanceof WriteAction) {

					if (action.getResult().getNumberOfTry() > 1) {
						totalConflicts += action.getResult().getNumberOfTry() - 1;
					}

					int konflict = 0;
					for (TimeMeasure m : action.getResult().getAttempt()) {
						double time = getDeltaInMilisec(m);
						out.write(actionNumber + "_" + konflict + ";" + time + ";" + "WRITE" + "\n");
						konflict++;
						totalTime += time;
					}
				}
				if (action instanceof ReadAction) {
					if (action.getResult().getNumberOfTry() > 1) {
						totalConflicts += action.getResult().getNumberOfTry() - 1;
					}
					int konflict = 0;
					for (TimeMeasure m : action.getResult().getAttempt()) {
						double time = getDeltaInMilisec(m);
						out.write(actionNumber + "_" + konflict + ";" + time + ";" + "READ" + "\n");
						konflict++;
						totalTime += time;
					}
				}
				actionNumber++;
			}

			out.write("Total Konflict: " + totalConflicts + " / Gesamt Dauer: " + totalTime + " ms");
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("Writing File failed");
		}
	}

	private double getDeltaInMilisec(TimeMeasure m) {
		long nanoSek = m.getStopTime() - m.getStartTime();
		return (double) nanoSek / 1000000;
	}

}
