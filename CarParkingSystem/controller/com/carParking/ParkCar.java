package com.carParking;

import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ParkCar {

	static List<Car> parkedCar;		//stores details of parked cars
	static List<Boolean> slots;		//keeps track of vacant and empty slots
	
	public  void initiate(){
			boolean flag=true;
			parkedCar=new ArrayList<Car>();
			slots=new ArrayList<Boolean>(); 
			try { 
			CreateLog log=new CreateLog("LogFile.txt");
			log.logger.info(Main.class.getSimpleName()+" Class is running");
			ParkCarHelper carService=new ParkCarHelper(); 
			for(int i=0;i<100;i++) {
				slots.add(true);				//initially all slots are vacant
			}

			do {
				Scanner sc=new Scanner(System.in);
				System.out.println("New car? yes/no: ");	//if parked already, enter no, else yes
				String ans=sc.nextLine();
				switch(ans) {
					
					case ("yes"):
						ArrayList<Integer> myslot=new ArrayList<Integer>();
						myslot=carService.registerCar();
					    log.logger.info("New car registered with floor: "+myslot.get(0)+" slot "+myslot.get(1)); 
					break;

			
					case ("no"):
						System.out.println("Press: ");
						System.out.println(" 1 to display Registration Number of all cars with a particular color");
						System.out.println(" 2 to get Slot number of your parked car");
						System.out.println(" 3 to get slot numbers of all slots where cars of particular color are parked");
						System.out.println(" 4 to depark your car");

						int option=sc.nextInt();
						
						switch(option) {
							case 1:
								ArrayList<Integer> allRegNo=new ArrayList<Integer>();
								allRegNo=carService.getRegistrationNo();
								for(int i=0;i<allRegNo.size();i++) {
									log.logger.info(allRegNo.get(i)+" ");
								}
							break;

							case 2:
								System.out.println("Enter your Car's Registration Number: ");
								int rno=sc.nextInt();
								int result=carService.getSlot(rno);
								log.logger.info("Alloted floor: "+ ((result/20)+1)+" slot "+((result%20)+1));//("Your car is parked at floor "+ ((result/20)+1)+" slot "+((result%20)+1));
							break;

							case 3:
								ArrayList<Integer> result3=new ArrayList<Integer>();
								System.out.println("Enter color: ");
								String color=sc.next();
								result3=carService.getSlot(color);
								for(int i=0;i<result3.size();i++){
								int s=result3.get(i);
								System.out.println("Floor "+((s/20)+1)+" slot "+((s%20)+1));
								log.logger.info("Floor "+((s/20)+1)+" slot "+((s%20)+1));
								}
						   break;

						   case 4:
							   System.out.println("Enter your Registration Number: ");
							   int carRegNo=sc.nextInt();
							   carService.freeSlot(carRegNo);
							   log.logger.info("Car deparked");
						   break;

						   default:
							   log.logger.info("No case matched");

				}

				   break;


				default:

						log.logger.info("No case matched");
	
				}

				System.out.println("Do you want to continue? yes/no");
				Scanner sc2=new Scanner(System.in);
				String con=sc2.nextLine();
				flag=con.equals("yes");
			
				
				
			
			}while(flag==true);
			}
			catch (Exception e) {  
	        e.printStackTrace();  
	    } 
			

		} 

	
}










