package main.domain;

import java.util.ArrayList;
import java.util.List;

import main.enums.ElevatorStatusEnum;
import main.service.ElevatorController;

public class Elevator implements Runnable{
	private Integer elevatorId;
	private int currentFloor;
	private int numOfTrips;
	private int numOfFloors;
	private boolean occupied;
	private ElevatorStatusEnum status;
	
	private Thread elevThread;
	private ElevatorController elevatorController;
	private List<ElevatorUser> userRequestList = new ArrayList<>();	
	
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
	
	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public List<ElevatorUser> getUserRequestList() {
		return userRequestList;
	}

	public void setUserRequestList(List<ElevatorUser> userRequestList) {
		this.userRequestList = userRequestList;
	}
}