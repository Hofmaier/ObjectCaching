package ch.hsr.objectCaching.testFrameworkClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.action.Action;
import ch.hsr.objectCaching.action.result.TimeRecord;
import ch.hsr.objectCaching.scenario.Scenario;

public class TestClientTest {

	private TestClient client;
	
	@Before
	public void setUp(){
		FakeClientUnderTest fakeClient = new FakeClientUnderTest();
		client = new TestClient(fakeClient);
		client.init();
	}
	
	@Test
	public void testSetScenario(){
		Scenario scenario = new Scenario(1);
		client.setScenario(scenario);
		Scenario clientScenario = client.getScenario();
		assertEquals(scenario, clientScenario);
	}
	
	@Test
	public void testRunScenario(){
		client.setScenario(buildFakeScenario());
		client.runScenario();
	}
	
	@Test
	public void testvalidateSenarioResult(){
		client.setScenario(buildFakeScenario());
		client.runScenario();
		
		Scenario resultScenario = client.getScenario();
		List<Action> actions = resultScenario.getActionList();
		for(Action a : actions){
			assertEquals(1, a.getResult().getNumberOfTry());
			assertTrue(checkTime(a));
		}
	}

	private boolean checkTime(Action a) {
		List<TimeRecord> times = a.getResult().getAllIntermediateResult();
		boolean startTimeSmallerThanStopTime = false;
		for(TimeRecord time : times){
			if(time.getStartTime() <= time.getStopTime())
				startTimeSmallerThanStopTime = true;
		}
		return startTimeSmallerThanStopTime;
	}

	private Scenario buildFakeScenario() {
		Scenario scenario = new Scenario(1);
		scenario.setReadAction(1);
		scenario.setWriteAction(2, 42);
		return scenario;
	}

}
