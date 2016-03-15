package main.test;

import java.util.Scanner;

import main.service.ElevatorController;

public class RunElevatorSimulator {
	public static void main(String[] args) {		
		//Initialize the elevator simulation with the desired number of elevators, and the desired number of floors.
		//Assume ground/min of 1.
		int groundFloor = 1;
		
		Scanner scanner = new Scanner(System.in);
		
		//Enter number of elevators in a building
		System.out.println("Enter the number of elevators in a building: ");
		String noOfElevatorsStr = scanner.next();
		Integer noOfElevators = Integer.parseInt(noOfElevatorsStr);
		
		//Enter number of floors in a building		
		System.out.println("Enter the number of floors in a building: ");
		String noOfFloorsStr = scanner.next();
		Integer noOfFloors = Integer.parseInt(noOfFloorsStr);
		
		scanner.close();
		
		//Generating number of elevator users (ex: 2 per floor)
		int noOfUsers = noOfFloors * 2;
		
		//Elevator thread
		
		//ElevatorUser thread
		
		//Create elevator controller, with list of elevators in initial state(Idle)
		ElevatorController elevatorController = new ElevatorController(noOfElevators);
	}
}