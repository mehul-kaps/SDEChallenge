package com.kaps.paytm.sde.movingavg;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

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
		movingAverageQueueImpl = new MovingAverageQueue(5);
		assertEquals(8.00, movingAverageQueueImpl.next(8d),0.001);
		assertEquals(5.00, movingAverageQueueImpl.next(2d),0.001);
		assertEquals(5.67, movingAverageQueueImpl.next(7d),0.001);
		assertEquals(5.25, movingAverageQueueImpl.next(4d),0.001);
		assertEquals(6.00, movingAverageQueueImpl.next(9d),0.001);
		assertEquals(4.80, movingAverageQueueImpl.next(2d),0.001);
		assertEquals(5.60, movingAverageQueueImpl.next(6d),0.001);
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
	
	/**
	 * getAllElements tests
	 * <li> initial test confirms addition of 5 elements and then calling getAllElements() and them compare against expected list.
	 * <li> We add more elements than data structure's initial size. This will start rotating/removing elements from queue head and adding new elements to queue.
	 */
	@Test
	public void checkMovingAverageElementsTest() {
		movingAverageQueueImpl = new MovingAverageQueue(5);

		movingAverageQueueImpl.next(8d);
		movingAverageQueueImpl.next(2d);
		movingAverageQueueImpl.next(7d);
		movingAverageQueueImpl.next(4d);
		movingAverageQueueImpl.next(9d);
		
		LinkedList<Double> elementsFromDS = movingAverageQueueImpl.getAllElements();
		LinkedList<Double> elementsExpected = new LinkedList<Double>();
		elementsExpected.add(8d);
		elementsExpected.add(2d);
		elementsExpected.add(7d);
		elementsExpected.add(4d);
		elementsExpected.add(9d);
		
		assertTrue(elementsFromDS.equals(elementsExpected));
		
		//Adding more elements than initial N size of the data structure, This will start rotating/removing elements from queue head.  
		movingAverageQueueImpl.next(2d);
		movingAverageQueueImpl.next(6d);
		
		elementsFromDS = movingAverageQueueImpl.getAllElements();
		elementsExpected = new LinkedList<Double>();
		elementsExpected.add(7d);
		elementsExpected.add(4d);
		elementsExpected.add(9d);
		elementsExpected.add(2d);
		elementsExpected.add(6d);
		
		assertTrue(elementsFromDS.equals(elementsExpected));
	}
}
