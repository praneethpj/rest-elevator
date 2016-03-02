package com.nie.elevator.business;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nie.elevator.model.ElevatorMovement;
import com.nie.elevator.repository.ElevatorMovementRepository;

/**
 * This class drives the action of an elevator.<br/>
 * The moving action of an elevator is:<br/>
 * 1. Move to the requested floor.
 * 2. Stay there so people can move in.
 * 3. Move to the destination floor.
 * 4. Stay there so people can move out.
 * 
 * Command design pattern is applied to organize elevator's behaviors.
 * @author lnie
 *
 */
@Component
@Scope("prototype")
public class ElevatorRunner{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Task task;
	private Elevator elevator;
	
	@Autowired
	private ElevatorMovementRepository elevatorRecordRepository;
	
	public synchronized void run() {
		
		try {
			persistElevatorMovement(elevator);
			
			moveElevator(task.getFromFloorNo());
			
			persistElevatorMovement(elevator);
			
			peopleMoveIn(task.getNoOfpeople());
			
			persistElevatorMovement(elevator);
			
			moveElevator(task.getToFloorNo());
			
			persistElevatorMovement(elevator);
			
			peopleMoveOut();
			
			persistElevatorMovement(elevator);
			
		} catch (Exception e) {
			logger.error("Elevator is at fault.", e);
		}
	}
	
	@Transactional
	private void persistElevatorMovement(Elevator elevator) {
		ElevatorMovement movement = new ElevatorMovement();
		movement.setElevatorID(elevator.getId());
		movement.setCurrentFloorNo(elevator.getCurrentFloorNo());
		movement.setToFloorNo(elevator.getToFloorNo());
		movement.setNoOfPeople(elevator.getNoOfPeople());
		movement.setStatus(elevator.getStatus());
		elevatorRecordRepository.save(movement);
	}

	private void moveElevator(int toFloorNo) throws Exception {
		MoveCommand moveCommand = new MoveCommand(elevator, toFloorNo);
		moveCommand.execute();
	}
	
	private void peopleMoveIn(int noOfpeople) throws Exception {
		MoveInCommand moveInCommand = new MoveInCommand(elevator, noOfpeople);
		moveInCommand.execute();
	}
	
	private void peopleMoveOut() throws Exception {
		MoveOutCommand moveOutCommand = new MoveOutCommand(elevator);
		moveOutCommand.execute();
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}
	
}
