package com.nie.elevator.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nie.elevator.business.Elevator;
import com.nie.elevator.business.ElevatorRunner;
import com.nie.elevator.business.Task;

/**
 * A Service that facilitates asynchronous call.
 * @author lnie
 *
 */
@Component
@Scope("prototype")
public class AsyncCallService {

	@Autowired ElevatorRunner elevatorRunner;
	
	@Async
    public void schedule(Task task, Elevator elevator) {
        
        elevatorRunner.setTask(task);
        elevatorRunner.setElevator(elevator);
        elevatorRunner.run();
    }
}
