package ch.hsr.objectCaching.reporting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import ch.hsr.objectCaching.action.Action;
import ch.hsr.objectCaching.action.IncrementAction;
import ch.hsr.objectCaching.action.ReadAction;
import ch.hsr.objectCaching.action.WriteAction;
import ch.hsr.objectCaching.action.result.TimeMeasure;
import ch.hsr.objectCaching.scenario.Scenario;

public class ReportGenerator {

	private final int NANOSEC_TO_MILISEC_FACTOR = 1000000;
	private double totalTime = 0;
	private int totalConflicts = 0;
	private Scenario scenario;
	private String clientIp;
	private String summary;

	public ReportGenerator(Scenario scenario, String clientIp) {
		this.scenario = scenario;
		this.clientIp = clientIp;
		generateReport();
	}
	
	public String getSummary()
	{
		return summary;
	}

	private void generateReport() {
		
		int actionNumber = 0;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("Client_" + clientIp + ".txt"));
			out.write("************************************************************" + "\n");
			out.write("Result for Client: " + clientIp + " with ScenarioID: " + scenario.getId() + "\n");
			out.write("************************************************************" + "\n");
			out.write("ActionNr;#ofTries;Time[ms];ACTION\n");
			for (Action action : scenario.getActionList()) {
				if (action instanceof WriteAction) {
					if (action.getResult().getNumberOfTry() > 1) {
						totalConflicts += action.getResult().getNumberOfTry() - 1;
					}

					int conflict = 0;
					for (TimeMeasure m : action.getResult().getAttempt()) {
						double time = getDeltaInMilisec(m);
						out.write(actionNumber + ";" + conflict + ";" + time + ";" + "WRITE" + "\n");
						conflict++;
						totalTime += time;
					}
				}
				if(action instanceof IncrementAction)
				{
					if(action.getResult().getNumberOfTry() > 1)
					{
						totalConflicts += action.getResult().getNumberOfTry() - 1;
					}
					int conflict = 0;
					IncrementAction iAction = (IncrementAction)action;
					for (TimeMeasure m : action.getResult().getAttempt()) {
						double time = getDeltaInMilisec(m);
						if(iAction.getDelay() < 1)
						{
							out.write(actionNumber + ";" + conflict + ";" + time + ";" + "INCREMENT WITHOUT DELAY\n");
						}
						else
						{
							out.write(actionNumber + ";" + conflict + ";" + time + ";" + "INCREMENT WITH DELAY: " + iAction.getDelay() + "\n");
						}
						conflict++;
						totalTime += time;
					}			
				}
				if (action instanceof ReadAction) {
					if (action.getResult().getNumberOfTry() > 1) {
						totalConflicts += action.getResult().getNumberOfTry() - 1;
					}
					int conflict = 0;
					for (TimeMeasure m : action.getResult().getAttempt()) {
						double time = getDeltaInMilisec(m);
						out.write(actionNumber + ";" + conflict + ";" + time + ";" + "READ" + "\n");
						conflict++;
						totalTime += time;
					}
				}
				actionNumber++;
			}
			summary= ("Total Konflict: " + totalConflicts + " / Gesamt Dauer: " + totalTime + " ms" + " / durch. Dauer pro Operation: " + totalTime/(scenario.getActionList().size()+totalConflicts));
			out.write("Total Konflict: " + totalConflicts + " / Gesamt Dauer: " + totalTime + " ms" + " / durch. Dauer pro Operation: " + totalTime/(scenario.getActionList().size()+totalConflicts));
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("Writing File failed");
		}
	}

	private double getDeltaInMilisec(TimeMeasure m) {
		long nanoSek = m.getStopTime() - m.getStartTime();
		return (double) nanoSek / NANOSEC_TO_MILISEC_FACTOR;
	}

}
