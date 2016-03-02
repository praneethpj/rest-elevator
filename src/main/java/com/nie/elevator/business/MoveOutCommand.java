package com.nie.elevator.business;

public class MoveOutCommand implements Command{

	private ElevatorReceiver elevatorReceiver;
	
	public MoveOutCommand(ElevatorReceiver elevatorReceiver) {
		this.elevatorReceiver = elevatorReceiver;
	}
	
	@Override
	public void execute() throws Exception{
		elevatorReceiver.peopleMoveOut();
	}

}
