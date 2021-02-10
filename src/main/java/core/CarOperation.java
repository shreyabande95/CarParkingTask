package core;

import dto.Car;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class CarOperation {
    public static List<Boolean> slots;
    static Logger log = Logger.getLogger(CarOperation.class.getName());
    public CarOperation() {
        slots=new ArrayList<Boolean>();
        for(int i=0;i<100;i++){
            slots.add(true);				//initially all slots are vacant
        }
    }

    public void initiate(StringBuilder calledClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        boolean flag=true;
        Scanner sc=new Scanner(System.in);
        String clientClass=calledClass.toString();
        DbBaseClient dbClient=new DbBaseClient();
        dbClient.setClient(calledClass);

        do {
            System.out.println("New car? yes/no: ");	//if parked already, enter no, else yes
            String ans=sc.nextLine();
            switch(ans){
                case ("yes"):
                    int regNo; String color;
                    System.out.println("Enter the Registration Number of your Car: ");
                    regNo=sc.nextInt();
                    System.out.println("Enter its color: ");
                    color=sc.next();
                    int allotThisSlot=allotSlot();
                    Car car=new Car(regNo,color,allotThisSlot);
                    dbClient.createObject(car);
                    break;

                case ("no"):
                    System.out.println("Press: ");
                    System.out.println(" 1 to display Registration Number of all cars with a particular color");
                    System.out.println(" 2 to get Slot number of your parked car");
                    System.out.println(" 3 to get Slot numbers of all slots where cars of particular color are parked");
                    System.out.println(" 4 to Depark your car");
                    int option=sc.nextInt();
                    switch(option)
                    {
                        case 1:

                            String col;
                            System.out.println("Enter color: ");
                            col=sc.next();
                            dbClient.getRegNo(col);

                            break;


                        case 2:

                            System.out.println("Enter your Car's Registration Number: ");
                            int rNo=sc.nextInt();
                            dbClient.getSlot(rNo);
                            break;


                        case 3:


                            System.out.println("Enter color: ");
                            String c=sc.next();
                            dbClient.getSlot(c);
                            break;

                        case 4:

                            System.out.println("Enter your Registration Number: ");
                            int carrno=sc.nextInt();
                            dbClient.dePark(carrno);
                            break;

                        default:

                            log.info("No case matched");

                    }

                    break;


                default:

                    log.info("No case matched");

            }

            System.out.println("Do you want to continue? yes/no");
            Scanner sc2=new Scanner(System.in);
            String con=sc2.nextLine();
            flag=con.equals("yes");
        } while(flag==true);

        dbClient.deleteCollection();

    }

    public int allotSlot() {
        int vac=slots.indexOf(true);	// find first vacant slot
        slots.set(vac,false);
        return vac;
    }

}
