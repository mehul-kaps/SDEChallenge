package com.kaps.paytm.sde;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kaps.paytm.sde.movingavg.IMovingAverage;
import com.kaps.paytm.sde.movingavg.impl.MovingAverageImpl;

/**
 * Spring Boot Application main class for MovingAverage data structure operations.
 * @author Mehul Kapadia
 *
 */
@SpringBootApplication
public class MovingavgApplication {

	private static Logger logger = LoggerFactory.getLogger(MovingavgApplication.class);

	@Autowired
	IMovingAverage movingAverageImpl; 

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
		//MovingAverageImpl maq = new MovingAverageImpl(5);
		logger.info(String.format(format, movingAverageImpl.next(8d), movingAverageImpl.getAllElements()));
		logger.info(String.format(format, movingAverageImpl.next(2d), movingAverageImpl.getAllElements()));
		logger.info(String.format(format, movingAverageImpl.next(7d), movingAverageImpl.getAllElements()));
		logger.info(String.format(format, movingAverageImpl.next(4d), movingAverageImpl.getAllElements()));
		logger.info(String.format(format, movingAverageImpl.next(9d), movingAverageImpl.getAllElements()));
		logger.info(String.format(format, movingAverageImpl.next(2d), movingAverageImpl.getAllElements()));
		logger.info(String.format(format, movingAverageImpl.next(6d), movingAverageImpl.getAllElements()));
	}
}
