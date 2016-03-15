package main.domain;

public class ElevatorUser implements Runnable{

	private Integer elevatorUserId;
	private int startFloor;
	private int endFloor;
	
	public ElevatorUser(Integer eUId, int stFl, int edFl){
		this.elevatorUserId = eUId;
		this.startFloor = stFl;
		this.endFloor = edFl;
	}
	
	@Override
	public void run() {
		// TODO
		
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
