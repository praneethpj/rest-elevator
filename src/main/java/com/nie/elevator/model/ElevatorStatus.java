package com.nie.elevator.model;

/**
 * Identify the status of elevator.
 * 
 * IDLE: elevator is idle, can be allocate to a task.
 * TASK_STARTED: elevator has been allocated to start a task.
 * MOVE_UP: elevator is moving up.
 * MOVE_DOWN: elevator is moving down.
 * STAY: elevator is stationary, so people can move in or move out.
 * 
 * @author lnie
 *
 */
public enum ElevatorStatus {
	IDLE, TASK_STARTED, MOVE_UP, MOVE_DOWN, STAY
}
