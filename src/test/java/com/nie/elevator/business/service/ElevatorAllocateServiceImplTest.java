package com.nie.elevator.business.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;

import com.nie.elevator.business.Elevator;
import com.nie.elevator.business.Task;
import com.nie.elevator.model.ElevatorStatus;
import com.nie.elevator.repository.ElevatorRepository;

@RunWith(MockitoJUnitRunner.class)
public class ElevatorAllocateServiceImplTest {

	@Mock
	private ElevatorRepository elevatorRepository;
	
	@InjectMocks
	private ElevatorAllocateServiceImpl elevatorAllocateService;
	
	private List<Elevator> elevators = new ArrayList<Elevator>();
	private Elevator elevatorA;
	private Elevator elevatorB;
	private Elevator elevatorC;
	private Elevator elevatorD;
	private Task task;
	
	@Before
	public void init() {
		elevatorA = new Elevator("A");
		elevatorA.setStatus(ElevatorStatus.IDLE);
		elevatorB = new Elevator("B");
		elevatorB.setStatus(ElevatorStatus.IDLE);
		elevatorC = new Elevator("C");
		elevatorC.setStatus(ElevatorStatus.IDLE);
		elevatorD = new Elevator("D");
		elevatorD.setStatus(ElevatorStatus.IDLE);
		elevators.clear();
		elevators.add(elevatorA);
		elevators.add(elevatorB);
		elevators.add(elevatorC);
		elevators.add(elevatorD);
		
		task = new Task();
		task.setFromFloorNO(1);
		task.setToFloorNo(4);
		task.setNoOfpeople(5);
	}
	
	@Test
	public void testRequestElevatorNormalFlow() {
		
		when(elevatorRepository.getElevators()).thenReturn(elevators);
		Elevator elevator = elevatorAllocateService.requestElevator(task);
		assertNotNull(elevator);
	}
	
	@Test
	public void returnNullWhenNoneIdel() {
		elevatorA.setStatus(ElevatorStatus.MOVE_DOWN);
		elevatorB.setStatus(ElevatorStatus.MOVE_UP);
		elevatorC.setStatus(ElevatorStatus.STAY);
		elevatorD.setStatus(ElevatorStatus.TASK_STARTED);
		
		when(elevatorRepository.getElevators()).thenReturn(elevators);
		Elevator elevator = elevatorAllocateService.requestElevator(task);
		assertNull(elevator);
	}
	
	@Test
	public void whenOnlyOneIdleThenReturnThatOne() {
		elevatorA.setStatus(ElevatorStatus.MOVE_DOWN);
		elevatorB.setStatus(ElevatorStatus.MOVE_UP);
		elevatorC.setStatus(ElevatorStatus.STAY);
		elevatorD.setStatus(ElevatorStatus.IDLE);
		
		when(elevatorRepository.getElevators()).thenReturn(elevators);
		Elevator elevator = elevatorAllocateService.requestElevator(task);
		assertNotNull(elevator);
		assertEquals("D", elevator.getId());
	}
	
	@Test
	public void returnClosestElevator() {
		task.setFromFloorNO(6);
		elevatorC.setCurrentFloorNo(7);
		
		elevatorB.setCurrentFloorNo(2);
		elevatorD.setCurrentFloorNo(9);
		elevatorA.setCurrentFloorNo(3);
		when(elevatorRepository.getElevators()).thenReturn(elevators);
		Elevator elevator = elevatorAllocateService.requestElevator(task);
		assertNotNull(elevator);
		assertEquals("C", elevator.getId());
	}
	
	@Test
	public void returnClosestIdelElevator() {
		task.setFromFloorNO(6);
		elevatorC.setCurrentFloorNo(7);
		elevatorC.setStatus(ElevatorStatus.MOVE_DOWN);
		
		elevatorB.setCurrentFloorNo(2);
		elevatorD.setCurrentFloorNo(9);
		elevatorA.setCurrentFloorNo(1);
		when(elevatorRepository.getElevators()).thenReturn(elevators);
		Elevator elevator = elevatorAllocateService.requestElevator(task);
		assertNotNull(elevator);
		assertEquals("D", elevator.getId());
	}

}
