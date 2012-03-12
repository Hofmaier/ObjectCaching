package ch.hsr.objectCaching.rmiOnlyServer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestObjectIDGenerator {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNext() {
		int first = ObjectIDGenerator.next();
		int second = ObjectIDGenerator.next();
		
		assertEquals(second, first + 1);
	}

}
