package main.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import main.domain.Elevator;
import main.domain.ElevatorUser;
import main.service.ElevatorController;

public class RunElevatorSimulator {
	public static void main(String[] args) {		
		/* 1. Initialize the elevator simulation with the desired number of elevators, and the desired number of floors.
		Assume ground/min of 1.*/
		
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
		
		//Create elevator controller, with list of elevators in initial state(Idle)
		ElevatorController elevatorController = new ElevatorController(noOfElevators);
		
		/*Create list of ElevatorUser objects with one request command <start floor, end floor> per user per simulation		
		4. An elevator cannot proceed above the top floor.
		5. An elevator cannot proceed below the ground floor (assume 1 as the min).
		6. An elevator request can be made at any floor, to go to any other floor.*/
		List<ElevatorUser> elevatorUserList = new ArrayList<>();
		for(int i = 1; i<= noOfUsers; i++){
			int randomStartFloor = ThreadLocalRandom.current().nextInt(groundFloor, noOfFloors + 1);
			int randomEndFloor = ThreadLocalRandom.current().nextInt(groundFloor, noOfFloors + 1);
			while(randomStartFloor == randomEndFloor){
				randomEndFloor = ThreadLocalRandom.current().nextInt(groundFloor, noOfFloors + 1);
			}
			ElevatorUser elevatorUser = new ElevatorUser(i, randomStartFloor, randomEndFloor, elevatorController);
			elevatorUserList.add(elevatorUser);
		}

		//Execute ElevatorUserRequests
		for (ElevatorUser elevatorUser : elevatorUserList){
			elevatorUser.getElevatorUserThread().start();
		}

		//Execute elevator user requests for each elevator
		for (Elevator elevator : elevatorController.getElevatorList()){
			elevator.getElevatorThread().start();
		}

		//Wait for all the elevator user requests complete
		for (ElevatorUser elevatorUser : elevatorUserList){
			try {
				elevatorUser.getElevatorUserThread().join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.err.println("Failed to complete elevator user request");
				e.printStackTrace();
			}
		}
		
		//Check any Elevator in Service state, reset to Idle state, will become available for waiting user request
		
		//Execute waiting elevator user requests for each elevator
        for (ElevatorUser elevatorUser : elevatorController.getWaitingElevUserRequestList()){
        	try {
				elevatorUser.getElevatorUserThread().join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.err.println("Failed to complete waiting elevator user request");
				e.printStackTrace();
			}
        }

		//Stop elevators
		for (Elevator elevator : elevatorController.getElevatorList()){
			elevator.getElevatorThread().interrupt();
		}
	}
}