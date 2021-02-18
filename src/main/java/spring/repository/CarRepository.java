package spring.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class CarRepository {
    @Autowired
    public RedisTemplate<String, Car> redis;

    public HashOperations hashOperations;
    public static List<Boolean> slots;
    static int count;

    public CarRepository(RedisTemplate<String,Car> redisTemplate){
        redis=redisTemplate;
        hashOperations=redis.opsForHash();
        System.out.println("redis constructor initialised");
        slots=new ArrayList<Boolean>();
        for(int i=0;i<100;i++){
            slots.add(true);				//initially all slots are vacant
        }
        count=1;
        System.out.println("Slots initialised");
    }

    public int assignSlot() {
        int vac=slots.indexOf(true);	// find first vacant slot
        slots.set(vac,false);
        return vac;
    }


    public void addCar(Car car){
        //int regNo= checkDuplicate(car.getRegistrationNo());
        int floor= (car.getSlot()/20)+1;
        int slot=(car.getSlot()%20)+1;
        hashOperations.put("parkedCars"+count,"Registration Number",car.getRegistrationNo());
        hashOperations.put("parkedCars"+count,"Color",car.getColor());
        hashOperations.put("parkedCars"+count,"Floor",floor);
        hashOperations.put("parkedCars"+count,"Slot",slot);
        count++;
    }

    public String getRegNoByColor(String color) {
        String res = " ";
        for (int i = 1; i < count; i++) {
            Map<String, Car> parkedCars = hashOperations.entries("parkedCars" + i);
            for (Map.Entry<String, Car> entry : parkedCars.entrySet()) {
                String key = entry.getKey();
                String val = String.valueOf(entry.getValue());
                if (key.equals("Color") && (val.equals(color))) {
                    for (Map.Entry<String, Car> subentry : parkedCars.entrySet()) {
                        String subkey = subentry.getKey();
                        if (subkey.equals("Registration Number")) {
                            res = res + "[" + String.valueOf(subentry.getValue()) + "] ";
                        }
                    }
                }
            }

        }
        return res;
    }

    public String getSlot(int regNo) {
        String res = " ";
        for (int i = 1; i < count; i++) {
            Map<String, Car> parkedCars = hashOperations.entries("parkedCars" + i);
            for (Map.Entry<String, Car> entry : parkedCars.entrySet()) {
                String key = entry.getKey();
                String val = String.valueOf(entry.getValue());
                if(key.equals("Registration Number")&&(val.equals(String.valueOf(regNo)))){
                    for(Map.Entry<String, Car> subentry : parkedCars.entrySet()){
                        String subkey= subentry.getKey();
                        if(subkey.equals("Floor")){
                            res=res+"[Floor:"+String.valueOf(subentry.getValue())+" Slot:";
                            break;
                        }
                    }
                    for(Map.Entry<String, Car> subentry : parkedCars.entrySet()){
                        String subkey= subentry.getKey();
                        if(subkey.equals("Slot")){
                            res=res+""+subentry.getValue()+"] ";
                            break;
                        }
                    }
                    break;
                }

            }
        }
        return res;
    }


    public String getSlotByColor(String color) {
        String res=" ";
        for (int i = 1; i < count; i++) {
            Map<String, Car> parkedCars = hashOperations.entries("parkedCars"+i);
            for (Map.Entry<String, Car> entry : parkedCars.entrySet()){
                String key=entry.getKey();
                String val= String.valueOf(entry.getValue());
                if((key.equals("Color"))&&val.equals(color)){
                    String result=String.valueOf(entry);
                    for(Map.Entry<String, Car> subentry : parkedCars.entrySet()){
                        String subkey= subentry.getKey();
                        if(subkey.equals("Floor")){
                            res=res+"[Floor:"+String.valueOf(subentry.getValue())+" Slot:";
                            break;
                        }
                    }
                    for(Map.Entry<String, Car> subentry : parkedCars.entrySet()){
                        String subkey= subentry.getKey();
                        if(subkey.equals("Slot")){
                            res=res+""+subentry.getValue()+"] ";
                            break;
                        }
                    }

                }
            }

        }
        return res;
    }
}



