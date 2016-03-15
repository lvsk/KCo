package main.domain;

import main.service.ElevatorController;

public class ElevatorUser implements Runnable{

	private Integer elevatorUserId;
	private int startFloor;
	private int endFloor;
	
	private Thread elevUsrthread;
	
	private ElevatorController elevatorController;
	
	public ElevatorUser(Integer eUId, int stFl, int edFl, ElevatorController elevatorController){
		this.elevatorUserId = eUId;
		this.startFloor = stFl;
		this.endFloor = edFl;
		this.elevUsrthread = new Thread(this, String.valueOf(eUId));
		this.elevatorController = elevatorController;
	}
	
	 public Thread getElevatorUserThread(){
		 return this.elevUsrthread;
	 }
	
	@Override
	public void run() {
		//Call elevator
		Elevator assignedElevator = elevatorController.callElevator(this);
		if(assignedElevator != null){
			//Ride elevator
			elevatorController.rideElevator(assignedElevator, this);
			//Exit elevator
			elevatorController.exitElevator(assignedElevator, this);
		}	
	}

	public Integer getElevatorUserId() {
		return elevatorUserId;
	}

	public void setElevatorUserId(Integer elevatorUserId) {
		this.elevatorUserId = elevatorUserId;
	}

	public int getStartFloor() {
		return startFloor;
	}

	public void setStartFloor(int startFloor) {
		this.startFloor = startFloor;
	}

	public int getEndFloor() {
		return endFloor;
	}

	public void setEndFloor(int endFloor) {
		this.endFloor = endFloor;
	}
}
