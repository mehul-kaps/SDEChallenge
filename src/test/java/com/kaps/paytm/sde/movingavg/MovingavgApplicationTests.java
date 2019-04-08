package com.kaps.paytm.sde.movingavg;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kaps.paytm.sde.movingavg.impl.MovingAverageQueue;

/**
 * Test for moving average implementation class MovingAverageQueue. 
 * @author Mehul Kapadia
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MovingavgApplicationTests {

	@Autowired
	IMovingAverage movingAverageQueueImpl; 

	static {
		//Defaulting the windowSize of moving average to 5.
		System.setProperty("windowSize", "5"); 		
	}

	@Before
	public void setup() {
	}

	/**
	 * These following sunny day test ensures correct functionality for moving average algorithm.
	 */
	@Test
	public void movingAveragePositiveTests() {
		assertEquals(8.00, movingAverageQueueImpl.next(8),0.001);
		assertEquals(5.00, movingAverageQueueImpl.next(2),0.001);
		assertEquals(5.67, movingAverageQueueImpl.next(7),0.001);
		assertEquals(5.25, movingAverageQueueImpl.next(4),0.001);
		assertEquals(6.00, movingAverageQueueImpl.next(9),0.001);
		assertEquals(4.80, movingAverageQueueImpl.next(2),0.001);
		assertEquals(5.60, movingAverageQueueImpl.next(6),0.001);
	}

	/**
	 * Ensures testing of negative test case, Where constructor initialization is failed when 0 or negative windowSize is supplied.
	 */
	@Test
	public void movingAverageNegativeTests() 
	{
		try {
			assertThrows(IllegalArgumentException.class, () -> new MovingAverageQueue(-1));
		}
		catch(Throwable t) {
			System.out.println(">>>> Exception : " + t.getMessage());
		}
	}
}
