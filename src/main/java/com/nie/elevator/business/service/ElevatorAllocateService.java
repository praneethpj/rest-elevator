package com.nie.elevator.business.service;

import com.nie.elevator.business.Elevator;
import com.nie.elevator.business.Task;

/**
 * A service that allocates the best available elevator upon request.
 * 
 * @author lnie
 *
 */
public interface ElevatorAllocateService {
	
	/**
	 * <p>
	 * Allocate the best available elevator as per the task passed in.<br/>
	 * The best available elevator means the idle elevator which is the closest to the floor of the request.
	 * </p>
	 * <p>
	 * Return null if no elevator is available.
	 * </p> 
	 * @param task Customer's request.
	 * @return Allocated elevator.
	 */
	public Elevator requestElevator(Task task);
	
}
