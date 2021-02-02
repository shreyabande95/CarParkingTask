package com.carParking;

import java.util.Scanner;

public class Main {
public static void main(String[] args){	
	try { 
		CreateLog log=new CreateLog("LogFile.txt");
		Scanner sc=new Scanner(System.in);
		System.out.println("Press: ");
		System.out.println("1 for in-memory based car parking ");
		System.out.println("2 for mongodb based car parking ");
		System.out.println("3 for elasticsearch ");
		System.out.println("4 for reddis ");
		int callClass=sc.nextInt();
		switch(callClass){
			
		case 1:
			ParkCar parkCar=new ParkCar();
			parkCar.initiate();
			break;
			
		default:
			log.logger.info("Invalid input in Main Class");
			
			}
		}	catch(Exception e) {  
			e.printStackTrace();  
		}
	} 
}
	
