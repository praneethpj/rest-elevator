package com.nie.elevator.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import com.nie.elevator.business.Elevator;
import com.nie.elevator.business.Task;
import com.nie.elevator.business.service.ElevatorScheduleService;
import com.nie.elevator.controller.exception.DataFormatException;
import com.nie.elevator.controller.exception.ResourceNotFoundException;
import com.nie.elevator.repository.ElevatorRepository;


@RestController
@RequestMapping("/elevators")
@Scope("prototype")
public class ElevatorController extends AbstractRestHandler{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	@RequestMapping(value="/floorNo/{floorNo}/toFloorNo/{toFloorNo}/noOfPeople/{noOfPeople}", method = RequestMethod.GET)
	public Elevator requestElevator(@PathVariable int floorNo, 
			@PathVariable int toFloorNo,  
			@PathVariable int noOfPeople) {
		String logInfo = String.format("Request elevator for floorNo:%s, toFloorNo:%s, noOfPeople:%s", floorNo, toFloorNo, noOfPeople);
		logger.info(logInfo);
		
		Task task = new Task();
		task.setFromFloorNO(floorNo);
		task.setToFloorNo(toFloorNo);
		task.setNoOfpeople(noOfPeople);
		
		if(!isTaskValid(task)) {
			throw new DataFormatException();
		}
		
		Elevator elevator = elevatorScheduleService.scheduleElevator(task);
		if(elevator == null) {
			throw new ResourceNotFoundException();
		}
		
		logger.info("Scheduled elevator " + elevator.getId());
		return elevator;
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
		if(task.getNoOfpeople() < 1 || task.getNoOfpeople() > maxNoOfPeople) {
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
