package com.nie.elevator.repository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nie.elevator.Application;
import com.nie.elevator.model.ElevatorMovement;
import com.nie.elevator.model.ElevatorStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, 
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class, 
	DbUnitTestExecutionListener.class })
@DatabaseSetup("/ElevatorMovement.xml")
@ActiveProfiles("test")
public class ElevatorMovementRepositoryTest {

	@Autowired
    private ElevatorMovementRepository repository;
	
	@Test
	public void findByElevatorIDReturnsCorrectResults() {
		String elevatorID = "A";
		List<ElevatorMovement> elevatorMovements = repository.findByElevatorIDOrderByTimeAsc(elevatorID);

		assertNotNull(elevatorMovements);
		assertEquals(5, elevatorMovements.size());
		assertThat(elevatorMovements.get(0),
				allOf(hasProperty("id", is(1)), hasProperty("currentFloorNo", is(1)),
						hasProperty("toFloorNo", is(0)), hasProperty("noOfPeople", is(0)),
						hasProperty("status", is(ElevatorStatus.DROPOFF))));
		elevatorMovements.forEach(elevatorMovement -> {
			assertThat(elevatorMovement,
					allOf(hasProperty("elevatorID", is("A"))));
		});
	}
	
	@Test
	public void findByElevatorIDReturnsOnlyResultsOfThatElevatorID() {
		String elevatorID = "A";
		List<ElevatorMovement> elevatorMovements = repository.findByElevatorIDOrderByTimeAsc(elevatorID);

		assertNotNull(elevatorMovements);
		assertEquals(5, elevatorMovements.size());
		
		elevatorMovements.forEach(elevatorMovement -> {
			assertThat(elevatorMovement,
					allOf(hasProperty("elevatorID", is("A"))));
		});
	}
	
	@Test
	public void findByWrongElevatorIDReturnsEmptyResult() {
		String elevatorID = "C";
		List<ElevatorMovement> elevatorMovements = repository.findByElevatorIDOrderByTimeAsc(elevatorID);

		assertNotNull(elevatorMovements);
		assertEquals(0, elevatorMovements.size());
	}
	
	@Test
	public void findByEmptyElevatorIDReturnsEmptyResult() {
		String elevatorID = " ";
		List<ElevatorMovement> elevatorMovements = repository.findByElevatorIDOrderByTimeAsc(elevatorID);

		assertNotNull(elevatorMovements);
		assertEquals(0, elevatorMovements.size());
	}
	
	@Test
	public void findByNullElevatorIDReturnsEmptyResult() {
		String elevatorID = null;
		List<ElevatorMovement> elevatorMovements = repository.findByElevatorIDOrderByTimeAsc(elevatorID);

		assertNotNull(elevatorMovements);
		assertEquals(0, elevatorMovements.size());
	}

}
