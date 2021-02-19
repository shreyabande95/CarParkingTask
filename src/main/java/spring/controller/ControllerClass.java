package spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import spring.dto.Car;
import spring.repository.CarOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/elastic_search/cars")
@ComponentScan("main.java.spring.repository")
public class ControllerClass {
    @Autowired
    CarOperations operations;
    public static List<Boolean> slots;
    static int id;
    public ControllerClass(CarOperations operations){
        slots=new ArrayList<Boolean>();
        for(int i=0;i<100;i++){
            slots.add(true);				//initially all slots are vacant
        }
    }

    public int assignSlot() {
        int vac=slots.indexOf(true);	// find first vacant slot
        slots.set(vac,false);
        return vac;
    }

    @RequestMapping(value="/post_car/{regNo}/{color}",method=RequestMethod.GET)
    public String newCar(@PathVariable String regNo,@PathVariable String color){
    int slot=assignSlot();
    id=Integer.parseInt(regNo);
    Car car=new Car(id,Integer.parseInt(regNo),color,slot);
    operations.addCar(car);
    return "New Car registered with Registration Number:"+car.getRegistrationNo();
    }

    @GetMapping(value="/search/registration_number/{color}")
    public String retrieveRegNoByColor(@PathVariable String color){
        return operations.getRegNoByColor(color);
    }

    @RequestMapping("/search/slot_by_regno/{regNo}")
    public String retrieveSlot(@PathVariable int regNo){
        return operations.getSlot(regNo);
    }

    @RequestMapping("/search/slot/{color}")
    public String retrieveSlotByColor(@PathVariable String color){
        return operations.getSlotByColor(color);
    }
}
