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
		//Calculate number of floors
		int elevatorCurrentFloor = elevator.getCurrentFloor();
		if(elevatorCurrentFloor < elevatorUser.getStartFloor()){ //Move up
			for(int i = elevatorCurrentFloor; i <= elevatorUser.getStartFloor() ; i++){
				System.out.println("Moving up - Elevator " + elevator.getElevatorId() + " travelling towards user request " + elevatorUser.getElevatorUserId() + " is on " + i  + " floor.");
			}
		} else { //Move down
			for(int i = elevatorCurrentFloor; i > elevatorUser.getStartFloor() ; i--){
				System.out.println("Moving down - Elevator " + elevator.getElevatorId() + " travelling towards user request " + elevatorUser.getElevatorUserId() + " is on " + i  + " floor.");
			}
		}
		
		elevator.setNumOfFloors(elevator.getNumOfFloors() + Math.abs(elevatorUser.getStartFloor() - elevatorUser.getEndFloor()));
		elevator.setCurrentFloor(elevatorUser.getStartFloor());
		List<ElevatorUser> addUserRequesttoList = elevator.getUserRequestList();
		addUserRequesttoList.add(elevatorUser);
		elevator.setUserRequestList(addUserRequesttoList);
		return elevator;
	}
	
	public void rideElevator(Elevator elevator, ElevatorUser elevatorUser){
		//3. Each elevator will report when it opens or closes its doors.
		
		//elevator door opens
		//loop through the user request start and end floor and report the floor traveling
		//elevator door closes
		System.out.println("Elevator " + elevator.getElevatorId() + " door opens." );		
		System.out.println("Elevator User " + elevatorUser.getElevatorUserId() + " enters Elevator " + elevator.getElevatorId());
		System.out.println("Elevator " + elevator.getElevatorId() + " door closes." );
		int startFloor = elevatorUser.getStartFloor();
		int endFloor = elevatorUser.getEndFloor();
		
		elevator.isOccupied();
		elevator.setStatus(ElevatorStatusEnum.MOVING);
		
		if(startFloor < endFloor){ //Move Up
			for(int i = startFloor + 1; i <= endFloor ; i++){
				System.out.println("Moving up - Elevator " + elevator.getElevatorId() + " taking user " + elevatorUser.getElevatorUserId() + " is on " + i  + " floor.");
			}
		} else { //Move down
			for(int i = startFloor-1; i >= endFloor ; i--){
				System.out.println("Moving down - Elevator " + elevator.getElevatorId() + " taking user " + elevatorUser.getElevatorUserId() + " is on " + i  + " floor.");
			}
		}
	}
	
	public void exitElevator(Elevator elevator, ElevatorUser elevatorUser){
		/*3. Each elevator will report when it opens or closes its doors.
		8. The elevator should keep track of how many trips it has made, and how many floors it
		has passed. The elevator should go into maintenance mode after 100 trips, and stop
		functioning until serviced, therefore not be available for elevator calls.*/
		
		//elevator door opens
		//Calculate number of floors
		//Add a trip to this elevator
		//Check elevator has reached 100 trips and set status to SERVICE
		elevator.setStatus(ElevatorStatusEnum.SERVICE);
		//Check anymore requests in the elevator else status to IDLE, unoccupied
		elevator.setStatus(ElevatorStatusEnum.IDLE);
		
		//elevator door closes
	}
	
	/* Find elevator - Criteria
	 * 7. When an elevator request is made, the unoccupied elevator closest to it will answer the call, unless an occupied elevator is moving and 
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