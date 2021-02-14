package core;

import dto.Car;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class DbBaseClient {
    static Logger log = Logger.getLogger(DbBaseClient.class.getName());
    String client;
    Object thisObject;


    public void setClient(StringBuilder clientClass) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        client = clientClass.toString();
        Class cls = Class.forName(client);
        //System.out.println(cls);
        thisObject = cls.newInstance();
    }
    public void createObject(Car car) {
        if(!client.equals("DbBaseClient"))
        ((DbBaseClient)thisObject).createObject(car);
        else log.info("Client connection failed");
    }
    public void getRegNoByColor(String color) throws IOException {
        if(!client.equals("DbBaseClient"))
        ((DbBaseClient)thisObject).getRegNoByColor(color);
        else log.info("Error in getting registration number from color");
    }

    public void getSlot(String color){
        if(!client.equals("DbBaseClient"))
        ((DbBaseClient)thisObject).getSlot(color);
        else log.info("Error in getting slot from color");
    }

    public void getSlot(int rNo){
        if(!client.equals("DbBaseClient"))
        ((DbBaseClient)thisObject).getSlot(rNo);
        else log.info("Error in getting slot from registration number");
    }
    public void dePark(int number) throws IOException {
        if(!client.equals("DbBaseClient"))
        ((DbBaseClient)thisObject).dePark(number);
        else log.info("Could not depark");
    }

    public void deleteCollection() throws IOException {
        if(!client.equals("DbBaseClient"))
        ((DbBaseClient)thisObject).deleteCollection();
        else log.info("Couldn't delete collection");
    }



}
