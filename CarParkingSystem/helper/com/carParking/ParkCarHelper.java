package com.carParking;

import java.util.ArrayList;
import java.util.Scanner;

public class ParkCarHelper {
	
	public  ArrayList<Integer> registerCar(){
		int registrationNo; String color; 
		ArrayList<Integer> result=new ArrayList<Integer>();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Registration Number of your Car: ");
		registrationNo=sc.nextInt();
		ArrayList<Integer> checkDuplicate=new ArrayList<Integer>();
		for(int i=0;i<ParkCar.parkedCar.size();i++){
		checkDuplicate.add(ParkCar.parkedCar.get(i).registration_no);
		}
	// check duplicate values of Registration Number
	
		boolean checkExistingRno=checkDuplicate.contains(registrationNo);
	
		while(checkExistingRno) {
			System.out.println("Car already exists! Enter different number:");
			registrationNo=sc.nextInt();
			checkExistingRno=checkDuplicate.contains(registrationNo);	
			}
	
		System.out.println("Enter its color: ");
		color=sc.next();
		Car car=new Car(registrationNo,color);

		ParkCar.parkedCar.add(car);
		result.add(((car.allotedSlot/20)+1));
		result.add(((car.allotedSlot%20)+1));
		return result;
		//System.out.println("You have been alloted slot "+((car.allotedSlot%20)+1)+" on floor "+((car.allotedSlot/20)+1));
	}

	public  ArrayList<Integer> getRegistrationNo() {

		String color;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter color: ");
		color=sc.next();
		ArrayList<Integer> result=new ArrayList<Integer>();

		for(int i=0;i<ParkCar.parkedCar.size();i++){
			Car thiscar=ParkCar.parkedCar.get(i);
			if(color.equals(thiscar.color)){  
				result.add(thiscar.registration_no); 
				}
		}
		return result;

	}

	public  int getSlot(int rno) {
		int result=0;
		for(int i=0;i<ParkCar.parkedCar.size();i++){
		Car thiscar=ParkCar.parkedCar.get(i);
		if(thiscar.registration_no==rno){
			result=thiscar.allotedSlot;
			break;
		}
	}
	return result;

	}

	public  ArrayList<Integer> getSlot(String color){
		ArrayList<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<ParkCar.parkedCar.size();i++) {
		Car thiscar=ParkCar.parkedCar.get(i);
		if(color.equals(thiscar.color)){
			result.add(thiscar.allotedSlot);
		}
	}

	return result;
	}

	public  void freeSlot(int regNo) {
		for(int i=0;i<ParkCar.parkedCar.size();i++){
		Car thiscar=ParkCar.parkedCar.get(i);
		if(regNo==thiscar.registration_no) {
			ParkCar.parkedCar.remove(i);
			ParkCar.slots.set(i, true);
		}

		}
		
	}

}
