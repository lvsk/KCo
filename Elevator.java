package main.domain;

import main.enums.ElevatorStatusEnum;
import main.service.ElevatorController;

public class Elevator implements Runnable{
	private Integer elevatorId;
	private int currentFloor;
	private int numOfTrips;
	private int numOfFloors;
	private ElevatorStatusEnum status;
	
	private Thread elevThread;
	private ElevatorController elevatorController;
	
	public Elevator(Integer elevatorId, int currentFloor, ElevatorStatusEnum initialStatus, ElevatorController elevatorController){		
		this.elevatorId = elevatorId;
		this.currentFloor = currentFloor;
		this.status = initialStatus;
		this.elevThread = new Thread(this, String.valueOf(elevatorId));
		this.elevatorController = elevatorController;
	}
	
	public Thread getElevatorThread(){
		return this.elevThread;
	}
	
	@Override
	public void run() {
		//TODO
	}

	public Integer getElevatorId() {
		return elevatorId;
	}

	public void setElevatorId(Integer elevatorId) {
		this.elevatorId = elevatorId;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public int getNumOfTrips() {
		return numOfTrips;
	}

	public void setNumOfTrips(int numOfTrips) {
		this.numOfTrips = numOfTrips;
	}

	public int getNumOfFloors() {
		return numOfFloors;
	}

	public void setNumOfFloors(int numOfFloors) {
		this.numOfFloors = numOfFloors;
	}

	public ElevatorStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ElevatorStatusEnum status) {
		this.status = status;
	}
}