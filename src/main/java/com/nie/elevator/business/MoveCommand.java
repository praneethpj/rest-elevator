package com.nie.elevator.business;

public class MoveCommand implements Command{

	private ElevatorReceiver elevatorReceiver;
	private int toFloor;
	
	public MoveCommand(ElevatorReceiver elevatorReceiver, int toFloor) {
		this.elevatorReceiver = elevatorReceiver;
		this.toFloor = toFloor;
	}
	
	@Override
	public void execute() throws Exception{
		elevatorReceiver.moveElevator(toFloor);
	}

}
