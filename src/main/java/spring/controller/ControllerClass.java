package spring.controller;

import spring.dto.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import spring.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/cars")
@ComponentScan("main.java.spring.spring.repository")
public class ControllerClass {

    @Autowired
    CarRepository carRepo;
    static int id;
    public static List<Boolean> slots;

    public ControllerClass(){
        slots=new ArrayList<Boolean>();
        for(int i=0;i<100;i++){
            slots.add(true);				//initially all slots are vacant
        }
   }

   @RequestMapping("/test")
   public String test(){
        return "test successful";
   }

    public int assignSlot() {
        int vac = slots.indexOf(true);    // find first vacant slot
        slots.set(vac, false);
        return vac;
    }

    @RequestMapping(value="/post_car/{regNo}/{color}",method= RequestMethod.GET) // post
    public String newCar(@PathVariable String regNo, @PathVariable String color){
        int slot=assignSlot();
        id=Integer.parseInt(regNo);
        Car car=new Car(id,Integer.parseInt(regNo),color,slot);
        carRepo.save(car);
        return "New car added with Registration Number:"+regNo ;
    }

    @GetMapping("/search/registration_number/{color}") // search
    public String retrieveRegNoByColor(@PathVariable String color) {
        String res="";
        List<Car> cars=carRepo.findAll();
        System.out.println(cars);
         for(Car thisCar:cars){
             if(thisCar.color.equals(color)){
                 res+="Registration Number:"+ thisCar.getRegistrationNo()+" ";
             }
         }
         return res;
    }

    @RequestMapping("/search/slot_by_regno/{regNo}") //search
    public String retrieveSlot(@PathVariable int regNo){
        String res="";
        List<Car> cars=carRepo.findAll();
        System.out.println(cars);
        for(Car thisCar:cars){
            if(thisCar.registrationNo==regNo){
                res+="[Floor:"+ ((thisCar.allotedSlot/20)+1)+" Slot:"+((thisCar.allotedSlot%20)+1)+"] ";
            }
        }
        return res;
    }

    @RequestMapping("/search/slot/{color}")
    public String retrieveSlotByColor(@PathVariable String color){
        String res="";
        List<Car> cars=carRepo.findAll();
        System.out.println(cars);
        for(Car thisCar:cars){
            if(thisCar.color.equals(color)){
                res+="[Floor:"+ ((thisCar.allotedSlot/20)+1)+" Slot:"+((thisCar.allotedSlot%20)+1)+"] ";
            }
        }
        return res;
    }

}
