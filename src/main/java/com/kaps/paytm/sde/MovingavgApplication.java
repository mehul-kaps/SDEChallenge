package com.kaps.paytm.sde;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kaps.paytm.sde.movingavg.IMovingAverage;
import com.kaps.paytm.sde.movingavg.impl.MovingAverageQueue;

/**
 * Spring Boot Application main class for MovingAverage data structure operations.
 * @author Mehul Kapadia
 *
 */
@SpringBootApplication
public class MovingavgApplication {

	@Autowired
	IMovingAverage movingAverageQueueImpl; 
	
	public static void main(String[] args) {
		System.setProperty("windowSize", "5"); //Defaulting the windowSize of moving average to 5.
		SpringApplication.run(MovingavgApplication.class, args);
	}

	/**
	 * Method will be called after Spring-boot is initialized so that we can call MovingAverage operations.
	 */
	@PostConstruct
	public void calculateMovingAverage() {
		String format = "Average=%4.2f Elements=%s";
		MovingAverageQueue maq = new MovingAverageQueue(5);
		System.out.println(String.format(format, maq.next(8d), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(2d), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(7d), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(4d), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(9d), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(2d), maq.getAllElements()));
		System.out.println(String.format(format, maq.next(6d), maq.getAllElements()));
	}
}
