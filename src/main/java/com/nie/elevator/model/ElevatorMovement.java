package com.nie.elevator.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="elevator_movements")
public class ElevatorMovement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Date time = new Date();
	
	@Column(name="elevator_id")
	private String elevatorID;
	
	@Column(name="current_floor_no")
	private int currentFloorNo;
	
	@Column(name="to_floor_no")
	private int toFloorNo;
	
	@Column(name="no_of_people")
	private int noOfPeople;
	
	@Enumerated(EnumType.STRING)
	private ElevatorStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getElevatorID() {
		return elevatorID;
	}

	public void setElevatorID(String elevatorID) {
		this.elevatorID = elevatorID;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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
