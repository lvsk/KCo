package main.service;

import java.util.ArrayList;
import java.util.List;

import main.domain.Elevator;
import main.domain.ElevatorUser;
import main.enums.ElevatorStatusEnum;

public class ElevatorController {
	private List<Elevator> elevatorList;
	
	public ElevatorController(int noOfElevators){
		//Create list of Elevator objects based on number of elevator in a building
		List<Elevator> elevatorList = new ArrayList<>();
		for(int i = 1; i <= noOfElevators; i++){
			Elevator elevator = new Elevator(i, 1, ElevatorStatusEnum.IDLE);
			elevatorList.add(elevator);
		}
		this.elevatorList = elevatorList;
	}
	
	public List<Elevator> getElevatorList() {
		return elevatorList;
	}

	public void setElevatorList(List<Elevator> elevatorList) {
		this.elevatorList = elevatorList;
	}
	
	public Elevator callElevator(ElevatorUser elevatorUser){		
		/*Find elevator based on given criteria
		7. When an elevator request is made, the unoccupied elevator closest to it will answer
		the call, unless an occupied elevator is moving and will pass that floor on its way. The
		exception is that if an unoccupied elevator is already stopped at that floor, then it will
		always have the highest priority answering that call.
		8. The elevator should keep track of how many trips it has made, and how many floors it
		has passed. The elevator should go into maintenance mode after 100 trips, and stop
		functioning until serviced, therefore not be available for elevator calls.*/
		
		return null;
	}
	
	public void rideElevator(Elevator elevator, ElevatorUser elevatorUser){
		
	}
	
	public void exitElevator(Elevator elevator, ElevatorUser elevatorUser){
		
	}
}