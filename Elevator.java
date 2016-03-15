package main.domain;

public class Elevator implements Runnable{
	private Integer elevatorId;
	private int currentFloor;
	private int numOfTrips;
	private int numOfFloors;
	
	public Elevator(Integer elevatorId, int currentFloor){		
		this.elevatorId = elevatorId;
		this.currentFloor = currentFloor;
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
}