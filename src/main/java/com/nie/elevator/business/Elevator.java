package com.nie.elevator.business;
 
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.sql.Timestamp;
import java.time.LocalDateTime;

 

import com.nie.elevator.business.exception.ElevatorException;
import com.nie.elevator.model.ElevatorDirection;
import com.nie.elevator.model.ElevatorStatus;

/**
 * @author lnie
 * Modified by Praneeth
 *
 */
public class Elevator implements ElevatorReceiver{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static int ELEVATOR_ACTION_TIME = 3000;
	private static int ELEVATOR_DROP_TIME = 4000;
	private static int DEFAULT_NO_OF_PEOPLE = 1;
	private static int DEFAULT_TO_FLOOR_NO = 1;
	
	private String id;
	
	private int currentFloorNo;
	
	private int toFloorNo;
	
	private int noOfPeople;
	
	private ElevatorStatus status;

	private ElevatorDirection direction;
	
	private int eta;


	@UpdateTimestamp
    private LocalDateTime lastTouched;
	
	/**
	 * Initialize an elevator with an identity.
	 * @param id
	 */
	public Elevator(String id) {
		this.id = id;
	}

	private void logs(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String logInfo = String.format(timestamp.getTime()+".123 "+"{liftId:%s,state:%s,direction:%s, person:%s, toFloorNo:%s}",getId(),getStatus(),getDirection(), "0", toFloorNo);
		logger.info(logInfo);
	}
	/**
	 * Call this function so that the elevator is occupied.
	 */
	public synchronized void startTask() {
		this.status = ElevatorStatus.IDLE;
		this.direction=ElevatorDirection.NAN;
		this.noOfPeople = DEFAULT_NO_OF_PEOPLE;
		this.toFloorNo = DEFAULT_TO_FLOOR_NO;
		
		logs();
	}

	/**
	 * Move elevator to toFloorNo
	 */
	@Override
	public synchronized void moveElevator(int toFloorNo) throws ElevatorException {
		this.toFloorNo = toFloorNo;
		if(currentFloorNo < toFloorNo) {
		 this.status = ElevatorStatus.TO_PICKUP;
			this.direction=ElevatorDirection.UP;
		}
		else if(currentFloorNo > toFloorNo) {
	 this.status = ElevatorStatus.TO_PICKUP;
		this.direction=ElevatorDirection.DOWN;
		}
		else {
			this.status = ElevatorStatus.IDLE;
			this.direction=ElevatorDirection.NAN;
		}
		
		elevatorInAction();
		
		this.currentFloorNo = toFloorNo;
		logs();
	}

	/**
	 * People move into the elevator. noOfPeople is the number of people move in.
	 */
	@Override
	public synchronized void peopleMoveIn(int noOfPeople) throws ElevatorException {
		
		this.status = ElevatorStatus.PICKUP;
		
		elevatorDropAction();
		
		this.noOfPeople = noOfPeople;
		logs();
	}

	/**
	 * People move out.
	 */
	@Override
	public synchronized void peopleMoveOut() throws ElevatorException {
		
		this.status = ElevatorStatus.IDLE;
		this.toFloorNo = DEFAULT_TO_FLOOR_NO;
		
		elevatorDropAction();
		
		this.noOfPeople = DEFAULT_NO_OF_PEOPLE;
		
		this.status = ElevatorStatus.DROPOFF;
		logs();
	}
	
	/**
	 * This function simulate the action of an elevator.
	 * 
	 * @throws ElevatorException
	 */
	private void elevatorDropAction() throws ElevatorException {
		try {
			Thread.sleep(ELEVATOR_DROP_TIME);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			throw new ElevatorException();
		}
		logs();
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
		logs();
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

	public int getEta() {
	
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}
	@Override
	public String toString() {
		return "{\"ETA\":"+getEta() +"}";
	}

	public ElevatorDirection getDirection() {
		return direction;
	}

	public void setDirection(ElevatorDirection direction) {
		this.direction = direction;
	}

}
