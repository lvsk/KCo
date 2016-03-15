package main.service;

import java.util.ArrayList;
import java.util.List;

import main.domain.Elevator;
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
}