package com.nie.elevator.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.nie.elevator.business.Elevator;
import com.nie.elevator.business.Task;

@Service("elevatorScheduleService")
@Scope("prototype")
public class ElevatorScheduleServiceImpl implements ElevatorScheduleService{
    
	@Autowired
	private ElevatorAllocateService elevatorAllocateService;
	
	@Autowired
	private AsyncCallService asyncService;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Elevator scheduleElevator(Task task) {
		Elevator elevator = elevatorAllocateService.requestElevator(task);
		if(elevator == null) {
			return null;
		}
		asyncService.schedule(task, elevator);
		return elevator;
	}
	

}
