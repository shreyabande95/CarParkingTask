package client;

import core.CarOperations;
import core.DbBaseClient;
import dto.Car;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;


public class Redis extends DbBaseClient {
    Jedis jedis;
    static int count = 1;
    static Logger log = Logger.getLogger(Redis.class.getName());
    private static final String  keyName="parkedCar";
    public Redis(){
        getConnection();
    }

    public void getConnection() {
        InputStream input = Redis.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        if (input == null) {
            System.out.println("Sorry, unable to find host");
            return;
        }
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String host = (new StringBuilder(prop.getProperty("hostName"))).toString();
        jedis = new Jedis(host);
    }

    public int checkDuplicateRegNo(int regNo){
        if(count==1)
        return regNo;
        else{
            ArrayList<Integer> usedRegNo=new ArrayList<>();
            for(int i=1;i<count;i++){
                Map<String, String> car = jedis.hgetAll(keyName + i);
                String no = car.get("Registration Number");
                usedRegNo.add(Integer.parseInt(no));
            }
            boolean containsDuplicate=usedRegNo.contains(regNo);
            Scanner sc=new Scanner(System.in);
            while(containsDuplicate){
                log.info("Duplicate value of Registration Number");
                System.out.println("Registration number already exists! Please enter a new one");
                regNo=sc.nextInt();
                containsDuplicate=usedRegNo.contains(regNo);
            }
            return regNo;
        }
    }

    public void createObject(Car car) {
        Map<String, String> map = new HashMap<>();
        int floor = (car.getSlot() / 20) + 1;
        int slot = (car.getSlot() % 20) + 1;
        int regNo=car.getRegistrationNo();
        regNo=checkDuplicateRegNo(regNo);
        map.put("Registration Number", Integer.toString(regNo));
        map.put("Color", car.getColor());
        map.put("Floor", Integer.toString(floor));
        map.put("Slot", Integer.toString(slot));
        jedis.hmset(keyName + count, map);
        count++;
    }

    public void getRegNoByColor(String color) {
        for (int i = 1; i < count; i++) {
            Map<String, String> car = jedis.hgetAll(keyName + i);
            if(car.isEmpty())
                continue;
            String thisColor = car.get("Color");
            if (thisColor.equals(color)) {
                int regNo = Integer.parseInt(car.get("Registration Number"));
                log.info(String.valueOf(regNo));
            }
        }
    }

    public void getSlot(int registrationNo) {
        String stringRegistrationNo=String.valueOf(registrationNo);
        for (int i = 1; i < count; i++) {
            Map<String, String> car = jedis.hgetAll(keyName + i);
            if(car.isEmpty())
                continue;
            String thisColor = car.get("Registration Number");
            if (thisColor.equals(stringRegistrationNo)) {
                int floor = Integer.parseInt(car.get("Floor"));
                int slot = Integer.parseInt(car.get("Slot"));
                log.info("Floor:"+floor +" Slot:"+ slot);
                break;
            }

        }
    }

    public void getSlot(String color) {
        for (int i = 1; i < count; i++) {
            Map<String, String> car = jedis.hgetAll(keyName + i);
            if(car.isEmpty())
                continue;
            String thisColor = car.get("Color");
            if (thisColor.equals(color)) {
                int floor = Integer.parseInt(car.get("Floor"));
                int slot = Integer.parseInt(car.get("Slot"));
                log.info("Floor:"+ floor +" Slot:"+ slot);
            }
        }
    }

    public void dePark(int registrationNumber) {
        for (int i = 1; i < count; i++) {
            Map<String, String> car = jedis.hgetAll(keyName + i);
            String thisColor = car.get("Registration Number");
            if (thisColor.equals(String.valueOf(registrationNumber))) {
                jedis.del(keyName + i);
                break;
            }
        }
        count--;
        log.info("Car deparked");
    }

    public void deleteCollection(){
        for(int i=1;i<count;i++){
            jedis.del(keyName+i);
        }
        closeConnection(jedis);
    }

    public void closeConnection(Jedis jedis){
        jedis.close();
    }
}
