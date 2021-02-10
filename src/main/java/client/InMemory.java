package client;

import core.CarOperation;
import core.DbBaseClient;
import dto.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class InMemory extends DbBaseClient {
    static List<Car> parkedCar=new ArrayList<>();
    static Logger log = Logger.getLogger(InMemory.class.getName());

    public  void createObject(Car car){

        int registrationNo; //String color;
        ArrayList<Integer> result=new ArrayList<Integer>();
        Scanner sc=new Scanner(System.in);
        ArrayList<Integer> checkDuplicate=new ArrayList<Integer>();
        for(int i=0;i<parkedCar.size();i++){
            checkDuplicate.add(parkedCar.get(i).registrationNo);
        }
        // check duplicate values of Registration Number

        boolean checkExistingRno=checkDuplicate.contains(car.getRegistrationNo());

        while(checkExistingRno) {
            System.out.println("Car already exists! Enter different number:");
            registrationNo=sc.nextInt();
            checkExistingRno=checkDuplicate.contains(registrationNo);
        }

        parkedCar.add(car);
        log.info(String.valueOf(parkedCar.size()));
        log.info("You have been alloted floor "+((car.allotedSlot/20)+1)+" slot "+((car.allotedSlot%20)+1));
    }

    public  void getRegNo(String color) {
        ArrayList<Integer> result=new ArrayList<>();
        log.info("parkedcar size"+parkedCar.size());
        for(int i=0;i<parkedCar.size();i++){
            Car thiscar=parkedCar.get(i);
            if(color.equals(thiscar.color)){
                result.add(thiscar.registrationNo);
            }
        }
        //log.info(String.valueOf(result.size()));
        for(int i=0;i<result.size();i++) {
            log.info(String.valueOf(result.get(i)));
        }
    }

    public  void getSlot(int rno) {
        int result=0;
        for(int i=0;i<parkedCar.size();i++){
            Car thiscar=parkedCar.get(i);
            if(thiscar.registrationNo==rno){
                result=thiscar.allotedSlot;
                break;
            }
        }
        log.info(String.valueOf(result));

    }

    public  void getSlot(String color){
        ArrayList<Integer> result=new ArrayList<Integer>();
        for(int i=0;i<parkedCar.size();i++) {
            Car thiscar=parkedCar.get(i);
            if(color.equals(thiscar.color)){
                result.add(thiscar.allotedSlot);
            }
        }
        log.info(String.valueOf(result.size()));
        for(int i=0;i<result.size();i++){
            int s=result.get(i);
            log.info("Floor "+((s/20)+1)+" slot "+((s%20)+1));
        }
    }

    public  void dePark(int regNo) {
        for(int i=0;i<parkedCar.size();i++){
            Car thiscar=parkedCar.get(i);
            if(regNo==thiscar.registrationNo) {
                parkedCar.remove(i);
                CarOperation.slots.set(i, true);
            }

        }
        log.info("Your car has been deparked");
    }

    public void deleteCollection()
    {
        parkedCar.clear();
        CarOperation.slots.clear();
    }

}
