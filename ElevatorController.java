package main.service;

import java.util.ArrayList;
import java.util.List;

import main.domain.Elevator;
import main.domain.ElevatorUser;
import main.enums.ElevatorStatusEnum;

public class ElevatorController {
	private List<Elevator> elevatorList;
	private List<ElevatorUser> waitingElevUserRequestList = new ArrayList<>();
	
	public ElevatorController(int noOfElevators){
		//Create list of Elevator objects based on number of elevator in a building
		List<Elevator> elevatorList = new ArrayList<>();
		for(int i = 1; i <= noOfElevators; i++){
			Elevator elevator = new Elevator(i, 1, ElevatorStatusEnum.IDLE, this);
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
	
	public List<ElevatorUser> getWaitingElevUserRequestList() {
		return waitingElevUserRequestList;
	}

	public void setWaitingElevUserRequestList(List<ElevatorUser> waitingElevUserRequestList) {
		this.waitingElevUserRequestList = waitingElevUserRequestList;
	}
	
	public Elevator callElevator(ElevatorUser elevatorUser){		
		Elevator elevator = this.findElevatorForUserRequest(elevatorUser.getStartFloor(), elevatorUser.getEndFloor());
		if(elevator == null){
			System.out.println("Elevator user request " + elevatorUser.getElevatorUserId() + " added to the waiting list.");
			waitingElevUserRequestList.add(elevatorUser);	
			return null;
		}
		
		//Make sure selected elevator is on requested floor, otherwise move the elevator to the requested start floor
		//Loop through the start and end floor and report the floor
		//2. Each elevator will report as is moves from floor to floor.
		
		return elevator;
	}
	
	public void rideElevator(Elevator elevator, ElevatorUser elevatorUser){
		
	}
	
	public void exitElevator(Elevator elevator, ElevatorUser elevatorUser){
		
	}
	
	/* Find elevator - Criteria
	 * When an elevator request is made, the unoccupied elevator closest to it will answer the call, unless an occupied elevator is moving and 
	 * will pass that floor on its way. 
	 * The exception is that if an unoccupied elevator is already stopped at that floor, then it will always have the highest priority answering that call.
	 * The elevator should go into maintenance mode after 100 trips, and stop functioning until serviced, therefore not be available for elevator calls.
	 */	
	public synchronized Elevator findElevatorForUserRequest(int startFloor, int endFloor){
		Elevator elevator = getElevatorStoppedAtGivenFloor(startFloor);
		if(elevator == null){
			elevator = getOccupiedElevatorPassingBy(startFloor, endFloor);
			if(elevator == null){
				elevator = getClosestUnoccupiedElevator(startFloor);
			}
		}
		
		return elevator;
	}
	
	//unoccupied elevator is already stopped at that floor
	private synchronized Elevator getElevatorStoppedAtGivenFloor(int startFloor){
		for(Elevator elevator : elevatorList){
			if(ElevatorStatusEnum.IDLE.equals(elevator.getStatus())
					&& !elevator.isOccupied()
					&& startFloor == elevator.getCurrentFloor()){
				return elevator;
			}
		}
		return null;
	}
	
	//occupied elevator is moving and will pass that floor on its way
	private synchronized Elevator getOccupiedElevatorPassingBy(int startFloor, int endFloor){
		for(Elevator elevator : elevatorList){
			if(ElevatorStatusEnum.MOVING.equals(elevator.getStatus())) {
				List<ElevatorUser> elevUserReqList = elevator.getUserRequestList();
				for(ElevatorUser elevUserReq : elevUserReqList) {
					//Lower floor to higher floor path
					if(elevUserReq.getEndFloor() > elevUserReq.getStartFloor()){
						//Same direction and in path
						if(startFloor >= elevUserReq.getStartFloor() && endFloor > elevUserReq.getStartFloor()){
							return elevator;
						}
					} else{ //Higher floor to lower floor path
						//Same direction and in path
						if(startFloor <= elevUserReq.getStartFloor() && endFloor < elevUserReq.getStartFloor()){
							return elevator;
						}
					}
				}
			}
		}
		return null;
	}
	
	//the unoccupied elevator closest
	private synchronized Elevator getClosestUnoccupiedElevator(int startFloor){
		//Go through all the elevators floor, get the closest floor number to the requests start floor	
		return null;
	}
}