package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.dto.Car;
import spring.repository.CarRepository;

@RestController
@RequestMapping("/parkedCars")
public class ControllerClass {
    @Autowired
    CarRepository carRepo;

    public ControllerClass(CarRepository carRepository){
        carRepo=carRepository;
        System.out.println("Controller class");
    }

    @RequestMapping("/test")
    public String test() {
        return "In controller class";
    }
    @GetMapping("/getRegistrationNumber/{color}")
    public String retrieveRegNoByColor(@PathVariable String color) {
        return carRepo.getRegNoByColor(color);
    }

    @RequestMapping(value="/addCar/{regNo}/{color}",method=RequestMethod.GET)
    public String newCar(@PathVariable String regNo,@PathVariable String color){
        int slot=carRepo.assignSlot();
        Car car=new Car(Integer.parseInt(regNo),color,slot);
        carRepo.addCar(car);
        return "New car added with Registration Number:"+regNo ;
    }

    @RequestMapping("/getSlot/{regNo}")
    public String retrieveSlot(@PathVariable int regNo){
        return carRepo.getSlot(regNo);
    }

    @RequestMapping("/getSlotbyColor/{color}")
    public String retrieveSlotByColor(@PathVariable String color){
        return carRepo.getSlotByColor(color);
    }




}
