package spring.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
@Document(indexName = "parkedcars")
public class Car implements Serializable {
    public int id;
    public int registrationNo;
    public String color;
    public int allotedSlot;

    public Car(int id,int registration_no,String color,int allotedSlot) { // constructor initialization
        this.registrationNo=registration_no;
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
