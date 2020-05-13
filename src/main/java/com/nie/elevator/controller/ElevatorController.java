package com.nie.elevator.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import com.nie.elevator.business.Elevator;
import com.nie.elevator.business.Task;
import com.nie.elevator.business.service.ElevatorScheduleService;
import com.nie.elevator.controller.exception.DataFormatException;
import com.nie.elevator.controller.exception.ResourceNotFoundException;
import com.nie.elevator.repository.ElevatorRepository;

@SpringBootApplication
@ComponentScan("com.nie.elevator.controller")
@RestController
@RequestMapping("/smartkent")
@Scope("prototype")
public class ElevatorController extends AbstractRestHandler{
	 
	
	@Autowired
	private ElevatorRepository repository;
	
	@Autowired
	private ElevatorScheduleService elevatorScheduleService;
	
	@Value("${max.noOfPeople}")
	private int maxNoOfPeople;
	
	@Value("${min.floorNo}")
	private int minFloorNo;
	
	@Value("${max.floorNo}")
	private int maxFloorNo;

	/**
	 * List all elevators.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Elevator> findElevators() {
		return repository.getElevators();
	}
	
	/**
	 * Request an elevator as per customer's request.
	 * @param floorNo
	 * @param toFloorNo
	 * @param noOfPeople
	 * @return
	 */
	@RequestMapping(value="/liftsimulation",method = RequestMethod.GET)
	public String requestElevator(@RequestParam("fromFloor") int fromFloor,@RequestParam("toFloor") int toFloor) {
		 
		Task task = new Task();
		task.setFromFloorNO(fromFloor);
		task.setToFloorNo(toFloor);
		task.setNoOfpeople(1);
		task.setEta((toFloor-fromFloor)*3);
		
		if(!isTaskValid(task)) {
			throw new DataFormatException();
		}
		
		Elevator elevator = elevatorScheduleService.scheduleElevator(task);
		 elevator.setEta(Math.abs((toFloor-fromFloor)*3));
		if(elevator == null) {
			throw new ResourceNotFoundException("No Resources");
		}
		 
	 
		return elevator.toString();
	}
	
	/**
	 * Validate request from front-end
	 * @param task
	 * @return
	 */
	private boolean isTaskValid(Task task) {
		if(task == null) {
			return false;
		}
	 
		if(task.getToFloorNo() < minFloorNo || task.getToFloorNo() > maxFloorNo) {
			return false;
		}
		
		if(task.getFromFloorNo() < minFloorNo || task.getFromFloorNo() > maxFloorNo) {
			return false;
		}
		
		if(task.getFromFloorNo() == task.getToFloorNo()) {
			return false;
		}
		return true;
	}

}
