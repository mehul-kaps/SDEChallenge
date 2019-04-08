package com.kaps.paytm.sde.movingavg;

/**
 * This interface defines the operations of data-structure for calculating moving average of last N elements added.
 * 
 * <br><li>Data-structure operations calculate the moving average with time complexity of O(1).
 * <br><li>Data-structure operations calculate the moving average with space complexity of O(1). [because upper bound of queue(N) is known up-front] 
 * @author Mehul Kapadia
 *
 */
public interface IMovingAverage {

	/**
	 * This method calculates and return moving average of last N elements added by accepting last element 'newElement'. 
	 * @param newElement - New element to be added/considered.
	 * @return (Moving) Average of last N elements added.
	 */
	public double next(int newElement);
	
	/**
	 * Returns all the elements of underlying moving-average data structure in string format. 
	 * @return all elements in String format. 
	 */
	public String getAllElements();
	
	
}
