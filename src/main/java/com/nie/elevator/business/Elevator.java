package com.nie.elevator.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nie.elevator.business.exception.ElevatorException;
import com.nie.elevator.model.ElevatorStatus;

/**
 * @author lnie
 *
 */
public class Elevator implements ElevatorReceiver{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static int ELEVATOR_ACTION_TIME = 5000;
	private static int DEFAULT_NO_OF_PEOPLE = 0;
	private static int DEFAULT_TO_FLOOR_NO = 0;
	
	private String id;
	
	private int currentFloorNo;
	
	private int toFloorNo;
	
	private int noOfPeople;
	
	private ElevatorStatus status;
	
	/**
	 * Initialize an elevator with an identity.
	 * @param id
	 */
	public Elevator(String id) {
		this.id = id;
	}
	
	/**
	 * Call this function so that the elevator is occupied.
	 */
	public synchronized void startTask() {
		this.status = ElevatorStatus.TASK_STARTED;
		this.noOfPeople = DEFAULT_NO_OF_PEOPLE;
		this.toFloorNo = DEFAULT_TO_FLOOR_NO;
	}

	/**
	 * Move elevator to toFloorNo
	 */
	@Override
	public synchronized void moveElevator(int toFloorNo) throws ElevatorException {
		this.toFloorNo = toFloorNo;
		if(currentFloorNo < toFloorNo) {
			this.status = ElevatorStatus.MOVE_UP;
		}
		else if(currentFloorNo > toFloorNo) {
			this.status = ElevatorStatus.MOVE_DOWN;
		}
		else {
			this.status = ElevatorStatus.STAY;
		}
		
		elevatorInAction();
		
		this.currentFloorNo = toFloorNo;
	}

	/**
	 * People move into the elevator. noOfPeople is the number of people move in.
	 */
	@Override
	public synchronized void peopleMoveIn(int noOfPeople) throws ElevatorException {
		
		this.status = ElevatorStatus.STAY;
		
		elevatorInAction();
		
		this.noOfPeople = noOfPeople;
	}

	/**
	 * People move out.
	 */
	@Override
	public synchronized void peopleMoveOut() throws ElevatorException {
		
		this.status = ElevatorStatus.STAY;
		this.toFloorNo = DEFAULT_TO_FLOOR_NO;
		
		elevatorInAction();
		
		this.noOfPeople = DEFAULT_NO_OF_PEOPLE;
		
		this.status = ElevatorStatus.IDLE;
	}
	
	/**
	 * This function simulate the action of an elevator.
	 * 
	 * @throws ElevatorException
	 */
	private void elevatorInAction() throws ElevatorException {
		try {
			Thread.sleep(ELEVATOR_ACTION_TIME);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			throw new ElevatorException();
		}
	}

	public String getId() {
		return id;
	}

	public int getCurrentFloorNo() {
		return currentFloorNo;
	}

	public void setCurrentFloorNo(int currentFloorNo) {
		this.currentFloorNo = currentFloorNo;
	}

	public int getToFloorNo() {
		return toFloorNo;
	}

	public void setToFloorNo(int toFloorNo) {
		this.toFloorNo = toFloorNo;
	}

	public int getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}

	public ElevatorStatus getStatus() {
		return status;
	}

	public void setStatus(ElevatorStatus status) {
		this.status = status;
	}

}
