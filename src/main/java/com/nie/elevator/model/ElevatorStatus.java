package com.nie.elevator.model;

/**
 * Identify the status of elevator.
 * 
IDLE - no job.
TO_PICKUP - moving to the floor to pick up the person
PICKUP - picking up the person.
TO_DROPOFF - moving to the destination floor
DROPOFF - dropping off the person on the floor
 * 
 * @author lnie
 * Modified by Praneeth
 *
 */
public enum ElevatorStatus {
	IDLE, TO_PICKUP, PICKUP, TO_DROPOFF, DROPOFF
}
