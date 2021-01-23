package com.carParking;

import java.io.*;
import java.util.*;

  class Car
{
	
	public int registration_no;
	public String color;
	public int alloted_slot;
	


	public Car(int registration_no,String color)  // constructor initialization
	{
		this.registration_no=registration_no;
		this.color=color;
		allot_slot();
	}

	public void allot_slot() {
		
		int vac=CarTask.slots.indexOf(true);	// find first vacant slot
		
		CarTask.slots.set(vac,false);
		
		alloted_slot=vac;
		
		

	}


}


public class CarTask

{
	public static List<Car> parkedCar;		//stores details of parked cars
	public static List<Boolean> slots;		//keeps track of vacant and empty slots


public static void main(String[] args)
{
	 boolean flag=true;
	 parkedCar=new ArrayList<Car>();
	slots=new ArrayList<Boolean>();
	
	
	for(int i=0;i<100;i++)
	{
		slots.add(true);				//initially all slots are vacant
	}
	
	
	do {

	Scanner sc=new Scanner(System.in);
	System.out.println("New car? yes/no: ");	//if parked already, enter no, else yes
	String ans=sc.nextLine();
	switch(ans)
	{
			case ("yes"):

				int regno; String color; 
				System.out.println("Enter the Registration Number of your Car: ");
				regno=sc.nextInt();
				
				ArrayList<Integer> checkduplicate=new ArrayList<Integer>();
				
			
					for(int i=0;i<parkedCar.size();i++)
				{
					checkduplicate.add(parkedCar.get(i).registration_no);
					
				}
					// check duplicate values of Registration Number
					
					boolean check_existing_rno=checkduplicate.contains(regno);
					
					while(check_existing_rno) {
						System.out.println("Car already exists! Enter different number:");
						regno=sc.nextInt();
						check_existing_rno=checkduplicate.contains(regno);
						
					}
					
			
				
				System.out.println("Enter its color: ");
				color=sc.next();
				Car car=new Car(regno,color);
				
				
				parkedCar.add(car);
				System.out.println("You have been alloted slot "+((car.alloted_slot%10)+1)+" on floor "+((car.alloted_slot/10)+1));
				break;

			
			case ("no"):

				System.out.println("Press: ");
				System.out.println(" 1 to display Registration Number of all cars with a particular color");
				System.out.println(" 2 to get Slot number of your parked car");
				System.out.println(" 3 to get slot numbers of all slots where cars of particular color are parked");
				System.out.println(" 4 to depark your car");

				int option=sc.nextInt();
				switch(option)
				{
					case 1:

						String col;
						System.out.println("Enter color: ");
						col=sc.next();
						ArrayList<Integer> result=new ArrayList<Integer>();
						//System.out.println(parkedCar);
						for(int i=0;i<parkedCar.size();i++)
						{
							Car thiscar=parkedCar.get(i);
							//System.out.println(thiscar.color);
							if(col.equals(thiscar.color))
								{  // System.out.println(thiscar.registration_no);
									result.add(thiscar.registration_no); }


						}
					    System.out.println(result);
						break;


					case 2:

						System.out.println("Enter your Car's Registration Number: ");
						int rno=sc.nextInt();
						int result2=0;
						for(int i=0;i<parkedCar.size();i++)
						{
							Car thiscar=parkedCar.get(i);
							if(thiscar.registration_no==rno)
							{	result2=thiscar.alloted_slot;
								break;

						     }
						 }
						     System.out.println("Your car is parked at floor "+ ((result2/10)+1)+" slot "+result2%10);
						     break;


					case 3:

						ArrayList<Integer> result3=new ArrayList<Integer>();
						System.out.println("Enter color: ");
						String c=sc.next();
						for(int i=0;i<parkedCar.size();i++)
						{
							Car thiscar=parkedCar.get(i);
							if(c.equals(thiscar.color))
							{
								result3.add(thiscar.alloted_slot);
							}

							
						}

						for(int i=0;i<result3.size();i++)
							{
								int s=result3.get(i);
								System.out.println("Floor "+((s/10)+1)+" slot "+((s%10)+1));
							}

						break;

					case 4:
						
						System.out.println("Enter your Registration Number: ");
						int car_rno=sc.nextInt();
						for(int i=0;i<parkedCar.size();i++)
						{
							Car thiscar=parkedCar.get(i);
							if(car_rno==thiscar.registration_no)
							{
								parkedCar.remove(i);
								slots.set(i, true);
							}
							System.out.println("Car deparked successfully");
							
						}
						break;

					default:

						System.out.println("No case matched");


		


				}

				break;


				default:

						System.out.println("No case matched");
	
}

System.out.println("Do you want to continue? yes/no");
Scanner sc2=new Scanner(System.in);
String con=sc2.nextLine();

flag=con.equals("yes");

 }while(flag==true);

} 

}












