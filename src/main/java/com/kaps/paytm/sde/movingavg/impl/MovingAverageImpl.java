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
 * <br><li>Data-structure operations calculate the moving average with time complexity of O(1).
 * <br><li>Data-structure operations calculate the moving average with space complexity of O(1). [because upper bound of queue(N) is known up-front, that gives constant space/size complexity] <br> 
 * @author Mehul Kapadia
 */
@Component("movingAverageImpl")
public class MovingAverageImpl implements IMovingAverage {

	/*
	 * Queue DS which actually holds all the elements for which moving average is to be calculated.
	 */
	private Queue<Double> eleQueue; 
	
	/*
	 * Maximum size of the window for which moving average is to be calculated.
	 */
	private int windowSize;
	
	/*
	 * Total of all the elements.
	 */
	private int sum = 0; 
	
	private DecimalFormat df = new DecimalFormat("####0.00");


	/**
	 * Constructor accepts window size of moving average data structure.
	 * @param windowSize (must be positive Integer) and cannot be change/updated once constructor is initialized.
	 */
	public MovingAverageImpl(@Value("#{ systemProperties['windowSize'] }") int windowSize) {
		if(windowSize <=0)
			throw new IllegalArgumentException("Window size of moving average data-structure must be positive Integer. Supplied value=[" + windowSize + "]");

		this.eleQueue = new LinkedList<>(); //Third party DS like CircularFifoQueue Or EvicitngQueue can be used here.
		this.windowSize = windowSize;
	}

	/**
	 * Method calculates and return moving average of last N elements added by accepting last element 'newElement'. 
	 * <br><li>It uses queue as it's internal DS to store and keep track of elements being added into moving average data-structure.
	 * <br><li>Method keeps track of total <b>'sum'</b> of all the elements,  <b>windowSize</b> - Window size of elements for which moving average to be calculated.
	 * <br><li>Total sum is calculated upon element addition, if element queue size is greater than windowSize(N) then first element is removed(poll) from queue and also from total 'sum' 
	 * <br><li>If element queue size is less than  windowSize(N) then element is simply added(offer) to sum.
	 * <br><li>Average is calculated based on total 'sum' and queue size. Average is calculate with 2 decimal points precision. 
	 * <br><br>
	 * {@inheritDoc}
	 * @param newElement - New element to be added/considered.
	 * @return (Moving) Average of last N elements added.
	 */
	@Override
	public double next(Double newElement) {
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
	public LinkedList<Double> getAllElements() {
		return new LinkedList<Double>(eleQueue);
	}
}
