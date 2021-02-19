package spring.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Document(collection = "parkedcars")
public class Car implements Serializable {
    public int id;
    public int registrationNo;
    public String color;
    public int allotedSlot;

    public Car(int id,int registrationNo,String color,int allotedSlot) { // constructor initialization
        this.id=id;
        this.registrationNo=registrationNo;
        this.color=color;
        this.allotedSlot=allotedSlot;
    }

    @Id
    public int getId() {
        return this.id;
    }

    public int getRegistrationNo(){
        return this.registrationNo;
    }

    public String getColor(){
        return this.color;
    }

    public int getSlot(){
        return this.allotedSlot;
    }
}
