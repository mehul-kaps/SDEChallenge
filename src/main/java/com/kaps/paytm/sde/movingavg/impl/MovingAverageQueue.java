package com.kaps.paytm.sde.movingavg.impl;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kaps.paytm.sde.movingavg.IMovingAverage;

/**
 * Concrete implementation of the operations supported by IMovingAverage contract interface 
 * to calculate moving average of last N elements added. 
 * @author Mehul Kapadia
 *
 */
@Component("movingAverageQueueImpl")
public class MovingAverageQueue implements IMovingAverage {

	/*
	 * Queue DS which actually holds all the elements for which moving average is to be calculated.
	 */
	private Queue<Integer> eleQueue; 
	
	/*
	 * Maximum size of the window for which moving average is to be calculated.
	 */
	private int windowSize;
	
	/*
	 * Total of all the elements.
	 */
	private int sum = 0; 
	
	DecimalFormat df = new DecimalFormat("####0.00");


	/**
	 * Constructor accepts window size of moving average data structure.
	 * @param windowSize (must be positive Integer) and cannot be change/updated once constructor is initialized.
	 */
	public MovingAverageQueue(@Value("#{ systemProperties['windowSize'] }") int windowSize) {
		if(windowSize <=0)
			throw new IllegalArgumentException("Window size of moving average data-structure must be positive Integer. Supplied value=[" + windowSize + "]");

		this.eleQueue = new LinkedList<>(); //Third party DS like CircularFifoQueue Or EvicitngQueue can be used here.
		this.windowSize = windowSize;
	}

	/**
	 * Method calculates and return moving average of last N elements added by accepting last element 'newElement'. 
	 * <br><li>It uses queue as it's internal DS to store and keep track of elements being added into the data-structure.
	 * <br><li>Method keeps track of total <b>'sum'</b> of all the elements,  <b>windowSize</b> - Window size of elements for which moving average to be calcualated.
	 * <br><li>Total sum is calculated upon element addition and depending on the size of the queue and windowSize it removes element from internal queue data structure.
	 * <br><li>Average is calculated based on total sum and queue size.  
	 * <br><br>
	 * {@inheritDoc}
	 * @param newElement - New element to be added/considered.
	 * @return (Moving) Average of last N elements added.
	 */
	@Override
	public double next(int newElement) {
		eleQueue.offer(newElement);
		sum += newElement;
		
		if(eleQueue.size()>this.windowSize){
			sum -= eleQueue.poll();
		}

		
		return Double.valueOf((df.format((double)sum/eleQueue.size())));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAllElements() {
		return eleQueue.toString();
	}

	public static void main(String[] args) {
		String format = "Average=%4.2f Elements=%s";
		MovingAverageQueue maq = new MovingAverageQueue(5);
		System.out.println(String.format(format, maq.next(8), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(2), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(7), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(4), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(9), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(2), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(6), maq.getAllElements()));
		
		/**
		 *  Average=8.00 Elements=[8]
			Average=5.00 Elements=[8, 2]
			Average=5.67 Elements=[8, 2, 7]
			Average=5.25 Elements=[8, 2, 7, 4]
			Average=6.00 Elements=[8, 2, 7, 4, 9]
			Average=4.80 Elements=[2, 7, 4, 9, 2]
			Average=5.60 Elements=[7, 4, 9, 2, 6]
		 */
	}
}
