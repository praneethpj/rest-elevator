package com.nie.elevator.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nie.elevator.business.Elevator;
import com.nie.elevator.business.Task;
import com.nie.elevator.model.ElevatorStatus;
import com.nie.elevator.repository.ElevatorRepository;

@Service
public class ElevatorAllocateServiceImpl implements ElevatorAllocateService{

	@Autowired
	private ElevatorRepository repository;
	
	/**
	 * <p>
	 * Allocate the best available elevator as per the task passed in.<br/>
	 * The best available elevator means the idle elevator which is the closest to the floor of the request.
	 * </p>
	 * <p>
	 * Return null if no elevator is available.
	 * </p>
	 * @param task
	 * @return 
	 */
	@Override
	public synchronized Elevator requestElevator(Task task) {
		
		if(task == null) {
			return null;
		}
		
		Elevator optionalElevator = null;
		for (Elevator elevator : repository.getElevators()) {
			
			if(elevator.getStatus() == ElevatorStatus.IDLE) {
				if(optionalElevator == null) {
					optionalElevator = elevator;
				}
				else {
					if(Math.abs(elevator.getCurrentFloorNo() - task.getFromFloorNo()) < Math.abs(optionalElevator.getCurrentFloorNo() - task.getFromFloorNo())){
						optionalElevator = elevator;
					}
				}
			}
		}
		if(optionalElevator != null) {
			optionalElevator.startTask();
		}

		return optionalElevator;
	}

}
