package com.nie.elevator.business;

public class MoveInCommand implements Command{

	private ElevatorReceiver elevatorReceiver;
	private int noOfPeople;
	
	public MoveInCommand(ElevatorReceiver elevatorReceiver, int noOfPeople) {
		this.elevatorReceiver = elevatorReceiver;
		this.noOfPeople = noOfPeople;
	}
	
	@Override
	public void execute() throws Exception{
		elevatorReceiver.peopleMoveIn(noOfPeople);
	}

}
