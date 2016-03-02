package com.nie.elevator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nie.elevator.model.ElevatorMovement;

@Repository
public interface ElevatorMovementRepository extends JpaRepository<ElevatorMovement, Integer> {
	
	List<ElevatorMovement> findByElevatorIDOrderByTimeAsc(String elevatorID);
	
}
