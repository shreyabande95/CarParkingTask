package com.carParking;


public class Car {
	public int registration_no;
	public String color;
	public int allotedSlot;
	
	public Car(int registration_no,String color)  // constructor initialization
	{
		this.registration_no=registration_no;
		this.color=color;
		allotSlot();
	}

	public void allotSlot() {
		
		int vac=ParkCar.slots.indexOf(true);	// find first vacant slot
		
		ParkCar.slots.set(vac,false);
		
		allotedSlot=vac;
}
	
	public int getRegistrationNo(){
		return this.registration_no;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public int getSlot(){
		return this.allotedSlot;
	}
}